package com.mczuba.blooddonorcompanion.util

import android.content.Context
import android.view.View
import androidx.cardview.widget.CardView

class FoldableCardHelper(val context: Context, val layout: View, val card: CardView, var folded: Boolean) {

    init{
        setupViews()

        card.setOnClickListener {
            folded = !folded
            setupViews()
        }
    }

    private fun setupViews() {
        layout.visibility = if (folded) View.GONE else View.VISIBLE
    }
}