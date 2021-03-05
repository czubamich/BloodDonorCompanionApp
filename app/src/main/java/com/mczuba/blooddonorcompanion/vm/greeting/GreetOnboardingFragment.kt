package com.mczuba.blooddonorcompanion.vm.greeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.mczuba.blooddonorcompanion.R
import com.mczuba.blooddonorcompanion.databinding.FragmentGreetOnboardingBinding
import kotlinx.android.synthetic.main.fragment_greet_final.view.*

class GreetOnBoardingFragment : Fragment() {
    companion object {
        private const val ARG_POSITION = "ARG_POSITION"

        fun getInstance(position: Int) = GreetOnBoardingFragment().apply {
            arguments = bundleOf(com.mczuba.blooddonorcompanion.vm.greeting.GreetOnBoardingFragment.Companion.ARG_POSITION to position)
        }
    }

    private lateinit var binding: FragmentGreetOnboardingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentGreetOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val position = requireArguments().getInt(ARG_POSITION)
        //val onBoardingTitles = requireContext().resources.getStringArray(R.array.on_boarding_titles)
        //val onBoardingTexts = requireContext().resources.getStringArray(R.array.on_boarding_texts)
        //val onBoardingImages = getOnBoardAssetsLocation()
        //with(binding) {
        //    onBoardingImage.setImageFromRaw(onBoardingImages[position])
        //    onBoardingTextTitle.text = onBoardingTitles[position]
        //    onBoardingTextMsg.text = onBoardingTexts[position]
        //}
    }

    /*
    private fun getOnBoardAssetsLocation(): List<Int> {
        val onBoardAssets: MutableList<Int> = ArrayList()
        onBoardAssets.add(R.raw.on_board_img1_land)
        onBoardAssets.add(R.raw.on_board_img2_land)
        onBoardAssets.add(R.raw.on_board_img3_land)
        return onBoardAssets
    }*/
}