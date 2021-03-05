package com.mczuba.blooddonorcompanion.vm.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.data.models.Donation
import com.mczuba.blooddonorcompanion.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }
    private lateinit var binding : FragmentHistoryBinding
    var donationData = donationListToDonationData(ArrayList<Donation>())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.donations.observe(viewLifecycleOwner, donationObserver)
        viewModel.updateDonations()
    }

    private val donationObserver = Observer<List<Donation>> { donations ->
        if(binding.rvHistory.adapter!=null) {
            donationData = donationListToDonationData(ArrayList(donations))
            val adapter = DonationAdapter(requireContext(), donationData)
            binding.rvHistory.adapter = adapter
            return@Observer
        }
        val donations = viewModel.donations

        if (!donations.value!!.isEmpty())
            binding.fragmentHistoryEmpty.visibility = View.INVISIBLE

        donationData = donationListToDonationData(ArrayList(donations.value))

        val adapter = DonationAdapter(requireContext(), donationData)
        registerForContextMenu(binding.rvHistory)

        binding.rvHistory.adapter = adapter
        binding.rvHistory.layoutManager = GridLayoutManager(requireContext(), 1)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.record_remove -> {
                val adapter = (binding.rvHistory.adapter as DonationAdapter)
                val index = donationData.indexOfLast { it.getDonationID() == adapter.selectedDonation!!.donationId }
                donationData.removeAt(index)
                adapter.notifyItemRemoved(index)

                if(donationData[index-1].header && (index==donationData.size || donationData[index].header) ) {
                    donationData.removeAt(index-1)
                    adapter.notifyItemRemoved(index-1)
                }

                viewModel.removeDonation(adapter.selectedDonation!!)
                if(viewModel.donations.value.isNullOrEmpty())
                    binding.fragmentHistoryEmpty.visibility = View.VISIBLE
            }
            R.id.record_edit -> {
                val adapter = (binding.rvHistory.adapter as DonationAdapter)
                val action = HistoryFragmentDirections.actionHistoryFragmentToNewRecordFragment(adapter.selectedDonation!!.donationId)
                this.findNavController().navigate(action)
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun donationListToDonationData(donations: ArrayList<Donation>): ArrayList<DonationData> {
        val donationData = ArrayList<DonationData>()
        var prevYear = 0;

        donations.sortWith(compareByDescending { it.date })
        donations.forEach {
            donationData.add(DonationData(requireContext(), it).apply {
                if (prevYear != this.donation.date.year) {
                    donationData.add(DonationData(requireContext(), it, true))
                    prevYear = this.donation.date.year
                }
            })
        }
        return donationData
    }
}