package com.example.covid_19.data.models

data class CountryData(
    val id: Int,
    val countryName: String,
    val flagImageUrl: String,
    val cases: Int,
    val recovered: Int,
    val deceased: Int
)
