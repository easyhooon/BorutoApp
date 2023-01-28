package com.kenshi.borutoapp.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kenshi.borutoapp.domain.model.Hero
import com.kenshi.borutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    //UseCases 클래스를 주입받는다
    private val useCase: UseCases
): ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchHeroes = MutableStateFlow<PagingData<Hero>>(PagingData.empty())
    val searchHeroes = _searchHeroes

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchHeroes(query: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            useCase.searchHeroesUseCase(query = query).cachedIn(viewModelScope).collect{
//                _searchHeres.value = it
//            }
//        }
        // call searchHeroesUsecase and collect result and store this variable (observe from SearchScreen)
        // Usecase for request to our server and receive heroes which we are looking for
        // use cachedIn function to cache the result of searchHero in our viewModel
        // remoteMediator 를 사용하지 않기 때문에 cachedIn 함수를 통해 viewModel 에 data 를 캐싱한다.
        // 뷰모델에 데이터를 캐싱하는 이유 -> request 를 요청한 이후에 configuration change 가 일어날 경우 또 다른 request 를 요청하는 것을 원치 않기 때문에
        // 구현 결과: configuration 이 일어나도 검색 api 가 재호출되지 않는 것을 확인
//        viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.launch {
            useCase.searchHeroesUseCase(query = query).cachedIn(viewModelScope).collect{
                _searchHeroes.value = it
            }
        }
    }
}