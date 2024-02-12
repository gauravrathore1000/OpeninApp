package com.example.openinapp.network

import com.example.openinapp.Model.dashboardAPIResponse.DashboardApiResponse
import retrofit2.Response

class Repository {
    suspend fun getDashboardApi() : Response<DashboardApiResponse> {
        return RetroFitClient().getApi().dashBoard("bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI")
    }
}