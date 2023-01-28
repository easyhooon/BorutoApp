package com.kenshi.borutoapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveOnBoardingState(completed: Boolean)
    //this function is actually flow, and by default flow is asynchronous data stream
    fun readOnBoardingState(): Flow<Boolean>
}