package com.test.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = onCreateViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onInitializeViews()
        binding.onBindViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected abstract fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    /**
     * Initialize view properties that cannot be set in XML
     */
    protected open fun VB.onInitializeViews() = Unit

    /**
     * Render the view model state in the UI
     */
    protected abstract fun VB.onBindViews()

}