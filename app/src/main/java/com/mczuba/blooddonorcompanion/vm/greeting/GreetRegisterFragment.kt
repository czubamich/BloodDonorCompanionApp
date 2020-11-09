package com.mczuba.blooddonorcompanion.vm.greeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.mczuba.blooddonorcompanion.databinding.FragmentGreetRegisterBinding
import kotlinx.coroutines.launch

class GreetRegisterFragment : Fragment() {

    companion object {
        fun newInstance() = GreetRegisterFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGreetRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.fragment = this

        return binding.root
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(GreetRegisterViewModel::class.java)
    }

    fun click() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.submit()
            val directions = GreetRegisterFragmentDirections.actionGreetMakeProfileFragmentToGreetUserFragment()
            Navigation.findNavController(requireView()).navigate(directions)
        }
    }
}