package com.mczuba.blooddonorcompanion.util

import android.content.Context
import android.view.View
import android.widget.Button
import com.mczuba.blooddonorcompanion.R

class FoldableLayoutHelper(val context: Context, val layout: View, val button: Button, var folded: Boolean) {

    init{
        setupViews()

        button.setOnClickListener {
            folded = !folded
            setupViews()
        }
    }

    fun fold()
    {
        folded = true;
        setupViews()
    }

    fun unfold()
    {
        folded = false;
        setupViews()
    }

    private fun setupViews() {
        layout.visibility = if (folded) View.GONE else View.VISIBLE
        button.text = if (folded)
            context.getString(R.string.control_expand)
        else
            context.getString(R.string.control_shrink)
    }
}