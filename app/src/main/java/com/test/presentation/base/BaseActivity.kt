package com.test.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.launch

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> :
    AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    protected abstract val viewModel: VM

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = onCreateViewBinding()
        setContentView(binding.root)
        binding.onInitializeViews()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.states.collect { state -> onBindState(state) }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    protected abstract fun onCreateViewBinding(): VB

    /**
     * Initialize view properties that cannot be set in XML
     */
    protected open fun VB.onInitializeViews() = Unit

    /**
     * Catch UI events and send them to the view model
     */
    protected abstract fun onBindState(state: BaseState)

}