package com.hfad.calculator

import android.util.Log
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    private var registers = FloatArray(3) { Float.NaN }
    private var operation: String = ""
    private var concat = true
    private var opBtnHit = false
    private var clearhist = false
    var displayinfo: String = ""
    val displayreg = Display()

    init {
        displayreg.setString("0")
    }

    fun negate() {
        displayreg.negate()
        opBtnHit = false
    }

    fun backspace() {
        displayreg.backspace()
        opBtnHit = false
    }

    fun clearEntry() {
        displayreg.setString("0")
        opBtnHit = false
    }

    fun clearall() {
        displayreg.setString("0")
        concat = true
        clear()
        opBtnHit = false
    }

    fun equalsOp() {
        concat = false
        executeEqual()
        opBtnHit = false
    }

    fun inputdigits(digitIn: Char) {
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

    fun operator(requestedop: String) {
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

    private fun infodisplay() {
        if (!registers[1].isNaN() && !clearhist && !opBtnHit) {
            displayinfo = "${registers[0].toString()} $operation " +
                    "${registers[1].toString()} = "
            clearhist = true
        } else {
            displayinfo = "${registers[0].toString()} $operation"
            clearhist = false
        }
    }

    fun logAll() {
        Log.d("CalculatorDbg", "displayreg: ${displayreg.getString()}")
        Log.d("CalculatorDbg", "register0: ${registers[0]}")
        Log.d("CalculatorDbg", "register1: ${registers[1]}")
        Log.d("CalculatorDbg", "operation: ${operation}")
        Log.d("CalculatorDbg", "register2: ${registers[2]}")
        Log.d("CalculatorDbg", "concat:  $concat")
        Log.d("CalculatorDbg", "clearhist:  $clearhist")
        Log.d("CalculatorDbg", "opBtnHit: $opBtnHit")
        Log.d("CalculatorDbg", "   ")
    }

}

class Display {
    var displayregister: String = ""

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