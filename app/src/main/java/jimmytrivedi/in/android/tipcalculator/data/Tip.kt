package jimmytrivedi.`in`.android.tipcalculator.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tip(
    var totalAmount: String? = null,
    var tipPercent: String? = null,
    var splitBy: String? = null,
    var roundUp: Boolean = false
) : Parcelable