package com.kenshi.borutoapp.domain.use_cases.save_onboarding

import com.kenshi.borutoapp.data.repository.Repository

class SaveOnBoardingUseCase(
    private val repository: Repository
) {
    //operator fun
    //명시적으로 Invoke 함수 를 호출하지 않아도 자동으로 호출하기 위해
    suspend operator fun invoke(completed: Boolean) {
        repository.saveOnBoardingState(completed = completed)
    }
}