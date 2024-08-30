package com.example.control.view.day

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.control.room.Something
import com.example.control.room.SomethingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DayViewModel(private val repository: SomethingRepository): ViewModel() {
    fun getAllItems(day: String) = repository.getAllItems(day).asLiveData(viewModelScope.coroutineContext)

    fun delete(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(userId)
        }
    }

    fun updateTime(newTime: String, userId: Int?) = viewModelScope.launch {
        repository.updateTime(newTime,userId)
    }

    fun sortByTime(dataList: List<Something>): List<Something> {
        return dataList.sortedBy { it.time }
    }
}

class DayViewModelFactory(private val somethingRepository: SomethingRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DayViewModel::class.java))
            return DayViewModel(somethingRepository) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}