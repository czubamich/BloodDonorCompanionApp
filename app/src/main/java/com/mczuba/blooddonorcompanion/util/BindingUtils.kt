package com.mczuba.blooddonorcompanion.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter


object BindingUtils {
    @JvmStatic
    @BindingAdapter("android:text")
    fun setFloat(view: TextView, value: Float?) {
        if (value == null) return
        view.text = value.toString()
    }
    @JvmStatic
    @InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
    fun getFloat(view: TextView): Float? {
        return view.text.toString().toFloatOrNull()
    }
    @JvmStatic
    @BindingAdapter("android:text")
    fun setInt(view: TextView, value: Int?) {
        if (value == null) return
        view.text = value.toString()
    }
    @JvmStatic
    @InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
    fun getInt(view: TextView): Int? {
        return view.text.toString().toIntOrNull()
    }
}

