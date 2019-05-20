package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val bundle: Bundle? = intent.extras
        val bmi = bundle!!.getString("EXTRA_BMI")
        val bmiDesc = bundle.getString("EXTRA_BMI_DESC")

        bmiTV.text = bmi

        when(bmiDesc) {
            "Underweight" -> {
                bmiIV.setImageResource(R.drawable.underweight)
                bmiDescTV.setText(R.string.info_desc_underweight)
                bmiTV.setTextColor(ContextCompat.getColor(baseContext, R.color.verdifris))
            }
            "Normal" -> {
                bmiIV.setImageResource(R.drawable.normal)
                bmiDescTV.setText(R.string.info_desc_normal)
                bmiTV.setTextColor(ContextCompat.getColor(baseContext, R.color.lapisLazuli))
            }
            "Overweight" -> {
                bmiIV.setImageResource(R.drawable.overweight)
                bmiDescTV.setText(R.string.info_desc_overweight)
                bmiTV.setTextColor(ContextCompat.getColor(baseContext, R.color.titian))
            }
            "Obese" -> {
                bmiIV.setImageResource(R.drawable.obese)
                bmiDescTV.setText(R.string.info_desc_obese)
                bmiTV.setTextColor(ContextCompat.getColor(baseContext, R.color.copper))
            }
            "Extremely Obese" -> {
                bmiIV.setImageResource(R.drawable.extremely_obese)
                bmiDescTV.setText(R.string.info_desc_extremely_obese)
                bmiTV.setTextColor(ContextCompat.getColor(baseContext, R.color.wineBerry))
            }
        }
    }
}
