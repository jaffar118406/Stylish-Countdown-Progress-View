package com.example.canvastry

import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.canvastry.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    val TAG = this::class.java.canonicalName
    var progressSeconds = 60
    var progressMinutes = 59
    var progressHours = 23
    var progressDays = 2
    val handler = Handler()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        enableEdgeToEdge()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val str = SpannableString("Our new business will be launch soon.")
        str.setSpan(BackgroundColorSpan(resources.getColor(R.color.progress_color)), 25, 37, 0)
        str.setSpan(BackgroundColorSpan(resources.getColor(R.color.progress_color)), 4, 7, 0)
        binding.tt.setText(str)

        startSecondsCountDown()
        myInit()
    }

    fun myInit(){
        binding.minutes.setMaxProgress(59)
        binding.minutes.setProgress(59)
        binding.minutes.setTextSize(60f)
        binding.minutes.setProgressColor(resources.getColor(R.color.progress_color))
        binding.minutes.setProgress(progressMinutes)
        binding.minutes.setCountdown(progressMinutes)

        binding.hours.setMaxProgress(23)
        binding.hours.setProgress(23)
        binding.hours.setTextSize(60f)
        binding.hours.setProgressColor(resources.getColor(R.color.progress_color))
        binding.hours.setProgress(progressHours)
        binding.hours.setCountdown(progressHours)

        binding.days.setMaxProgress(2)
        binding.days.setProgress(2)
        binding.days.setTextSize(60f)
        binding.days.setProgressColor(resources.getColor(R.color.progress_color))
        binding.days.setProgress(progressDays)
        binding.days.setCountdown(progressDays)
    }


    private fun startSecondsCountDown() {
        binding.seconds.setMaxProgress(60)
        binding.seconds.setTextSize(60f)
        binding.seconds.setProgressColor(resources.getColor(R.color.progress_color))
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (progressSeconds >= 0) {
                    binding.run {
                        seconds.setProgress(progressSeconds)
                        seconds.setCountdown(progressSeconds)
                    }
                    progressSeconds--
                    handler.postDelayed(this, 1000)
                }else {
                    progressSeconds = 60
                    progressMinutes--
                    handler.postDelayed(this, 1000)
                    startMinutesCountDown(progressMinutes)
                }
            }
        }, 0)
    }


    private fun startMinutesCountDown(progressMinutesArg: Int) {
        binding.minutes.setProgress(progressMinutesArg)
        binding.minutes.setCountdown(progressMinutesArg)

        if (progressMinutesArg < 0) {
            progressHours--
            startHoursCountDown(progressHours)
        }
    }

    private fun startHoursCountDown(progressHoursArgs: Int) {
        binding.hours.setProgress(progressHoursArgs)
        binding.hours.setCountdown(progressHoursArgs)

        if (progressHours < 0) {
            progressDays--
            startDaysCountDown(progressDays)
        }
    }

    private fun startDaysCountDown(progressDaysArgs: Int) {
        binding.days.setProgress(progressDaysArgs)
        binding.days.setCountdown(progressDaysArgs)
    }
}