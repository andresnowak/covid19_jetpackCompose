package com.example.covid_19

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.covid_19.ViewModel.CovidViewModel
import com.example.covid_19.data.models.CountryData
import com.example.covid_19.data.models.remote.responses.CountryInfo

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun AppNav(viewModel: CovidViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.HOME,
    ) {
        composable(Destinations.HOME) {
            CountriesList(navController = navController)
        }
        composable(
            "${Destinations.COUNTRY_DETAILS}/{countryIndexInList}",
            arguments = listOf(
                navArgument("countryIndexInList") {
                    type = NavType.IntType
                },
            )
        ) {
            val countryIndexInList = remember {
                it.arguments?.getInt("countryIndexInList") ?: 0
            }

            val countryData = viewModel.getCountryDataByIndex(countryIndexInList)
            CovidCountryInfo(countryData)
        }
    }
}

object Destinations {
    const val HOME = "home"
    const val COUNTRY_DETAILS = "countryDetails"
}