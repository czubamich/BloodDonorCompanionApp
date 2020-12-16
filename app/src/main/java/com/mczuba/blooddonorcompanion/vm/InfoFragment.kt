package com.mczuba.blooddonorcompanion.vm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.databinding.FragmentInfoBinding
import com.mczuba.blooddonorcompanion.util.FoldableLayoutHelper

class InfoFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(InfoViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentInfoBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        val buttonExpand = binding.root.findViewById<MaterialButton>(R.id.button_Expand)
        val layoutExpand = binding.root.findViewById<View>(R.id.layout_details)
        FoldableLayoutHelper(requireContext(), layoutExpand, buttonExpand, true)

        val buttonExpand2 = binding.root.findViewById<MaterialButton>(R.id.button_Expand2)
        val layoutExpand2 = binding.root.findViewById<View>(R.id.layoutDetails2)
        FoldableLayoutHelper(requireContext(), layoutExpand2, buttonExpand2, true)

        return binding.root
    }
}