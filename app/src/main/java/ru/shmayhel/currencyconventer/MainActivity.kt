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
        firstCurrencySpinner.adapter = spinnerAdapter
        secondCurrencySpinner.adapter = spinnerAdapter
        firstCurrencySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateNumber()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        secondCurrencySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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
            val secondCurrencyNumber = convert(firstCurrencyNumber, firstCurrencySpinner.selectedItemPosition, secondCurrencySpinner.selectedItemPosition)
            if(secondCurrencyNumber < 0.1)
                secondCurrencyNumberView.text = String.format("%.4f", secondCurrencyNumber)
            else
                secondCurrencyNumberView.text = String.format("%.2f", secondCurrencyNumber)
        }
        catch (nfe: NumberFormatException) {}
    }

    private fun convert(firstCurrencyNumber: Double, firstCurrencyID: Int, secondCurrencyID: Int): Double {
        val exchangeRates: Array<Double> = arrayOf(69.0, 1.0, 0.89, 0.79, 0.96, 27.0, 24000.0)
        return firstCurrencyNumber * exchangeRates[secondCurrencyID] / exchangeRates[firstCurrencyID]
    }

    fun swapCurrencies(view: View) {
        var swapPos = secondCurrencySpinner.selectedItemPosition
        secondCurrencySpinner.setSelection(firstCurrencySpinner.selectedItemPosition)
        firstCurrencySpinner.setSelection(swapPos)
    }
}