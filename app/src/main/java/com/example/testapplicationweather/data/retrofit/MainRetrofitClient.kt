package com.example.testapplicationweather.data.retrofit

import com.example.testapplicationweather.utilites.Resources.cacheDirectory
import com.example.testapplicationweather.utilites.Resources.internetConnection
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainRetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String, needCache: Boolean): Retrofit {
        if (retrofit == null) {

            val mLoggingInterceptor = HttpLoggingInterceptor()
            mLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val clientBuilder = OkHttpClient.Builder()
                .addInterceptor(mLoggingInterceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)

            if (needCache) clientBuilder.addCache(20)

            val client = clientBuilder.build()

            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    private fun OkHttpClient.Builder.addCache(cache: Long): OkHttpClient.Builder = this
        .addInterceptor(offlineInterceptor)
        .addNetworkInterceptor(onlineInterceptor)
        .cache(Cache(cacheDirectory, cache * 1024 * 1024))

    private var onlineInterceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val maxAge = 60 * 60 * 3
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
    }

    private var offlineInterceptor = Interceptor { chain ->
        var request: Request = chain.request()
        if (!internetConnection) {
            val maxStale = 60 * 60 * 3
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
                .build()
        }
        chain.proceed(request)
    }
}


