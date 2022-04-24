package com.test.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.test.presentation.view.ProgressDialog
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel<*>> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    private var progressDialog: ProgressDialog? = null

    protected abstract val viewModel: VM

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.states.collect { state -> onBindState(state) }
            }
        }
        binding.onInitializeViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun backButtonAction(action: () -> Unit) {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    action.invoke()
                }
            }
        activity?.onBackPressedDispatcher?.addCallback(this, callback)
    }

    protected abstract fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    /**
     * Initialize view properties that cannot be set in XML
     */
    protected open fun VB.onInitializeViews() = Unit

    /**
     * Catch UI events and send them to the view model
     */
    protected open fun onBindState(state: BaseState) {
        when (state) {
            is BaseState.Error -> showError(state.error.message ?: "")
            is BaseState.ShowLoading -> showProgressDialog()
            is BaseState.HideLoading -> hideProgressDialog()
        }
    }


    protected open fun showError(error: String, view: View? = null) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT)
            .setAnchorView(view)
            .show()
    }

    protected open fun showError(@StringRes resId: Int, view: View? = null) {
        showError(getString(resId), view)
    }

    private fun hideProgressDialog() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    private fun showProgressDialog() {
        progressDialog = ProgressDialog.show(childFragmentManager)
    }
}