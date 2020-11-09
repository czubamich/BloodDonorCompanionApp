package com.mczuba.blooddonorcompanion.vm.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.data.Donation
import com.mczuba.blooddonorcompanion.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {


        private val viewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHistoryBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        var donationData = donationListToDonationData(ArrayList<Donation>())

        val donationObserver = Observer<List<Donation>> { donations ->
            val donations = viewModel.donations

            if(!donations.value!!.isEmpty())
                binding.root.findViewById<View>(R.id.fragment_history_empty).visibility = View.INVISIBLE

            donationData = donationListToDonationData(ArrayList(donations.value))
            val adapter = DonationAdapter(requireContext(), donationData)

            val recyclerView = binding.root.findViewById<RecyclerView>(R.id.rv_history)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)

        }

        viewModel.donations.observe( viewLifecycleOwner, donationObserver)

        return binding.root
    }

    private fun donationListToDonationData(donations: ArrayList<Donation>) : ArrayList<DonationData>
    {
        val donationData = ArrayList<DonationData>()
        var prevYear = 0;

        donations.sortWith(compareByDescending{it.date})
        donations.forEach {
            donationData.add(DonationData(requireContext(), it).apply {
                if(prevYear!=this.donation.date.year) {
                    donationData.add(DonationData(requireContext(), it, true))
                    prevYear=this.donation.date.year
                }
            })
        }
        return donationData
    }
}