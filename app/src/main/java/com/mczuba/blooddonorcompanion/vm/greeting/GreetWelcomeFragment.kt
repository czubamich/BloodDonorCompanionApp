package com.mczuba.blooddonorcompanion.vm.greeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mczuba.blooddonorcompanion.databinding.FragmentGreetWelcomeBinding

class GreetWelcomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = com.mczuba.blooddonorcompanion.databinding.FragmentGreetWelcomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.fragment = this

        //val numberOfScreens = resources.getStringArray(R.array.on_boarding_titles).size
        //binding.onBoardingMainContainer.makeStatusBarTransparent()
        //val onBoardingAdapter = OnBoardingAdapter(this, numberOfScreens)
        //binding.onBoardingViewPager.adapter = onBoardingAdapter
        //binding.onBoardingViewPager.registerOnPageChangeCallback(onBoardingPageChangeCallback)

        return binding.root
    }
    /*
    private fun updateCircleMarker(binding: ActivityOnBoardingBinding, position: Int) {
        when (position) {
            0 -> {
                binding.onBoardingInitialCircle.background = getDrawable(R.drawable.bg_green_circle)
                binding.onBoardingMiddleCircle.background = getDrawable(R.drawable.bg_gray_circle)
                binding.onBoardingLastCircle.background = getDrawable(R.drawable.bg_gray_circle)
            }
            1 -> {
                binding.onBoardingInitialCircle.background = getDrawable(R.drawable.bg_gray_circle)
                binding.onBoardingMiddleCircle.background = getDrawable(R.drawable.bg_green_circle)
                binding.onBoardingLastCircle.background = getDrawable(R.drawable.bg_gray_circle)
            }
            2 -> {
                binding.onBoardingInitialCircle.background = getDrawable(R.drawable.bg_gray_circle)
                binding.onBoardingMiddleCircle.background = getDrawable(R.drawable.bg_gray_circle)
                binding.onBoardingLastCircle.background = getDrawable(R.drawable.bg_green_circle)
            }
        }
    }
    */

    fun click() {
        val directions = GreetWelcomeFragmentDirections.actionGreetingFragmentToGreetMakeProfileFragment()
        Navigation.findNavController(requireView()).navigate(directions)
    }
}