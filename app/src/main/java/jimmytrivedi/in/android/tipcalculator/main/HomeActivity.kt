package jimmytrivedi.`in`.android.tipcalculator.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import jimmytrivedi.`in`.android.tipcalculator.R
import jimmytrivedi.`in`.android.tipcalculator.base.BaseActivity
import jimmytrivedi.`in`.android.tipcalculator.data.Tip
import jimmytrivedi.`in`.android.tipcalculator.databinding.ActivityHomeBinding
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
    }

    override fun getBundleParameters(bundle: Bundle) {}

    private fun initViews() {
        binding.finalAmount.text = getString(R.string.total_amount_without_colon)
        binding.splitAmount.text = getString(R.string.split_amount_without_colon)
        binding.finalTip.text = getString(R.string.tip_amount_without_colon)
    }

    private fun setListeners() {
        // Total amount listener
        binding.totalAmount.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val tip = Tip()
                tip.totalAmount = s.toString()
                tip.tipPercent = binding.tipPercent.editText?.text.toString()
                tip.splitBy = binding.splitBy.editText?.text.toString()
                tip.roundUp = binding.roundUpToggle.isChecked

                viewModel.calculateTip(tip)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Tip percent listener
        binding.tipPercent.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val tip = Tip()
                tip.totalAmount = binding.totalAmount.editText?.text.toString()
                tip.tipPercent = s.toString()
                tip.splitBy = binding.splitBy.editText?.text.toString()
                tip.roundUp = binding.roundUpToggle.isChecked

                viewModel.calculateTip(tip)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Split by listener
        binding.splitBy.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val tip = Tip()
                tip.totalAmount = binding.totalAmount.editText?.text.toString()
                tip.tipPercent = binding.tipPercent.editText?.text.toString()
                tip.splitBy = s.toString()
                tip.roundUp = binding.roundUpToggle.isChecked

                viewModel.calculateTip(tip)
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
    }
}
