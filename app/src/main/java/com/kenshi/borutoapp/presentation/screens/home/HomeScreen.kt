package com.kenshi.borutoapp.presentation.screens.home

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kenshi.borutoapp.navigation.Screen
import com.kenshi.borutoapp.presentation.common.ListContent
import com.kenshi.borutoapp.ui.theme.statusBarColor

@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    // we have already access to those 12 items at our first network request
    // remote mediator is handling all those network requests and database caching by it self
    val allHeroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()

    //홈화면으로 돌아왔을때 변경되었던 statusBar 색상을 제어하기 위함 (원래 색상으로 다시 변경)
    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colors.statusBarColor

    SideEffect {
        systemUiController.setStatusBarColor(
            // @Composable invocations can only happen from the context of a @Composable function
            // color = MaterialTheme.colors.statusBarColor
            color = systemBarColor
        )
    }

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(Screen.Search.route)
                }
            )
        },
        content = {
            ListContent(
                heroes = allHeroes,
                navController = navController
            )
        }
    )
}