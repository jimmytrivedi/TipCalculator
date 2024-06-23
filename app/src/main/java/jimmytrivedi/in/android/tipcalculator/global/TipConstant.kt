package jimmytrivedi.`in`.android.tipcalculator.global

sealed class TipConstant {

    class Default : TipConstant() {
        companion object {
            const val ENGLISH_LANGUAGE = "en"
            const val LOCALE_INDIA = "in"
        }
    }

    class TipServiceQuality : TipConstant() {
        companion object {
            const val POOR_QUALITY_PERCENT = "5"
            const val AVERAGE_QUALITY_PERCENT = "10"
            const val GOOD_QUALITY_PERCENT = "15"
            const val EXCELLENT_QUALITY_PERCENT = "20"
        }
    }
}