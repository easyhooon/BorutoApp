package com.kenshi.borutoapp.presentation.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenshi.borutoapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel(){
    fun saveOnBoardingState(completed: Boolean) {
        //dispatcher io 안붙혀도 될듯? 이젠 ㅇㅇ 안붙혀도 됨
        viewModelScope.launch {
            useCases.saveOnBoardingUseCase(completed = completed)
        }
    }
}