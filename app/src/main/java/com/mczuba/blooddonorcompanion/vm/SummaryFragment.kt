package com.mczuba.blooddonorcompanion.vm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.databinding.FragmentSummaryBinding
import kotlinx.android.synthetic.main.fragment_summary.*

class SummaryFragment : Fragment() {
    private lateinit var viewModel: SummaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = FragmentSummaryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(SummaryViewModel::class.java)
        binding.viewmodel = viewModel
        val navController = findNavController()

        binding.btnScheduleDonation.setOnClickListener {
            val direction = SummaryFragmentDirections.actionNavigationSummaryToScheduleRecordFragment()
            navController.graph.startDestination = R.id.summaryFragment;
            navController.navigate(direction)
        }

        viewModel.schedule.observe( viewLifecycleOwner, Observer() {
            if(it.scheduleId == -1) {
                binding.textScheduleNone.visibility = View.VISIBLE
                binding.layoutScheduleDetails.visibility = View.GONE
                btnScheduleRemove.visibility = View.GONE
            }
            else {
                binding.textScheduleNone.visibility = View.GONE
                binding.layoutScheduleDetails.visibility = View.VISIBLE
                btnScheduleRemove.visibility = View.VISIBLE

                if (it.note == "")
                    binding.viewScheduleNote.visibility = View.GONE
                else
                    binding.viewScheduleNote.visibility = View.VISIBLE

                if (it.location == "")
                    binding.viewScheduleLocation.visibility = View.GONE
                else
                    binding.viewScheduleLocation.visibility = View.VISIBLE

            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel?.updateData()
    }
}