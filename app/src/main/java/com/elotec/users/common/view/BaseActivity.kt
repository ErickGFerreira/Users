package com.elotec.users.common.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.modifyContextConfig())
    }

    private fun Context.modifyContextConfig(): Context {
        val config = resources.configuration.apply {
            fontScale = closestValue(fontScale)
        }

        return createConfigurationContext(config)
    }

    private fun closestValue(value: Float): Float =
        FONT_SCALE_OPTIONS.minBy { abs(value - it) }

    private companion object {
        private val FONT_SCALE_OPTIONS = listOf(0.8f, 1.0f, 1.2f)
    }
}