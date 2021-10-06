package com.example.covid_19.di

import com.example.covid_19.data.models.remote.CovidApi
import com.example.covid_19.repository.CountriesRepository
import com.example.covid_19.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideCountriesRepository(
        api: CovidApi
    ) = CountriesRepository(api)

    @Singleton
    @Provides
    fun provideCovidApi(): CovidApi {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(CovidApi::class.java)
    }
}