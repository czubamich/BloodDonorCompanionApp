package com.mczuba.blooddonorcompanion.util

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import java.util.*


class DatePickerDialogFragment(callback: Fragment, val date: Date?) : DialogFragment() {
    private val mFragment: Fragment = callback


    //Todo: Change to LocalDate
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val cal = Calendar.getInstance(TimeZone.getDefault())
        cal.setTime(date)
        val dialog = DatePickerDialog(requireContext(), mFragment as OnDateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
        dialog.datePicker.minDate = Date(cal.get(Calendar.YEAR)-1900-5, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).time
        dialog.datePicker.maxDate = Date(cal.get(Calendar.YEAR)-1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).time
        return dialog
    }

}