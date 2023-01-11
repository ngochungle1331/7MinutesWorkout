package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinish)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarFinish?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.btnFinish?.setOnClickListener {
            finish()
        }

        val historyDao = (application as WorkoutApp).db.HistoryDao()
        addDateToDatabase(historyDao)
    }

    private fun addDateToDatabase(historyDao: HistoryDao) {
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        Log.e("Date: ", "" + dateTime)

        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = simpleDateFormat.format(dateTime)
        Log.e("Formatted Date: ", "" + date)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
            Log.e("Date: ", "Added...")
        }
    }
}