package com.example.covid_19.repository

import android.util.Log
import com.example.covid_19.data.models.remote.CovidApi
import com.example.covid_19.data.models.remote.responses.CountriesList
import com.example.covid_19.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CountriesRepository @Inject constructor(private val api: CovidApi) {
    suspend fun getCountriesList(order: String): Resource<CountriesList> {
        val response = try {
            api.getCountriesList(order)
        } catch (e: Exception) {
            Log.wtf("Error", e)
            return Resource.Error("An unknown error occurred")
        }

        return Resource.Success(response)
    }
}