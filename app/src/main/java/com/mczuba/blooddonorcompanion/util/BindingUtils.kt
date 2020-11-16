package com.mczuba.blooddonorcompanion.util

import android.widget.TextView
import androidx.databinding.*
import com.google.android.material.chip.ChipGroup


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

@InverseBindingMethods(InverseBindingMethod(type = ChipGroup::class, attribute = "android:checkedButton", method = "getCheckedChipId"))
class ChipGroupBindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter("android:checkedButton")
        fun setCheckedChip(view: ChipGroup?, id: Int) {
            if (id != view?.checkedChipId) {
                view?.check(id)
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["android:onCheckedChanged", "android:checkedButtonAttrChanged"], requireAll = false)
        fun setChipsListeners(view: ChipGroup?, listener: ChipGroup.OnCheckedChangeListener?,
                              attrChange: InverseBindingListener?) {
            if (attrChange == null) {
                view?.setOnCheckedChangeListener(listener)
            } else {
                view?.setOnCheckedChangeListener { group, checkedId ->
                    listener?.onCheckedChanged(group, checkedId)
                    attrChange.onChange()
                }
            }
        }
    }
}

