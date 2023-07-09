package com.gemastik.bersihkanbersama.ui.donate

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.gemastik.bersihkanbersama.R

class DonasiCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var imageView: ImageView
    private var nameTextView: TextView
    private var counterTextView: TextView
    private var decreaseButton: TextView
    private var increaseButton: TextView
    private var counter = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.sample_donasi_card_view, this, true)

        imageView = findViewById(R.id.imageView)
        nameTextView = findViewById(R.id.nameTextView)
        counterTextView = findViewById(R.id.counterTextView)
        decreaseButton = findViewById(R.id.decreaseButton)
        increaseButton = findViewById(R.id.increaseButton)

        decreaseButton.setOnClickListener {
            if (counter > 0) {
                counter--
                counterTextView.text = counter.toString()
            }
        }

        increaseButton.setOnClickListener {
            counter++
            counterTextView.text = counter.toString()
        }

        val imageResId = R.drawable.ic_water_824239
        val nameText = "Item Name"
        val counterText = "0"

        imageView.setImageResource(imageResId)
        nameTextView.text = nameText
        counterTextView.text = counterText
    }

    fun setCounter(counter: Int) {
        this.counter = counter
        counterTextView.text = counter.toString()
    }

    fun getCounter(): Int {
        return counter
    }
}
