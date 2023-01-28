package com.kenshi.borutoapp.data.repository

import com.kenshi.borutoapp.data.local.BorutoDatabase
import com.kenshi.borutoapp.domain.model.Hero
import com.kenshi.borutoapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(
    borutoDatabase: BorutoDatabase
): LocalDataSource {

    private val heroDao = borutoDatabase.heroDao()

    override suspend fun getSelectedHero(heroId: Int): Hero {
        return heroDao.getSelectedHero(heroId = heroId)
    }
}