package ru.shmayhel.currencyconventer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerAdapter = ArrayAdapter(this, R.layout.spinner_custom, resources.getStringArray(R.array.currency_list))
        currencySpinner.adapter = spinnerAdapter
        currencySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position == 0)
                    secondCurrencyTextView.text = resources.getStringArray(R.array.currency_list)[1]
                else
                    secondCurrencyTextView.text = resources.getStringArray(R.array.currency_list)[0]
                updateNumber()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        firstCurrencyNumberInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateNumber()
            }
        })
    }

    private fun updateNumber() {
        try {
            val firstCurrencyNumber = firstCurrencyNumberInput.text.toString().toDouble()
            secondCurrencyNumberView.text =
                String.format("%.2f", convert(firstCurrencyNumber, currencySpinner.selectedItemPosition))
        }
        catch (nfe: NumberFormatException) {}
    }

    private fun convert(firstCurrencyNumber: Double, firstCurrencyID: Int): Double {
        if(firstCurrencyID == 0)
            return firstCurrencyNumber / 74
        else
            return firstCurrencyNumber * 74
    }
}