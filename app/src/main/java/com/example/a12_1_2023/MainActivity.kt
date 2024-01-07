package com.example.a12_1_2023

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.lang.Integer.valueOf
import java.text.DecimalFormat


class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private lateinit var tvPercent: TextView
    private lateinit var tvLevel: TextView
    private lateinit var tvCommissionValue: TextView
    private lateinit var tvSummaValue: TextView
    private lateinit var edText: EditText
    var df = DecimalFormat("0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.tvBottom)
        val text = "MADE IN   BY AZAT"
        val flagDrawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.kz_flag)

        if (flagDrawable != null) {
            val spannable = SpannableString(text)
            val width = resources.getDimensionPixelSize(R.dimen.icon_width)
            val height = resources.getDimensionPixelSize(R.dimen.icon_height)
            flagDrawable.setBounds(0, 0, width, height)
            val imageSpan = ImageSpan(flagDrawable, ImageSpan.ALIGN_BASELINE)
            val start = (spannable.length - 1) / 2
            val end = start + 1
            spannable.setSpan(imageSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            tv.text = spannable
        }
        tvPercent = findViewById(R.id.tvPercent)
        tvPercent.text = "0"
        tvCommissionValue = findViewById(R.id.tvCommissionValue)
        tvCommissionValue.text = "0"
        tvSummaValue = findViewById(R.id.tvSummaValue)
        tvSummaValue.text = "0"
        val seekBar: SeekBar = findViewById(R.id.seekBar)
        seekBar.setOnSeekBarChangeListener(this)
        edText = findViewById(R.id.tvEditText)
        edText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER
                    && edText.text.toString() != "" && edText.text.toString() > 0.toString()
                ) {
                    val depValue = edText.text.toString().toDouble()
                    edText.text = Editable.Factory.getInstance().newEditable(df.format(depValue).toString())
                    tvCommissionValue.text =
                        df.format((valueOf(seekBar.progress) * depValue / 100.00)).toString()
                    tvSummaValue.text =
                        df.format((valueOf(seekBar.progress) * depValue / 100.00) + depValue)
                            .toString()
                    return true
                } else if (edText.text.toString() == "") {
                        edText.setText("0")
                        tvCommissionValue.text = "0"
                        tvSummaValue.text = "0"
                }
                return false
            }
        })

    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (seekBar != null) {
            updateLevel(seekBar.progress)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        if (seekBar != null) {
            updateLevel(seekBar.progress)
        }
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        if (seekBar != null) {
            if (edText.text.toString().equals("")) {
                edText.setText("0")
            }
            val depValue = edText.text.toString().toDouble()
            tvPercent.text = valueOf(seekBar.progress).toString()
            tvCommissionValue.text =
                df.format((valueOf(seekBar.progress) * depValue / 100.00)).toString()
            tvSummaValue.text =
                df.format((valueOf(seekBar.progress) * depValue / 100.00) + depValue).toString()
        }
    }

    private fun updateLevel(progress: Int) {
        tvLevel = findViewById(R.id.tvLevel)
        val number = valueOf(progress)

        if (number in 0..9) {
            tvLevel.text = getString(R.string.tv_level1)
            tvLevel.setTextColor(ContextCompat.getColor(applicationContext, R.color.teal_200))
        } else if (number in 10..14) {
            tvLevel.text = getString(R.string.tv_level2)
            tvLevel.setTextColor(ContextCompat.getColor(applicationContext, R.color.purple_700))
        } else if (progress in 15..19) {
            tvLevel.text = getString(R.string.tv_level3)
            tvLevel.setTextColor(ContextCompat.getColor(applicationContext, R.color.orange))
        } else if (number in 20..24) {
            tvLevel.text = getString(R.string.tv_level4)
            tvLevel.setTextColor(ContextCompat.getColor(applicationContext, R.color.violet))
        } else {
            tvLevel.text = getString(R.string.tv_level5)
            tvLevel.setTextColor(ContextCompat.getColor(applicationContext, R.color.red))
        }
    }
}

