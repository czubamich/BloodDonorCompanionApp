package com.mczuba.blooddonorcompanion.vm.editors

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.mczuba.blooddonorcompanion.BR
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.data.models.Donation
import com.mczuba.blooddonorcompanion.databinding.FragmentRecordNewBinding
import com.mczuba.blooddonorcompanion.util.DatePickerDialogFragment
import com.mczuba.blooddonorcompanion.util.FoldableLayoutHelper
import java.util.*


class NewRecordFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private val args: NewRecordFragmentArgs by navArgs()
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

        if (args.donationArgument>=0) {
            viewModel.setupEditMode(args.donationArgument)
            binding.newrecordSubmit.text = getString(R.string.donation_submitedit)
            binding.newrecordTitle.text = getString(R.string.donation_edit)
        }

        binding.editDonationType.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                requireContext().resources.getStringArray(
                    R.array.donation_array
                )
            )
        )
        binding.editDonationType.setOnItemClickListener { parent, view, position, id ->
            run {
                viewModel.setDonationType(
                    when (position) {
                        0 -> Donation.DonationType.WHOLE
                        1 -> Donation.DonationType.PLASMA
                        2 -> Donation.DonationType.PLATELETS
                        else -> Donation.DonationType.DISQUALIFIED
                    }
                ) }
        }
        binding.editDonationType.setText(
            binding.editDonationType.adapter.getItem(0).toString(),
            false
        );

        viewModel.donation.addOnPropertyChangedCallback(object :
            OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when(propertyId) {
                    BR.type-> {
                        binding.editDonationType.setText(
                            resources.getStringArray(R.array.donation_array)[viewModel.donation.getType()!!.ordinal],
                            false
                        );
                    }
                    BR.arm -> {
                        binding.editUsedArm.setText(
                            resources.getStringArray(R.array.arm_array)[viewModel.donation.getArm()!!.ordinal],
                            false
                        );
                    }
                }

            }
        })

        binding.editUsedArm.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                requireContext().resources.getStringArray(
                    R.array.arm_array
                )
            )
        )
        binding.editUsedArm.setText(binding.editUsedArm.adapter.getItem(2).toString(), false);
        binding.editUsedArm.setOnItemClickListener { parent, view, position, id ->
            run {
                viewModel.setArmType(
                    when (position) {
                        0 -> Donation.ArmType.RIGHT
                        1 -> Donation.ArmType.LEFT
                        else -> Donation.ArmType.UNKNOWN
                    }
                ) }
        }

        viewModel.completeState.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Navigation.findNavController(binding.root).popBackStack()
            }
        })

        binding.newrecordDate.setOnClickListener {
            val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
            val newFragment: DialogFragment = DatePickerDialogFragment(
                this,
                viewModel.donation.getDate() ?: Date(),
                viewModel.getMinTime(),
                viewModel.getMaxTime()
            )
            newFragment.show(ft, "dialog")
        }

        return binding.root
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.setNewDateTime(year, month, dayOfMonth)
    }
}
