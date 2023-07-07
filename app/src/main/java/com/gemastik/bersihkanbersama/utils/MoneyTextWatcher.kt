package com.gemastik.bersihkanbersama.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.ref.WeakReference
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

class MoneyTextWatcher(
    editText: EditText
) : TextWatcher {
    private val locale = Locale("id", "ID")
    private val formatter = NumberFormat.getCurrencyInstance(locale) as DecimalFormat
    private val editTextWeakReference: WeakReference<EditText>

    init {
        editTextWeakReference = WeakReference(editText)
        formatter.apply {
            maximumFractionDigits = 0
            roundingMode = RoundingMode.FLOOR
        }

        val symbol = DecimalFormatSymbols(locale)
        symbol.currencySymbol = symbol.currencySymbol + " "
        formatter.decimalFormatSymbols = symbol
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // Do nothing.
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // Do nothing.
    }

    override fun afterTextChanged(s: Editable?) {
        val editText = editTextWeakReference.get()
        if (editText == null || editText.text.toString().isEmpty()) return
        editText.removeTextChangedListener(this)

        val parsed = Utils.parseCurrencyString(editText.text.toString())
        val formatted = formatter.format(parsed)

        editText.apply {
            setText(formatted)
            setSelection(formatted.length)
        }
        editText.addTextChangedListener(this)
    }
}