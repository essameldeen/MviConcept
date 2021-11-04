package com.example.mviconcept

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.mviconcept.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<AddNumberViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        //sender
        binding.addNumberBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.intentChannel.send(
                    MainIntent.AddNumber
                )

            }

        }
        binding.clearBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.intentChannel.send(
                    MainIntent.ClearData
                )
            }
        }

        //render
        renderView()
    }

    private fun renderView() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    is MainViewState.Idle -> binding.numberTv.text = "Waiting..."
                    is MainViewState.Result -> binding.numberTv.text = it.number.toString()
                    is MainViewState.Error -> binding.numberTv.text = it.errorMessage
                }
            }

        }
    }
}