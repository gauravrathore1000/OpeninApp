package com.example.openinapp.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openinapp.Model.dashboardAPIResponse.DashboardApiResponse
import com.example.openinapp.Utils.Resource
import com.example.openinapp.network.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(val application: Application, val repository: Repository) : ViewModel() {

    val resultDashboard: MutableLiveData<Resource<DashboardApiResponse>> = MutableLiveData()

        fun hitApi() {
        resultDashboard.value = Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response = repository.getDashboardApi()
                withContext(Dispatchers.Main) {
                    if (response.code() == 200) {
                        resultDashboard.value =
                            Resource.success(response.body()!!, response.body()?.message.toString())
                    } else {
                        resultDashboard.value =
                            Resource.error(response.body()!!, response.body()?.message.toString())

                    }
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    resultDashboard.value = Resource.error(null, e.message.toString())
                }

            }

        }
    }
}