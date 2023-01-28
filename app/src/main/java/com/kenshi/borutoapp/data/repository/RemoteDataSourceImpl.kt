package com.kenshi.borutoapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kenshi.borutoapp.data.local.BorutoDatabase
import com.kenshi.borutoapp.data.local.paging_source.HeroRemoteMediator
import com.kenshi.borutoapp.data.local.paging_source.SearchHeroesSource
import com.kenshi.borutoapp.data.remote.BorutoApi
import com.kenshi.borutoapp.domain.model.Hero
import com.kenshi.borutoapp.domain.repository.RemoteDataSource
import com.kenshi.borutoapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class RemoteDataSourceImpl(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
) : RemoteDataSource {

    private val heroDao = borutoDatabase.heroDao()

    override fun getAllHeroes(): Flow<PagingData<Hero>> {
        val pagingSourceFactory = { heroDao.getAllHeroes() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = HeroRemoteMediator(
                //HeroRemoteMediator 클래스에서 @Inject constructor 키워드를 삭제한 이유
                //이미 두 의존성을 전달하고 있음
                borutoApi = borutoApi,
                borutoDatabase = borutoDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(query: String): Flow<PagingData<Hero>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchHeroesSource(
                    // 마찬가지로 이미 의존성을 전달하고 있음(@Inject constuctor keyword 삭제)
                    borutoApi = borutoApi,
                    query = query
                )
            }
        ).flow
    }
}