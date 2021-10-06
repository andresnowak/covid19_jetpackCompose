package com.example.covid_19

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
fun CountryCard(
    navController: NavController,
    countryData: CountryData,
    countryIndexInList: Int
) {
    // format cases to have commas like 1000000 be 1,000,000
    val casesFormatter = DecimalFormat("#,###")
    val casesFormated = casesFormatter.format(countryData.cases)

    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    var palette by remember { mutableStateOf<Palette?>(null) }

    palette?.dominantSwatch?.rgb?.let { colorValue ->
        dominantColor = Color(colorValue)
    }

    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = 12.dp, // como si fuera una sombra para dar el efecto de elevacion
        modifier = Modifier
            .aspectRatio(2f)
            .padding(10.dp),
        backgroundColor = Color.Transparent,
        onClick = {
            Log.wtf("navigate", "slkjdfoiiqwjef")
            navController.navigate(
                "countryDetails/${countryIndexInList}"
            )
        }
    ) {
        Box(
            modifier = Modifier
                .background(
                    verticalGradient(
                        listOf(
                            dominantColor,
                            defaultDominantColor
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /*Image(
                painter = rememberImagePainter(flagUrl),
                contentDescription = countryName,
                modifier = Modifier
                    .size(150.dp, 90.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(16.dp),
                    )
                )*/
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
                Spacer(Modifier.height(8.dp))
                Text(
                    text = countryData.countryName,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    softWrap = true,
                    letterSpacing = 1.sp
                )
                Spacer(Modifier.height(10.dp))
                Text(casesFormated)
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun PreviewCountryCard() {
    val country = CountryData(
        4,
        "Mexico",
        "https://m.media-amazon.com/images/I/71ZpLH48rxL._AC_SL1500_.jpg",
        6000,
        400,
        30
    )

    val navController = rememberNavController()
    CountryCard(
        navController = navController,
        countryData = country,
        countryIndexInList = 0
    )
}