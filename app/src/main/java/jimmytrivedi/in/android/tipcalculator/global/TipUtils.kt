package jimmytrivedi.`in`.android.tipcalculator.global

import java.util.regex.Pattern

object TipUtils {

    fun checkValidation(text: String) : Boolean{
        val nPattern = "[1-9][0-9]*"
        val p = Pattern.compile(nPattern)
        val m = p.matcher(text)
        return m.matches()
    }
}