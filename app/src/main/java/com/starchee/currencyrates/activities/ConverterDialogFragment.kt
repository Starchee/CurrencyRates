package com.starchee.currencyrates.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.addTextChangedListener
import com.starchee.currencyrates.R
import com.starchee.currencyrates.model.local.CurrencyLocal
import com.starchee.currencyrates.presenters.ConverterDialogPresenter
import com.starchee.currencyrates.views.ConverterDialogView
import kotlinx.android.synthetic.main.convert_dialog_fragment.*
import moxy.MvpAppCompatDialogFragment
import moxy.presenter.InjectPresenter

class ConverterDialogFragment : MvpAppCompatDialogFragment(), ConverterDialogView {

    @InjectPresenter
    lateinit var converterDialogPresenter: ConverterDialogPresenter

    companion object {

        val TAG = ConverterDialogFragment::class.java.simpleName

        private const val KEY_CHAR_CODE = "charCode"
        private const val KEY_NOMINAL = "nominal"
        private const val KEY_VALUE = "value"

        fun newInstance(currencyLocal: CurrencyLocal): ConverterDialogFragment {
            val args = Bundle()
            args.apply {
                putString(KEY_CHAR_CODE, currencyLocal.charCode)
                putInt(KEY_NOMINAL,currencyLocal.nominal)
                putFloat(KEY_VALUE, currencyLocal.value)
            }

            val fragment = ConverterDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.convert_dialog_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        convert_dialog_tv_charCode.text = arguments!!.getString(KEY_CHAR_CODE)

        convert_dialog_et_amount.addTextChangedListener {
            convert_dialog_btn_convert.isEnabled = convert_dialog_et_amount.text.toString().isNotEmpty() }

        convert_dialog_btn_convert.isEnabled = convert_dialog_et_amount.text.toString().isNotEmpty()
        convert_dialog_btn_convert.setOnClickListener {
            converterDialogPresenter.convert(
                nominal = arguments!!.getInt(KEY_NOMINAL),
                value = arguments!!.getFloat(KEY_VALUE),
                amount = convert_dialog_et_amount.text.toString())
        }

        convert_dialog_btn_cancel.setOnClickListener {
            dismiss()
        }
    }

    override fun setResult(amount: String, result: String) {
        convert_dialog_et_amount.setText(amount)
        convert_dialog_tv_result.text = result
    }


}