package com.test.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import com.test.R
import com.test.databinding.ViewTableBinding

class TableView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayoutCompat(context, attrs) {

    private var _viewBinding: ViewTableBinding? = null
    private val viewBinding: ViewTableBinding get() = _viewBinding!!

    init {
        val inflater = LayoutInflater.from(context)
        _viewBinding = ViewTableBinding.inflate(inflater, this, true)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TableView)
        try {
            with(viewBinding) {
                val textTitle = ta.getResourceId(R.styleable.TableView_tbv_title, -1)
                textTitle.takeIf { it > -1 }?.let { vTableTitle.setText(it) }
                val textValue = ta.getResourceId(R.styleable.TableView_tbv_value, -1)
                textValue.takeIf { it > -1 }?.let { vTableTitle.setText(it) }
            }
        } finally {
            ta.recycle()
        }
    }

    fun setValueText(valueText: String) {
        viewBinding.vTableValue.text = valueText
    }
}