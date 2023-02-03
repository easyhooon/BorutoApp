package com.kenshi.borutoapp.presentation.screens.search

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kenshi.borutoapp.presentation.common.ListContent
import com.kenshi.borutoapp.ui.theme.statusBarColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalCoilApi
@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by searchViewModel.searchQuery
    val heroes = searchViewModel.searchHeroes.collectAsLazyPagingItems()

    // 검색 초기 화면으로 돌아왔을때 변경되었던 statusBar 색상을 제어하기 위함 (원래 색상으로 다시 변경)
    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colors.statusBarColor

    // 해당 함수 호출 의미 학습
    // -> composable 이 recomposition 이 수행되었을 때 호출
    SideEffect {
        systemUiController.setStatusBarColor(
            color = systemBarColor
        )
    }

    Scaffold(
        topBar = {
            SearchTopBar(
                text = searchQuery,
                onTextChange = {
                   searchViewModel.updateSearchQuery(query = it)
                },
                onSearchClicked = {
                   searchViewModel.searchHeroes(query = it)
                },
                onCloseClicked = {
                    navController.popBackStack()
                }
            )
        },
        content = {
            ListContent(heroes = heroes, navController = navController)
        }
    )
}
