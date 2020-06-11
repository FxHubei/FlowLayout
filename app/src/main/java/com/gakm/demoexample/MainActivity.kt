package com.gakm.demoexample

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private val array = intArrayOf(
        R.color.colorPrimary,
        R.color.FF8EE5EE,
        R.color.colorAccent,
        R.color.A52A2A
    )
    private val title = arrayOf<String>(
        "宋江提出招安",
        "武松和李逵都反对",
        "为何宋江",
        "杀李逵？"
    )
    private val conten = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewGroup = findViewById<ViewGroup>(R.id.root_container)
        for (i in 0 until 30) {
            val apply = TextView(this).apply {
                text = title[i % 4] + "==$i"
                (Math.random() * 25 + 65).roundToInt();
                setBackgroundColor(ContextCompat.getColor(this@MainActivity, array[i % 4]))
                if (layoutParams == null) {
                    layoutParams = ViewGroup.MarginLayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT
                    )
                }
                (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                    topMargin = 20
                    leftMargin = 20
                    rightMargin = 20
                    bottomMargin = 20
                }
            }
            viewGroup.addView(apply)
        }

    }
}
