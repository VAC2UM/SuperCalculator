package com.itproger.supercalculator

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var editText: TextView
    private lateinit var resultViewText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        resultViewText = findViewById(R.id.resultTextView)

        // список всех кнопок
        val buttonIds = arrayOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
            R.id.buttonDot, R.id.buttonAdd, R.id.buttonMinus, R.id.buttonMult,
            R.id.buttonDivide, R.id.buttonBr1, R.id.buttonBr2, R.id.backspace,
            R.id.AC, R.id.buttonEquals
        )

        // покраска кнопок в определенный цвет
        for (buttonId in buttonIds) {
            val button = findViewById<Button>(buttonId)
            if (buttonId in arrayOf(
                    R.id.buttonDot,
                    R.id.button0,
                    R.id.button1,
                    R.id.button2,
                    R.id.button3,
                    R.id.button4,
                    R.id.button5,
                    R.id.button6,
                    R.id.button7,
                    R.id.button8,
                    R.id.button9
                )
            ) {
                val color = ContextCompat.getColor(this, R.color.navi)
                val drawable = ContextCompat.getDrawable(this, R.drawable.rounded_button)
                drawable?.setTint(color)

                button.background = drawable
            }
            if (buttonId in arrayOf(
                    R.id.buttonEquals,
                    R.id.buttonMinus,
                    R.id.buttonMult,
                    R.id.buttonDivide,
                    R.id.buttonAdd
                )
            ) {
                val color = ContextCompat.getColor(this, R.color.yellow)
                val drawable = ContextCompat.getDrawable(this, R.drawable.rounded_button)
                drawable?.setTint(color)

                button.background = drawable
            }
            if (buttonId in arrayOf(R.id.backspace, R.id.AC)) {
                val color = ContextCompat.getColor(this, R.color.red)
                val drawable = ContextCompat.getDrawable(this, R.drawable.rounded_button)
                drawable?.setTint(color)

                button.background = drawable
            }
            button.setOnClickListener { onButtonClick(it) }

        }
        findViewById<Button>(R.id.buttonEquals).setOnClickListener { onEqualsButtonClick() }
    }


    // Функция для обработки нажатия на кнопки
    private fun onButtonClick(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()
        val currentText = editText.text.toString()

        when (buttonText) {
            "AC" -> {
                editText.setText("")
                resultViewText.setText("0")
            }

            "C" -> {
                if (currentText.isNotEmpty()) {
                    editText.setText(currentText.dropLast(1))
                }
            }

            else -> editText.setText(currentText + buttonText)
        }
    }

    // Функция для обработки нажатия на кнопку "равно"
    private fun onEqualsButtonClick() {
        val currentText = editText.text.toString()
        try {
            val result = evaluateExpression(currentText)
            if (result % 1 == 0.0f) {
                val resultInt = result.toInt()
                resultViewText.text =
                    Editable.Factory.getInstance().newEditable(resultInt.toString())
            } else {
                val resultFloat = result.toFloat()
                resultViewText.text =
                    Editable.Factory.getInstance().newEditable(resultFloat.toString())
            }
        } catch (e: Exception) {
            editText.setText("Error: ${e.message}")
        }
    }

    // Функция для вычисления математического выражения
    private fun evaluateExpression(expression: String): Float {
        if (expression.isEmpty()) {
            return 0.0f
        }
        return ExpressionBuilder(expression).build().evaluate().toFloat()
    }
}