package com.example.jetpack.ktpaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData

class MainViewModel(repository: Repository) :ViewModel(){
    val liveData:LiveData<PagingData<GitHubAccount>> = repository.postOfData(0).asLiveData()

}