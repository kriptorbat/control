package com.example.control.view.addSomething

import android.icu.util.Calendar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.control.room.Something
import com.example.control.room.SomethingRepository
import com.example.control.view.day.DayViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class AddSomethingViewModel(private val repository: SomethingRepository): ViewModel() {

    private val calendar : Calendar = Calendar.getInstance()
    var time = MutableLiveData <String>()

    init {
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        time.value = simpleDateFormat.format(calendar.time)
    }

    fun insertItem(something: Something){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertItem(something)
        }
    }
//    fun setPresentTime() {
//        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
//        time.value = simpleDateFormat.format(calendar.time)
//    }
    fun setNewTime(newTime: String){
        time.value = newTime
    }
//    fun getTime(): String?{
//        return time.value
//    }
}
class AddSomethingViewModelFactory(private val somethingRepository: SomethingRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddSomethingViewModel::class.java))
            return AddSomethingViewModel(somethingRepository) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}