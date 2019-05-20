package com.example.myapplication

import android.content.Context
import android.content.Intent
import logic.BMIForKgCm
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import logic.BMIForLbIn

class MainActivity : AppCompatActivity() {

    private var otherUnits = false
    private var calculationList: ArrayList<Calculation>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadCalculatedBMI()

        buttonBMI.setOnClickListener {
            val weight: Int? = weightET.text.toString().toIntOrNull()
            val height: Int? = heightET.text.toString().toIntOrNull()

            var flagOne = true
            var flagTwo = true

            if(weight == null || weight <= 0) {
                weightET.error = "The weight should be a total positive number!"
                flagOne = false
            }
            if(height == null || height <= 0) {
                heightET.error = "The height should be a total positive number!"
                flagTwo = false
            }
            if(flagOne && flagTwo){
                calculateBMI(weight!!, height!!)
            }
        }

        buttonInfo.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            intent.putExtra("EXTRA_BMI", bmiTV.text.toString())
            intent.putExtra("EXTRA_BMI_DESC", bmiDescTV.text.toString())
            intent.putExtra("EXTRA_COLOR", bmiTV.currentTextColor)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.about_me -> startActivity(Intent(this, AboutMeActivity::class.java))
            R.id.change_units -> changeUnits()
            R.id.recycler_view -> {
                if (calculationList!!.isEmpty()) {
                    Toast.makeText(this, "History not available!", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this, RecyclerViewActivity::class.java)
                    intent.putParcelableArrayListExtra("EXTRA_LIST", calculationList)
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeUnits() {
        otherUnits = !otherUnits

        Toast.makeText(baseContext, "Units changed", Toast.LENGTH_SHORT).show()

        when(otherUnits) {
            true -> {
                weightTV.setText(R.string.main_units_weight_lb)
                heightTV.setText(R.string.main_units_height_in)
            }
            false -> {
                weightTV.setText(R.string.main_units_weight_kg)
                heightTV.setText(R.string.main_units_height_cm)
            }
        }
    }

    private fun saveCalculatedBMI() {
        val sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("CALCULATION_LIST", Gson().toJson(calculationList))
        editor.apply()
    }

    private fun loadCalculatedBMI() {
        val sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("CALCULATION_LIST", null)
        val type = object: TypeToken<ArrayList<Calculation>>(){}.type
        calculationList = Gson().fromJson<ArrayList<Calculation>>(json, type)

        if (calculationList == null) {
            calculationList = ArrayList()
        }
    }

    private fun calculateBMI(weight: Int, height: Int) {
        val weightUnit: String
        val heightUnit: String
        val result: Double

        when(otherUnits) {
            true -> {
                result = "%.2f".format(BMIForLbIn(weight, height).calculateBmi()).toDouble()
                weightUnit = "[lb]"
                heightUnit = "[in]"
            }
            false -> {
                result = "%.2f".format(BMIForKgCm(weight, height).calculateBmi()).toDouble()
                weightUnit = "[kg]"
                heightUnit = "[cm]"
            }
        }

        val calculation = Calculation(weight, height, result, weightUnit, heightUnit)

        calculationList!!.add(calculation)

        if(calculationList!!.size > 10) {
            calculationList!!.removeAt(0)
        }

        saveCalculatedBMI()

        bmiTV.text = result.toString()

        bmiTV.visibility = View.VISIBLE
        bmiDescTV.visibility = View.VISIBLE
        buttonInfo.visibility = View.VISIBLE

        when {
            result < 18.5 -> {
                bmiDescTV.setText(R.string.global_underweight)
                bmiTV.setTextColor(ContextCompat.getColor(baseContext, R.color.verdifris))
                bmiDescTV.setTextColor(ContextCompat.getColor(baseContext, R.color.verdifris))
            }
            18.5 <= result && result < 25.0 -> {
                bmiDescTV.setText(R.string.global_normal)
                bmiTV.setTextColor(ContextCompat.getColor(baseContext, R.color.lapisLazuli))
                bmiDescTV.setTextColor(ContextCompat.getColor(baseContext, R.color.lapisLazuli))
            }
            25.0 <= result && result < 30.0 -> {
                bmiDescTV.setText(R.string.global_overweight)
                bmiTV.setTextColor(ContextCompat.getColor(baseContext, R.color.titian))
                bmiDescTV.setTextColor(ContextCompat.getColor(baseContext, R.color.titian))
            }
            30.0 <= result && result < 35.0 -> {
                bmiDescTV.setText(R.string.global_obese)
                bmiTV.setTextColor(ContextCompat.getColor(baseContext, R.color.copper))
                bmiDescTV.setTextColor(ContextCompat.getColor(baseContext, R.color.copper))
            }
            35.0 <= result -> {
                bmiDescTV.setText(R.string.global_extremely_obese)
                bmiTV.setTextColor(ContextCompat.getColor(baseContext, R.color.wineBerry))
                bmiDescTV.setTextColor(ContextCompat.getColor(baseContext, R.color.wineBerry))
            }
        }
    }
}