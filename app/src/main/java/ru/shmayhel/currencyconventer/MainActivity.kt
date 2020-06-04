package ru.shmayhel.currencyconventer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstCurrencyNumberInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                try {
                    secondCurrencyNumberView.text =
                        String.format("%.2f", convert(firstCurrencyNumberInput.text.toString().toDouble()))
                }
                catch (nfe: NumberFormatException) {}
            }
        })
    }

    private fun convert(firstCurrencyNumber: Double): Double {
        return firstCurrencyNumber / 74
    }
}