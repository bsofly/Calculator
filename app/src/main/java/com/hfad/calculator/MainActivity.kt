package com.hfad.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hfad.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var registers = FloatArray(3) { Float.NaN }
    private var operation: String = ""
    private val displayreg = Display()
    private var concat = true
    private var opBtnHit = false
    private var clearhist = false
    private var displayinfo: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.info.text = ""
        displayreg.setString("0")
        updateScreen()

        binding.button0.setOnClickListener{ inputdigits('0'); updateScreen() }
        binding.button1.setOnClickListener{ inputdigits('1'); updateScreen() }
        binding.button2.setOnClickListener{ inputdigits('2'); updateScreen() }
        binding.button3.setOnClickListener{ inputdigits('3'); updateScreen() }
        binding.button4.setOnClickListener{ inputdigits('4'); updateScreen() }
        binding.button5.setOnClickListener{ inputdigits('5'); updateScreen() }
        binding.button6.setOnClickListener{ inputdigits('6'); updateScreen() }
        binding.button7.setOnClickListener{ inputdigits('7'); updateScreen() }
        binding.button8.setOnClickListener{ inputdigits('8'); updateScreen() }
        binding.button9.setOnClickListener{ inputdigits('9'); updateScreen() }
        binding.dot.setOnClickListener{ inputdigits('.'); updateScreen() }
        binding.plusMinus.setOnClickListener { negate(); updateScreen() }
        binding.backspace.setOnClickListener { backspace(); updateScreen() }
        binding.clear.setOnClickListener { clearall(); updateScreen() }
        binding.clearEntry.setOnClickListener { clearEntry(); updateScreen() }
        binding.plus.setOnClickListener { operator("+"); updateScreen() }
        binding.minus.setOnClickListener { operator("-"); updateScreen() }
        binding.times.setOnClickListener { operator("*"); updateScreen() }
        binding.divide.setOnClickListener { operator("/"); updateScreen() }
        binding.equals.setOnClickListener { equalsOp(); updateScreen() }
    }

    private fun negate() {
        displayreg.negate()
        opBtnHit = false
    }

    private fun backspace() {
        displayreg.backspace()
        opBtnHit = false
    }

    private fun clearEntry() {
        displayreg.setString("0")
        opBtnHit = false
    }

    private fun clearall() {
        displayreg.setString("0")
        concat = true
        clear()
        opBtnHit = false
    }

    private fun equalsOp() {
        concat = false
        executeEqual()
        opBtnHit = false
    }

    private fun inputdigits(digitIn: Char) {
        displayreg.inputbutton(digitIn, concat)
        concat = true
        opBtnHit = false
    }

    private fun clear() {
        registers.fill(Float.NaN, 0)
        displayinfo = ""
        operation = ""
        clearhist = false
    }

    private fun operator(requestedop: String) {
        if (operation != "" && !displayreg.getFloat().isNaN() && !clearhist && !opBtnHit) {
            executeEqual()
        }
        operation = requestedop
        registers[0] = displayreg.getFloat()
        infodisplay()
        logAll()
        concat = false
        opBtnHit = true
    }

    private fun executeEqual() {
        registers[1] = displayreg.getFloat()
        execute()
        updateScreen()
        logAll()
    }

    private fun execute() {
        if (!registers[0].isNaN() && !registers[1].isNaN() && operation != "") {
            when (operation) {
                "+" -> registers[2] = registers[0] + registers[1]
                "-" -> registers[2] = registers[0] - registers[1]
                "*" -> registers[2] = registers[0] * registers[1]
                "/" -> registers[2] = registers[0] / registers[1]
            }
            infodisplay()
            logAll()
            registers[0] = registers[2]
            displayreg.setFloat(registers[2])
        }
    }

    @SuppressLint("SetTextI18n")
    private fun infodisplay(){
        if (!registers[1].isNaN() && !clearhist && !opBtnHit) {
            displayinfo = "${registers[0].toString()} $operation " +
                    "${registers[1].toString()} = "
            clearhist = true
        } else {
            displayinfo = "${registers[0].toString()} $operation"
            clearhist = false
        }
    }

    private fun updateScreen() {
        binding.display.text = displayreg.getString()
        binding.info.text = displayinfo
    }

    fun logAll() {
        Log.d("CalculatorDbg", "binding.display.text: ${binding.display.text}")
        Log.d("CalculatorDbg", "displayreg: ${displayreg.getString()}")
        Log.d("CalculatorDbg", "register0: ${registers[0]}")
        Log.d("CalculatorDbg", "register1: ${registers[1]}")
        Log.d("CalculatorDbg", "operation: ${operation}")
        Log.d("CalculatorDbg", "register2: ${registers[2]}")
        Log.d("CalculatorDbg", "concat:  $concat")
        Log.d("CalculatorDbg", "clearhist:  $clearhist")
        Log.d("CalculatorDbg", "opBtnHit: $opBtnHit")
        Log.d("CalculatorDbg", "binding.info.text: ${binding.info.text}")
        Log.d("CalculatorDbg", "   ")
    }
}

class Display {
    private var displayregister: String = ""

    fun setString(registerString: String) {
        displayregister = registerString
    }

    fun setFloat(registerFloat: Float) {
        displayregister = registerFloat.toString()
        if (displayregister.slice(displayregister.length-2 until displayregister.length)
            ==".0") {
            displayregister = displayregister.slice(0 .. displayregister.length-3)
        }
    }

    fun inputbutton(digit: Char, concatflag: Boolean) {
        if ((displayregister.equals("0") && digit != '.') || !concatflag)
            displayregister = ""
        displayregister += digit
    }

    fun negate() {
        if (displayregister.first() == '-') {
            displayregister = displayregister.drop(1)
        }
        else {
            displayregister = "-$displayregister"
        }
    }

    fun backspace() {
        if (displayregister.length <= 1) {
            displayregister = "0"
        } else {
            displayregister = displayregister.dropLast(1)
        }
    }

    fun getString() : String {
        return displayregister
    }

    fun getFloat(): Float {
        return displayregister.toFloat()
    }
}