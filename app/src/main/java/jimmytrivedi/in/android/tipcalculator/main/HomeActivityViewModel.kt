package jimmytrivedi.`in`.android.tipcalculator.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jimmytrivedi.`in`.android.tipcalculator.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(): BaseViewModel() {
    private val _finalTip = MutableSharedFlow <String>()
    val finalTip = _finalTip.asSharedFlow()

    fun calculateTip(totalAmount: String?, tipPercent: String?, roundUp: Boolean) {
        viewModelScope.launch {
            val finalTotalAmount = totalAmount?.toDoubleOrNull() ?: 0.0
            val finalTipPercent = tipPercent?.toDoubleOrNull() ?: 0.0

            var tip = finalTipPercent / 100 * finalTotalAmount
            if (roundUp) {
                tip = kotlin.math.ceil(tip)
            }
            _finalTip.emit(NumberFormat.getCurrencyInstance(Locale("en", "in")).format(tip))
        }
    }
}