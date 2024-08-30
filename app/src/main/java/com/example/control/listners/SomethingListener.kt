package com.example.control.listners

import com.example.control.room.Something

interface SomethingListener {
    fun onSomethingClicked(something: Something)
    fun onTimeClicked(something: Something)
}