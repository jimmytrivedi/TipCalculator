package jimmytrivedi.`in`.android.tipcalculator.base

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity: AppCompatActivity() {

    /** ----------------------------------- Views ----------------------------------- **/


    /** ----------------------------------- Object ----------------------------------- **/


    /** ----------------------------------- Primitive variables ----------------------------------- **/


    /** ----------------------------------- Abstract functions declaration ----------------------------------- **/
    protected abstract fun init()

    protected abstract fun initObservers()

    protected abstract fun getBundleParameters(bundle: Bundle)


    /** ----------------------------------- Listener / Receiver implementation ----------------------------------- **/


    /** ----------------------------------- overridden functions ----------------------------------- **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        intent.extras?.let {
            //If the @arguments is null, then no need to give invocation of below function to child classes
            getBundleParameters(it)
        }
        init()
        initObservers()
    }


    /** ----------------------------------- open functions ----------------------------------- **/


    /** ----------------------------------- public functions ----------------------------------- **/
    fun showLog(message: Any?) {
        Log.d("Result", message.toString())
    }

    /** ----------------------------------- private functions ----------------------------------- **/
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {

        }
    }
}