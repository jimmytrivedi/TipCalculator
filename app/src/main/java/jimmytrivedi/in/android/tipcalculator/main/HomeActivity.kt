package jimmytrivedi.`in`.android.tipcalculator.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import jimmytrivedi.`in`.android.tipcalculator.R
import jimmytrivedi.`in`.android.tipcalculator.base.BaseActivity
import jimmytrivedi.`in`.android.tipcalculator.databinding.ActivityHomeBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeActivityViewModel by viewModels()
    private var totalAmount: Double
    private var tipPercent: Double

    init {
        totalAmount = 0.0
        tipPercent = 0.0
    }

    override fun init() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.finalAmount.text = getString(R.string.tip_amount_without_colon)
        setListeners()
    }

    override fun initObservers() {
        lifecycleScope.launch {
            viewModel.finalTip.collect {
                binding.finalAmount.text = getString(R.string.tip_amount_with_colon, it)
            }
        }
    }

    override fun getBundleParameters(bundle: Bundle) {
    }

    private fun setListeners() {
        binding.totalAmount.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.calculateTip(
                    s.toString(),
                    binding.tipPercent.editText?.text.toString(),
                    binding.roundUpToggle.isChecked)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.tipPercent.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.calculateTip(
                    binding.totalAmount.editText?.text.toString(),
                    s.toString(),
                    binding.roundUpToggle.isChecked)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.roundUpToggle.setOnCheckedChangeListener { _, isChecked ->
            viewModel.calculateTip(
                binding.totalAmount.editText?.text.toString(),
                binding.tipPercent.editText?.text.toString(),
                isChecked
            )
        }
    }
}
