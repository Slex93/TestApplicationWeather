package com.example.testapplicationweather.main.pager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapplicationweather.databinding.FragmentPagerBinding
import com.example.testapplicationweather.main.model.DayModel

class PagerFragment : Fragment() {

    private lateinit var binding: FragmentPagerBinding
    private val viewModel: PagerSharedViewModel by activityViewModels()

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
        val adapter = PagerAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.listOfDays.observe(viewLifecycleOwner, {
            adapter.setList(it.data as MutableList<DayModel>)
        })
    }

}