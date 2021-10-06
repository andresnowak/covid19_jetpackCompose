package com.example.covid_19.ViewModel

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.palette.graphics.Palette
import com.example.covid_19.data.models.CountryData
import com.example.covid_19.repository.CountriesRepository
import com.example.covid_19.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CovidViewModel @Inject constructor(
    private val repository: CountriesRepository
) : ViewModel() {
    var countriesList = mutableStateOf<List<CountryData>>(listOf())
    private var _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading
    private var _loadError = mutableStateOf("")
    val loadError get() = _loadError
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    private var cachedPokemonList = countriesList.value

    init {
        getCountriesList()
    }

    fun searchCountriesList(query: String) {
        val listToSearch = if (isSearchStarting) {
            countriesList.value
        } else {
            cachedPokemonList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                countriesList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }

            val results = listToSearch.filter {
                it.countryName.contains(query.trim(), ignoreCase = true)
            }

            // save original list in the cached list
            if (isSearchStarting) {
                cachedPokemonList = countriesList.value
                isSearchStarting = false
            }

            countriesList.value = results
            isSearching.value = true
        }
    }

    fun getCountriesList() {
        viewModelScope.launch {
            val result = repository.getCountriesList("cases")

            when (result) {
                is Resource.Success -> {
                    val countryEntries = result.data?.mapIndexed { index, entry ->
                        val name = entry.country
                        val imageUrl = entry.countryInfo.flag
                        val cases = entry.cases
                        val id = entry.countryInfo._id
                        val deceased = entry.deaths
                        val recovered = entry.recovered

                        CountryData(id, name, imageUrl, cases, recovered, deceased)
                    }

                    loadError.value = ""
                    isLoading.value = false

                    if (countryEntries != null) {
                        countriesList.value = countryEntries
                    }
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    fun getCountryDataByIndex(index: Int): CountryData {
        return countriesList.value[index]
    }
}
