package com.example.control.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.control.adapters.SliderAdapter
import com.example.control.databinding.ActivityMainBinding
import com.example.control.tools.Constants
import com.example.control.view.day.DayFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTabs()
        //createNotificationChannel()
    }

    private fun setTabs(){
        binding.vp.isSaveEnabled = false

        val adapter = SliderAdapter(this.supportFragmentManager,lifecycle)
        adapter.addFragment(DayFragment.newInstance(Constants.MONDAY), "П")
        adapter.addFragment(DayFragment.newInstance(Constants.TUESDAY), "В")
        adapter.addFragment(DayFragment.newInstance(Constants.WEDNESDAY), "С")
        adapter.addFragment(DayFragment.newInstance(Constants.THURSDAY), "Ч")
        adapter.addFragment(DayFragment.newInstance(Constants.FRIDAY), "П")
        adapter.addFragment(DayFragment.newInstance(Constants.SATURDAY), "С")
        adapter.addFragment(DayFragment.newInstance(Constants.SUNDAY), "В")
        adapter.notifyDataSetChanged()
        binding.vp.adapter = adapter

        TabLayoutMediator(binding.tabLayout,binding.vp) { tab, position ->
            tab.text = adapter.getPageTitle(position)
            binding.vp.setCurrentItem(tab.position, true)
        }.attach()
    }

//    private fun createNotificationChannel() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            val name: CharSequence = "ControlReminderChannel"
//            val description = "Chanel for alarm manager"
//            val importance = NotificationManager.IMPORTANCE_HIGH
//            val channel = NotificationChannel("Control",name,importance)
//            channel.description = description
//            val notificationManager = getSystemService(NotificationManager::class.java)
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
}
