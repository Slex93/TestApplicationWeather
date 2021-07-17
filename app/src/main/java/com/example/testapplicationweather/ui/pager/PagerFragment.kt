package com.example.testapplicationweather.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapplicationweather.data.model.DayModel
import com.example.testapplicationweather.databinding.FragmentPagerBinding

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