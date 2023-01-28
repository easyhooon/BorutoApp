package com.kenshi.borutoapp.domain.repository

import com.kenshi.borutoapp.domain.model.Hero

interface LocalDataSource {

    suspend fun getSelectedHero(heroId: Int): Hero
}