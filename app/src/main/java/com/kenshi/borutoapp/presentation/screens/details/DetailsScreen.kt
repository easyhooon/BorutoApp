package com.kenshi.borutoapp.presentation.screens.details

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.kenshi.borutoapp.util.Constants.BASE_URL
import com.kenshi.borutoapp.util.PaletteGenerator.convertImageUrlToBitmap
import com.kenshi.borutoapp.util.PaletteGenerator.extractColorFromBitmap
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
// hiltViewModel need initialize
fun DetailsScreen(
    navController: NavHostController,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    // val selectedHero = detailsViewModel.selectedHero
    // TODO nullable 을 해결할 수 있는 이유
    // automatically extract that state into a just nuallable hero
    // type이 state가 아닌 stateFlow라면 뒤에 collectAsState()로 변횐 해줘야함
    val selectedHero by detailsViewModel.selectedHero.collectAsState()
    val colorPalette by detailsViewModel.colorPalette

    if (colorPalette.isNotEmpty()) {
        DetailsContent(
            navController = navController,
            selectedHero = selectedHero,
            colors = colorPalette
        )
    } else {
        detailsViewModel.generateColorPalette()
    }

    val context = LocalContext.current

    // key 가 true 이면 어떻게 동작하는지 학습
    // -> composable 이 recomposition 될때마다 호출
    LaunchedEffect(key1 = true) {
        detailsViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.GenerateColorPalette -> {
                    val bitmap = convertImageUrlToBitmap(
                        imageUrl = "$BASE_URL${selectedHero?.image}",
                        context = context
                    )
                    if (bitmap != null) {
                        detailsViewModel.setColorPalette(
                            colors = extractColorFromBitmap(
                                bitmap = bitmap
                            )
                        )
                    }
                }
            }
        }
    }
}