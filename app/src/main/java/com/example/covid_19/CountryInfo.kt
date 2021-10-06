package com.example.covid_19

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import coil.annotation.ExperimentalCoilApi
import com.example.covid_19.ViewModel.CovidViewModel
import com.example.covid_19.data.models.CountryData
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.palette.BitmapPalette
import java.text.DecimalFormat

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CovidCountryInfo(
    countryData: CountryData,
) {
    // format cases to have commas like 1000000 be 1,000,000
    val dataFormatter = DecimalFormat("#,###")
    val casesFormated = dataFormatter.format(countryData.cases)
    val recoveredFormated = dataFormatter.format(countryData.recovered)
    val deceasedFormated = dataFormatter.format(countryData.deceased)

    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    var palette by remember { mutableStateOf<Palette?>(null) }

    palette?.dominantSwatch?.rgb?.let { colorValue ->
        dominantColor = Color(colorValue)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        defaultDominantColor,
                        dominantColor
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                imageModel = countryData.flagImageUrl,
                bitmapPalette = BitmapPalette {
                    palette = it
                },
                contentDescription = countryData.countryName,
                modifier = Modifier
                    .size(150.dp, 90.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(16.dp),
                    ),
                loading = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.scale(0.5f)
                        )
                    }
                },
                failure = { Text(text = "Image request failed") }
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = countryData.countryName,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                softWrap = true,
                letterSpacing = 1.sp
            )
            Spacer(Modifier.height(28.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Casos: $casesFormated", fontSize = 19.sp)
                Spacer(Modifier.height(12.dp))
                Text(text = "Recuperados: $recoveredFormated", fontSize = 19.sp)
                Spacer(Modifier.height(12.dp))
                Text(text = "Decesos: $deceasedFormated", fontSize = 19.sp)
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun PreviewCountryData() {
    val country = CountryData(
        4,
        "Mexico",
        "https://m.media-amazon.com/images/I/71ZpLH48rxL._AC_SL1500_.jpg",
        6000,
        400,
        50
    )
    CovidCountryInfo(
        country
    )
}