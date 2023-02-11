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
        binding.calculatorViewModel = viewModel
        binding.lifecycleOwner = this

        binding.button0.setOnClickListener{ viewModel.inputdigits('0') }
        binding.button1.setOnClickListener{ viewModel.inputdigits('1') }
        binding.button2.setOnClickListener{ viewModel.inputdigits('2') }
        binding.button3.setOnClickListener{ viewModel.inputdigits('3') }
        binding.button4.setOnClickListener{ viewModel.inputdigits('4') }
        binding.button5.setOnClickListener{ viewModel.inputdigits('5') }
        binding.button6.setOnClickListener{ viewModel.inputdigits('6') }
//        binding.button7.setOnClickListener{ viewModel.inputdigits('7') }
        binding.button8.setOnClickListener{ viewModel.inputdigits('8') }
        binding.button9.setOnClickListener{ viewModel.inputdigits('9') }
        binding.dot.setOnClickListener{ viewModel.inputdigits('.') }
        binding.plusMinus.setOnClickListener { viewModel.negate() }
        binding.backspace.setOnClickListener { viewModel.backspace() }
//        binding.clear.setOnClickListener { viewModel.clearall() }
        binding.clearEntry.setOnClickListener { viewModel.clearEntry() }
        binding.plus.setOnClickListener { viewModel.operator('+') }
        binding.minus.setOnClickListener { viewModel.operator('-') }
        binding.times.setOnClickListener { viewModel.operator('*') }
        binding.divide.setOnClickListener { viewModel.operator('/') }
        binding.equals.setOnClickListener { viewModel.equalsOp() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}