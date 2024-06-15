package jimmytrivedi.`in`.android.tipcalculator.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jimmytrivedi.`in`.android.tipcalculator.base.BaseViewModel
import jimmytrivedi.`in`.android.tipcalculator.data.Tip
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(): BaseViewModel() {
    private val _totalAmount = MutableSharedFlow <String>()
    val totalAmount = _totalAmount.asSharedFlow()

    private val _splitAmount = MutableSharedFlow <String>()
    val splitAmount = _splitAmount.asSharedFlow()

    private val _finalTip = MutableSharedFlow <String>()
    val finalTip = _finalTip.asSharedFlow()

    fun calculateTip(tip: Tip) {
        viewModelScope.launch {
            val finalTotalAmount = tip.totalAmount?.toDoubleOrNull() ?: 0.0
            val finalTipPercent = tip.tipPercent?.toDoubleOrNull() ?: 0.0
            var splitBy = 1

            if (tip.splitBy?.isNotEmpty() == true) {
                val mSplitBy = tip.splitBy?.toInt() ?: 1
                if (mSplitBy > 0) {
                    splitBy = mSplitBy
                }
            }

            val roundUp = tip.roundUp

            if (finalTotalAmount > 0 && finalTipPercent > 0) {

                // Tip calculation
                var mTip = finalTipPercent / 100 * finalTotalAmount
                if (roundUp) {
                    mTip = kotlin.math.ceil(mTip)
                }
                _finalTip.emit(NumberFormat.getCurrencyInstance(Locale("en", "in")).format(mTip))

                // Total amount calculation
                val mAmount = finalTotalAmount - mTip
                _totalAmount.emit(NumberFormat.getCurrencyInstance(Locale("en", "in")).format(mAmount))

                // Split amount calculation
                val mSplitAmount = mAmount / splitBy
                _splitAmount.emit(NumberFormat.getCurrencyInstance(Locale("en", "in")).format(mSplitAmount))
            }
        }
    }
}