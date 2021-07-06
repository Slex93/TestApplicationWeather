package com.example.testapplicationweather.main.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapplicationweather.databinding.FragmentPagerBinding

class PagerFragment : Fragment() {

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
        binding.recyclerView.adapter = PagerAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

}