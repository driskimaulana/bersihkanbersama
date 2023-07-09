package com.gemastik.bersihkanbersama.ui.donate

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.gemastik.bersihkanbersama.R

@SuppressLint("SetTextI18n")
class DonasiCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var imageView: ImageView
    private var nameTextView: TextView
    private var priceTextView: TextView
    private var counterTextView: TextView
    private var decreaseButton: TextView
    private var increaseButton: TextView
    private var counter = 0

    private var imageCardDonasi: Drawable? = null
    private var donateName: String? = null
    private var donatePrice = 0

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> get() = _totalPrice

    init {
        LayoutInflater.from(context).inflate(R.layout.sample_donasi_card_view, this, true)

        imageView = findViewById(R.id.imageView)
        nameTextView = findViewById(R.id.nameTextView)
        priceTextView = findViewById(R.id.priceTextView)
        counterTextView = findViewById(R.id.counterTextView)
        decreaseButton = findViewById(R.id.decreaseButton)
        increaseButton = findViewById(R.id.increaseButton)

        decreaseButton.setOnClickListener {
            if (counter > 0) {
                counter--
                counterTextView.text = counter.toString()
                updateTotalPrice()
            }
        }

        increaseButton.setOnClickListener {
            counter++
            counterTextView.text = counter.toString()
            updateTotalPrice()
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DonasiCardView)
        imageCardDonasi = typedArray.getDrawable(R.styleable.DonasiCardView_imageCardDonasi)
        donateName = typedArray.getString(R.styleable.DonasiCardView_donateName)
        donatePrice = typedArray.getInt(R.styleable.DonasiCardView_donatePrice, 0)
        typedArray.recycle()

        imageView.setImageDrawable(imageCardDonasi)
        nameTextView.text = donateName
        priceTextView.text = "Rp $donatePrice"
        counterTextView.text = counter.toString()
    }

    private fun updateTotalPrice() {
        _totalPrice.value = counter * donatePrice
    }
}
