package jimmytrivedi.`in`.android.tipcalculator.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import jimmytrivedi.`in`.android.tipcalculator.R
import jimmytrivedi.`in`.android.tipcalculator.base.BaseActivity
import jimmytrivedi.`in`.android.tipcalculator.data.Tip
import jimmytrivedi.`in`.android.tipcalculator.databinding.ActivityHomeBinding
import jimmytrivedi.`in`.android.tipcalculator.global.TipConstant
import jimmytrivedi.`in`.android.tipcalculator.global.TipUtils
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeActivityViewModel by viewModels()
    private var totalAmount: Double
    private var tipPercent: Double
    private var spliBy: Int

    init {
        totalAmount = 0.0
        tipPercent = 0.0
        spliBy = 1
    }

    override fun init() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        setListeners()
    }

    override fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.totalAmount.collect {
                    binding.finalAmount.text = getString(R.string.total_amount_with_colon, it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.splitAmount.collect {
                    binding.splitAmount.text = getString(R.string.split_amount_with_colon, it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
               viewModel.finalTip.collect {
                    binding.finalTip.text = getString(R.string.tip_amount_with_colon, it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
               viewModel.menuItems.collect {
                   showPopupMenu(binding.helpMe)
                }
            }
        }
    }

    override fun getBundleParameters(bundle: Bundle) {}

    private fun initViews() {
       setDefaultView()
    }

    private fun setListeners() {
        // Total amount listener
        binding.totalAmount.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (TipUtils.checkValidation(s.toString())) {
                    setErrorView(binding.totalAmount, false)

                    val tip = Tip()
                    tip.totalAmount = s.toString()
                    tip.tipPercent = binding.tipPercent.editText?.text.toString()
                    tip.splitBy = binding.splitBy.editText?.text.toString()
                    tip.roundUp = binding.roundUpToggle.isChecked

                    viewModel.calculateTip(tip)
                } else {
                    setErrorView(binding.totalAmount, true)
                }

                if (s.toString().isEmpty()) {
                    setErrorView(binding.totalAmount, false)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Tip percent listener
        binding.tipPercent.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (TipUtils.checkValidation(s.toString())) {
                    setErrorView(binding.tipPercent, false)

                    val tip = Tip()
                    tip.totalAmount = binding.totalAmount.editText?.text.toString()
                    tip.tipPercent = s.toString()
                    tip.splitBy = binding.splitBy.editText?.text.toString()
                    tip.roundUp = binding.roundUpToggle.isChecked

                    viewModel.calculateTip(tip)
                } else {
                    setErrorView(binding.tipPercent, true)
                }

                if (s.toString().isEmpty()) {
                    setErrorView(binding.tipPercent, false)
                    setDefaultView()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Split by listener
        binding.splitBy.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (TipUtils.checkValidation(s.toString())) {
                    setErrorView(binding.splitBy, false)

                    val tip = Tip()
                    tip.totalAmount = binding.totalAmount.editText?.text.toString()
                    tip.tipPercent = binding.tipPercent.editText?.text.toString()
                    tip.splitBy = s.toString()
                    tip.roundUp = binding.roundUpToggle.isChecked

                    viewModel.calculateTip(tip)
                } else {
                    setErrorView(binding.splitBy, true)
                }

                if (s.toString().isEmpty()) {
                    setErrorView(binding.splitBy, false)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Round up listener
        binding.roundUpToggle.setOnCheckedChangeListener { _, isChecked ->
            val tip = Tip()
            tip.totalAmount = binding.totalAmount.editText?.text.toString()
            tip.tipPercent = binding.tipPercent.editText?.text.toString()
            tip.splitBy = binding.splitBy.editText?.text.toString()
            tip.roundUp = isChecked

            viewModel.calculateTip(tip)
        }

        // Reset Listener
        binding.reset.setOnClickListener {
            // Clear text
            binding.totalAmount.editText?.text?.clear()
            binding.splitBy.editText?.text?.clear()
            binding.tipPercent.editText?.text?.clear()

            // Clear focus
            binding.totalAmount.clearFocus()
            binding.splitBy.clearFocus()
            binding.tipPercent.clearFocus()

            // Clear selection
            binding.roundUpToggle.isChecked = false

            // Resetting text
            setDefaultView()
        }

        // Help Me Listener
        binding.helpMe.setOnClickListener {
            viewModel.getMenuData(this)
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.service_quality, popupMenu.menu)


        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.poor -> {
                    binding.tipPercent.editText?.setText(TipConstant.TipServiceQuality.POOR_QUALITY_PERCENT)
                    true
                }
                R.id.average -> {
                    binding.tipPercent.editText?.setText(TipConstant.TipServiceQuality.AVERAGE_QUALITY_PERCENT)
                    true
                }
                R.id.good -> {
                    binding.tipPercent.editText?.setText(TipConstant.TipServiceQuality.GOOD_QUALITY_PERCENT)
                    true
                }
                R.id.excellent -> {
                    binding.tipPercent.editText?.setText(TipConstant.TipServiceQuality.EXCELLENT_QUALITY_PERCENT)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun setDefaultView() {
        binding.finalAmount.text = getString(R.string.total_amount_without_colon)
        binding.splitAmount.text = getString(R.string.split_amount_without_colon)
        binding.finalTip.text = getString(R.string.tip_amount_without_colon)
    }

    private fun setErrorView(view: TextInputLayout, isErrorEnabled: Boolean) {
        if (isErrorEnabled) {
            view.isErrorEnabled = true
            view.error = getString(R.string.invalid_input)
        } else {
            view.error = null
            view.isErrorEnabled = false
        }
    }
}
