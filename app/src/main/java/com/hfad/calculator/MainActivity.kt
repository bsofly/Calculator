package com.hfad.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hfad.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)

        binding.info.text = ""
        updateScreen()

        binding.button0.setOnClickListener{ viewModel.inputdigits('0'); updateScreen() }
        binding.button1.setOnClickListener{ viewModel.inputdigits('1'); updateScreen() }
        binding.button2.setOnClickListener{ viewModel.inputdigits('2'); updateScreen() }
        binding.button3.setOnClickListener{ viewModel.inputdigits('3'); updateScreen() }
        binding.button4.setOnClickListener{ viewModel.inputdigits('4'); updateScreen() }
        binding.button5.setOnClickListener{ viewModel.inputdigits('5'); updateScreen() }
        binding.button6.setOnClickListener{ viewModel.inputdigits('6'); updateScreen() }
        binding.button7.setOnClickListener{ viewModel.inputdigits('7'); updateScreen() }
        binding.button8.setOnClickListener{ viewModel.inputdigits('8'); updateScreen() }
        binding.button9.setOnClickListener{ viewModel.inputdigits('9'); updateScreen() }
        binding.dot.setOnClickListener{ viewModel.inputdigits('.'); updateScreen() }
        binding.plusMinus.setOnClickListener { viewModel.negate(); updateScreen() }
        binding.backspace.setOnClickListener { viewModel.backspace(); updateScreen() }
        binding.clear.setOnClickListener { viewModel.clearall(); updateScreen() }
        binding.clearEntry.setOnClickListener { viewModel.clearEntry(); updateScreen() }
        binding.plus.setOnClickListener { viewModel.operator("+"); updateScreen() }
        binding.minus.setOnClickListener { viewModel.operator("-"); updateScreen() }
        binding.times.setOnClickListener { viewModel.operator("*"); updateScreen() }
        binding.divide.setOnClickListener { viewModel.operator("/"); updateScreen() }
        binding.equals.setOnClickListener { viewModel.equalsOp(); updateScreen() }
    }

    private fun updateScreen() {
        binding.display.text = viewModel.displayreg.displayregister
        binding.info.text = viewModel.displayinfo
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}