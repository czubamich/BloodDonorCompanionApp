package com.mczuba.blooddonorcompanion.vm.greeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mczuba.blooddonorcompanion.databinding.FragmentGreetWelcomeBinding

class GreetWelcomeFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(GreetWelcomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGreetWelcomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.fragment = this

        return binding.root
    }

    fun click() {
        val directions = GreetWelcomeFragmentDirections.actionGreetingFragmentToGreetMakeProfileFragment()
        Navigation.findNavController(requireView()).navigate(directions)
    }
}