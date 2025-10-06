package com.ryzen30xx.caculatorlab2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import kotlin.math.floor

class MainActivity : AppCompatActivity() {
    private lateinit var btn0: Button
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btn7: Button
    private lateinit var btn8: Button
    private lateinit var btn9: Button
    private lateinit var btnClear: Button
    private lateinit var btnDivide: Button
    private lateinit var btnMultiply: Button
    private lateinit var btnSubtract: Button
    private lateinit var btnAdd: Button
    private lateinit var btnEquals: Button
    private lateinit var btnDecimal: Button
    private lateinit var display: TextView

    private var operand1: Double? = null
    private var currentOperator: String? = null
    private var displayString: String = "0"
    private var isNewInput: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initializeViews()
        setupClickListeners()
        display.text = displayString

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initializeViews() {
        btn0 = findViewById(R.id.btn0)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)
        btnClear = findViewById(R.id.btnClear)
        btnDivide = findViewById(R.id.btnDivide)
        btnMultiply = findViewById(R.id.btnMultiply)
        btnSubtract = findViewById(R.id.btnSubtract)
        btnAdd = findViewById(R.id.btnAdd)
        btnEquals = findViewById(R.id.btnEquals)
        btnDecimal = findViewById(R.id.btnDecimal)
        display = findViewById(R.id.display)
    }

    private fun setupClickListeners() {
        // Digit buttons
        btn0.setOnClickListener { appendDigit("0") }
        btn1.setOnClickListener { appendDigit("1") }
        btn2.setOnClickListener { appendDigit("2") }
        btn3.setOnClickListener { appendDigit("3") }
        btn4.setOnClickListener { appendDigit("4") }
        btn5.setOnClickListener { appendDigit("5") }
        btn6.setOnClickListener { appendDigit("6") }
        btn7.setOnClickListener { appendDigit("7") }
        btn8.setOnClickListener { appendDigit("8") }
        btn9.setOnClickListener { appendDigit("9") }

        // Operation buttons
        btnAdd.setOnClickListener { setOperator("+") }
        btnSubtract.setOnClickListener { setOperator("-") }
        btnMultiply.setOnClickListener { setOperator("*") }
        btnDivide.setOnClickListener { setOperator("/") }

        // Other buttons
        btnDecimal.setOnClickListener { appendDecimal() }
        btnEquals.setOnClickListener { calculateResult() }
        btnClear.setOnClickListener { clearAll() }
    }

    private fun appendDigit(digit: String) {
        if (isNewInput) {
            displayString = digit
            isNewInput = false
        } else {
            // Prevent appending 0 to "0" to avoid "00"
            if (displayString != "0" || digit != "0") {
                displayString += digit
            }
        }
        display.text = displayString
    }

    private fun appendDecimal() {
        if (isNewInput) {
            displayString = "0."
            isNewInput = false
        } else if (!displayString.contains(".")) {
            displayString += "."
        }
        display.text = displayString
    }

    private fun setOperator(op: String) {
        val currentValue = displayString.toDoubleOrNull() ?: 0.0

        if (operand1 == null) {
            // First operand
            operand1 = currentValue
        } else if (currentOperator != null && !isNewInput) {
            // Chain operation: calculate previous result only if second input has started
            val secondValue = currentValue
            val result = performCalculation(operand1!!, currentOperator!!, secondValue)
            operand1 = result
            // Do not update display here to avoid flashing; we'll clear it below
        }
        // If isNewInput is true (no second input started), just change the operator without calculating

        currentOperator = op
        // Clean the screen immediately to "0" and prepare for next number input
        displayString = "0"
        display.text = displayString
        isNewInput = true
    }

    private fun calculateResult() {
        if (operand1 != null && currentOperator != null && !isNewInput) {
            val secondValue = displayString.toDoubleOrNull() ?: 0.0
            val result = performCalculation(operand1!!, currentOperator!!, secondValue)
            displayString = formatResult(result)
            display.text = displayString
            // Reset for new calculation
            operand1 = null
            currentOperator = null
            isNewInput = true
        } else if (operand1 != null && currentOperator != null && isNewInput) {
            // If equals pressed right after operator (no second number), treat as operating with 0
            val result = performCalculation(operand1!!, currentOperator!!, 0.0)
            displayString = formatResult(result)
            display.text = displayString
            operand1 = null
            currentOperator = null
            isNewInput = true
        }
        // If no operator or new input, do nothing (equals on a single number just shows it)
    }

    private fun performCalculation(a: Double, op: String, b: Double): Double {
        return when (op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> if (b != 0.0) a / b else Double.POSITIVE_INFINITY  // Trigger error handling
            else -> a
        }
    }

    private fun formatResult(value: Double): String {
        return if (value.isInfinite() || value.isNaN()) {
            "Error"
        } else {
            val longValue = floor(value).toLong()
            if (value == longValue.toDouble()) {
                longValue.toString()
            } else {
                value.toString()
            }
        }
    }

    private fun clearAll() {
        displayString = "0"
        operand1 = null
        currentOperator = null
        isNewInput = true
        display.text = displayString
    }
}