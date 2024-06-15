package jimmytrivedi.`in`.android.tipcalculator.main.activity

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import jimmytrivedi.`in`.android.tipcalculator.base.BaseActivity
import jimmytrivedi.`in`.android.tipcalculator.databinding.ActivityHomeBinding

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun init() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initObservers() {
    }

    override fun getBundleParameters(bundle: Bundle) {
    }

}
