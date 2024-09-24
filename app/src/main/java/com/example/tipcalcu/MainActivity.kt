package com.example.tipcalcu

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tipcalcu.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Set click listener for the button
        binding.calcButton.setOnClickListener { calculateTip() }

        // Adjust the insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun calculateTip() {
        // Get the cost of service and handle potential null or empty values
        val costInput = binding.tipsForService.text.toString()
        val cost: Double = costInput.toDoubleOrNull() ?: run {
            binding.tipResult.text = "Invalid cost"
            return
        }

        // Get the selected tip percentage
        val chosenId: Int = binding.tipOption.checkedRadioButtonId
        val tipPercentage: Double = when (chosenId) {
            R.id.option_ten_percent -> 0.1
            R.id.option_fifteen_percent -> 0.15
            R.id.option_twenty_percent -> 0.2
            else -> 0.05
        }

        // Calculate the tip
        var tip: Double = cost * tipPercentage
        val roundUp: Boolean = binding.roundTip.isChecked

        // Round up if selected
        if (roundUp) {
            tip = ceil(tip)
        }

        val currency :String = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = String.format("Tip Amount: $%.2f", tip)
    }
}
