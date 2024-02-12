package com.example.openinapp.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.openinapp.network.Repository

class DashboardViewModelFactory(val application: Application, val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(com.example.openinapp.viewmodels.DashboardViewModel::class.java)){
            return com.example.openinapp.viewmodels.DashboardViewModel(application, repository) as T
        }

        throw  IllegalArgumentException("Class not found")
    }
}