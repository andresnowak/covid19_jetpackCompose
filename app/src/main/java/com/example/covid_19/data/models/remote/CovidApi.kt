package com.example.covid_19.data.models.remote

import com.example.covid_19.data.models.remote.responses.CountriesList
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidApi {
    @GET("countries")
    suspend fun getCountriesList(
        @Query("sort") order: String,
    ) : CountriesList
}