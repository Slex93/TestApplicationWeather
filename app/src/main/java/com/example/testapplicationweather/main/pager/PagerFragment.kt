package com.example.testapplicationweather.main.pager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapplicationweather.databinding.FragmentPagerBinding
import com.example.testapplicationweather.main.model.DailyModel
import com.example.testapplicationweather.main.model.DayModel

class PagerFragment(private val list: MutableLiveData<DailyModel>) : Fragment() {

    private lateinit var binding: FragmentPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagerBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        val adapter = PagerAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        list.observe(viewLifecycleOwner){
            adapter.setList(it.data as MutableList<DayModel>)
        }
    }

}