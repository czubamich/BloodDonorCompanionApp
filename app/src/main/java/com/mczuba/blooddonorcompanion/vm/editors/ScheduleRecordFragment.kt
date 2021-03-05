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
import com.mczuba.blooddonorcompanion.BR
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.data.models.Donation
import com.mczuba.blooddonorcompanion.data.models.Schedule
import com.mczuba.blooddonorcompanion.databinding.FragmentRecordScheduleBinding
import com.mczuba.blooddonorcompanion.util.DatePickerDialogFragment
import java.util.*


class ScheduleRecordFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private val viewModel by lazy {
        ViewModelProvider(this).get(ScheduleRecordViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecordScheduleBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        binding.editDonationType.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                requireContext().resources.getStringArray(
                    R.array.donation_array
                ).dropLast(1)
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

        binding.editNotification.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                requireContext().resources.getStringArray(
                    R.array.notification_array
                )
            )
        )
        binding.editNotification.setOnItemClickListener { parent, view, position, id ->
            run {
                viewModel.setNotification(
                    when (position) {
                        0 -> Schedule.NotificationSetting.WEEK_BEFORE
                        1 -> Schedule.NotificationSetting.THREE_DAYS_BEFORE
                        2 -> Schedule.NotificationSetting.DAY_BEFORE
                        else -> Schedule.NotificationSetting.NO_NOTIFICATION
                    }
                ) }
        }
        binding.editNotification.setText(
            binding.editNotification.adapter.getItem(3).toString(),
            false
        );

        viewModel.schedule.addOnPropertyChangedCallback(object :
            OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when(propertyId) {
                    BR.type-> {
                        binding.editDonationType.setText(
                            resources.getStringArray(R.array.donation_array)[viewModel.schedule.getType()!!.ordinal],
                            false
                        );
                    }
                    BR.notification-> {
                        binding.editNotification.setText(
                            resources.getStringArray(R.array.notification_array)[viewModel.schedule.getNotification()!!.ordinal],
                            false
                        );
                    }
                }
            }
        })

        viewModel.completeState.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Navigation.findNavController(binding.root).popBackStack()
            }
        })

        binding.openDatePicker.setOnClickListener {
            val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
            val newFragment: DialogFragment = DatePickerDialogFragment(
                this,
                viewModel.schedule.getDate() ?: Date(),
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
