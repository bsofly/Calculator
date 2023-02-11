package com.hfad.calculator

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    private var registers = FloatArray(3) { Float.NaN }
    private var operation: String = ""
    private var concat = true
    private var opBtnHit = false
    private var clearhist = false
    val displayinfo = MutableLiveData("")
    val displayreg = Display()

    init {
        displayreg.setString("0")
        displayinfo.value = ""
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
        displayinfo.value = ""
        operation = ""
        clearhist = false
    }

    fun operator(requestedop: Char) {
        if (operation != "" && !displayreg.getFloat().isNaN() && !clearhist && !opBtnHit) {
            executeEqual()
        }
        operation = requestedop.toString()
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
            displayinfo.value = "${registers[0].toString()} $operation " +
                    "${registers[1].toString()} = "
            clearhist = true
        } else {
            displayinfo.value = "${registers[0].toString()} $operation"
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
    val displayregister = MutableLiveData("")

    fun setString(registerString: String) {
        displayregister.value = registerString
    }

    fun setFloat(registerFloat: Float) {
        displayregister.value = registerFloat.toString()
        if (displayregister.value?.slice((displayregister.value?.length?.minus(2) ?: 0)
                    until displayregister.value?.length!!) ==".0") {
                        displayregister.value =
                        displayregister.value?.slice(0 .. displayregister.value!!.length-3)
        }
    }

    fun inputbutton(digit: Char, concatflag: Boolean) {
        if ((displayregister.value.equals("0") && digit != '.') || !concatflag)
            displayregister.value = ""
        displayregister.value += digit
    }

    fun negate() {
        if ((displayregister.value?.first() ?: "") == '-') {
            displayregister.value = displayregister.value?.drop(1)
        }
        else {
            displayregister.value = "-$displayregister.value"
        }
    }

    fun backspace() {
        if ((displayregister.value?.length ?: 0) <= 1) {
            displayregister.value = "0"
        } else {
            displayregister.value = displayregister.value?.dropLast(1)
        }
    }

    fun getString() : String? {
        return displayregister.value
    }

    fun getFloat(): Float {
        return displayregister.value?.toFloat() ?: 0.0F
    }
}