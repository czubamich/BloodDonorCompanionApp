package com.mczuba.blooddonorcompanion.vm.editors

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.databinding.FragmentRecordNewBinding
import com.mczuba.blooddonorcompanion.util.DatePickerDialogFragment
import com.mczuba.blooddonorcompanion.util.FoldableLayoutHelper


class NewRecordFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    var detailsExpanded: Boolean = false;
    private val viewModel by lazy {
        ViewModelProvider(this).get(NewRecordViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecordNewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        val buttonExpand = binding.root.findViewById<MaterialButton>(R.id.button_Expand)
        val layoutExpand = binding.root.findViewById<View>(R.id.layout_details)
        FoldableLayoutHelper(requireContext(), layoutExpand, buttonExpand, true)

        viewModel.completeState.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val action =
                    NewRecordFragmentDirections.actionNewRecordFragmentToNavigationHistory()
                Navigation.findNavController(binding.root).navigate(action)
            }
        })

        viewModel.openDatePicker.observe(viewLifecycleOwner, Observer {
            if (it) {
                val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
                val newFragment: DialogFragment = DatePickerDialogFragment(this, viewModel.donation.getDate()).apply {

                }
                newFragment.show(ft, "dialog")
            }
        })

        return binding.root
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.setNewDateTime(year, month, dayOfMonth)
    }
}
