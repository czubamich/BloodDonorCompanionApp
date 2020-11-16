package com.mczuba.blooddonorcompanion.vm.greeting

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.data.User
import com.mczuba.blooddonorcompanion.databinding.FragmentGreetRegisterBinding
import kotlinx.coroutines.launch

class GreetRegisterFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private val viewModel by lazy {
        ViewModelProvider(this).get(GreetRegisterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGreetRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.fragment = this

        binding.editGender.setAdapter(
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, requireContext().resources.getStringArray(
                R.array.gender_array))
        )

        binding.editBloodType.setAdapter (
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, User.BloodType.values())
        )
        binding.editBloodType.setOnItemClickListener { parent, view, position, id ->
            run {
                viewModel.setBloodType(User.BloodType.values()[position])
            }
        }

        return binding.root
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.setNewDateTime(year, month, dayOfMonth)
    }

    fun click() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.submit()
            val directions = GreetRegisterFragmentDirections.actionGreetMakeProfileFragmentToGreetUserFragment()
            Navigation.findNavController(requireView()).navigate(directions)
        }
    }
}