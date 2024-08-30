package com.example.control.view.addSomething

import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.control.R
import com.example.control.databinding.ActivityAddSomethingBinding
import com.example.control.room.Something
import com.example.control.room.MainDb
import com.example.control.room.SomethingRepository
import com.example.control.view.day.DayViewModel
import com.example.control.view.day.DayViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale

class AddSomethingActivity : AppCompatActivity() {

    lateinit var binding : ActivityAddSomethingBinding
    private val calendar : Calendar = Calendar.getInstance()
    private val viewModel : AddSomethingViewModel by lazy {
        ViewModelProvider(this, AddSomethingViewModelFactory(SomethingRepository(MainDb.getDb(this))))[AddSomethingViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSomethingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
        update()
    }

    private fun setListeners() {
        binding.tvTime.setOnClickListener {
            setTime()
        }
        binding.btnAdd.setOnClickListener{
            addData()
        }
    }
    private fun update(){
        viewModel.time.observe(this, Observer {
            binding.tvTime.text = it.toString()
        })
    }

    private fun setTime(){
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _,hour,minute ->
            calendar.set(Calendar.HOUR_OF_DAY,hour)
            calendar.set(Calendar.MINUTE,minute)
            viewModel.setNewTime(SimpleDateFormat("HH:mm",Locale.getDefault()).format(calendar.time))
        }
        TimePickerDialog(this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show()
    }

    private fun addData(){
        if (binding.etTitle.text.toString() != ""){
            val something = Something()
            something.title = binding.etTitle.text.toString()
            something.data = binding.etData.text.toString()
            something.time = binding.tvTime.text.toString()
            something.typeColor = colorTranslate()
            something.day = intent.getStringExtra("day")!!
            something.alarm = false
            viewModel.insertItem(something)
            finish()
        } else {
            Toast.makeText(this,"Поле заголовка пустое",Toast.LENGTH_SHORT).show()
        }
    }

    private fun colorTranslate() : Int{
        return when(binding.rgColors.checkedRadioButtonId){
            R.id.rbRed -> ContextCompat.getColor(applicationContext,R.color.color1)
            R.id.rbPurple -> ContextCompat.getColor(applicationContext,R.color.color2)
            R.id.rbBlue -> ContextCompat.getColor(applicationContext,R.color.color3)
            R.id.rbWhiteBlue -> ContextCompat.getColor(applicationContext,R.color.color4)
            R.id.rbWhiteGreen -> ContextCompat.getColor(applicationContext,R.color.color5)
            R.id.rbYellow -> ContextCompat.getColor(applicationContext,R.color.color6)
            R.id.rbGray -> ContextCompat.getColor(applicationContext,R.color.color7)
            else -> {ContextCompat.getColor(applicationContext,R.color.black)}
        }
    }
}