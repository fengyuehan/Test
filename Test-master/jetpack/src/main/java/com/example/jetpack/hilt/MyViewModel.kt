package com.example.jetpack.hilt

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class MyViewModel @ViewModelInject constructor(private val repository: Repository):ViewModel(){
    fun dowork(){
        repository.doRepositoryWork()
    }
}