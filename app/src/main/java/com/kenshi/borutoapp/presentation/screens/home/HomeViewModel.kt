package com.kenshi.borutoapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.kenshi.borutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    useCases: UseCases
) : ViewModel() {
    //read data from local database(Single source of truth by RemoteMediator)
    //cached function 을 통해 받아온 데이터를 뷰모델에 캐싱하지 않는 이유
    // -> remoteMediator 를 이용해서 local database 에 캐싱하기 때문
     val getAllHeroes = useCases.getAllHeroesUseCase()
}