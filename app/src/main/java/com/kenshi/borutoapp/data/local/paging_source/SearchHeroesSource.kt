package com.kenshi.borutoapp.data.local.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kenshi.borutoapp.data.remote.BorutoApi
import com.kenshi.borutoapp.domain.model.Hero

//class SearchHeroesSource @Inject constructor(
//    private val borutoApi: BorutoApi,
//    private val query: String
//): PagingSource<Int, Hero>() {

class SearchHeroesSource (
    private val borutoApi: BorutoApi,
    private val query: String
) : PagingSource<Int, Hero>() {

    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, Hero> {
        return try {
            val apiResponse = borutoApi.searchHeroes(name = query)
            val heroes = apiResponse.heroes
            if (heroes.isNotEmpty()) {
                LoadResult.Page(
                    data = heroes,
                    prevKey = apiResponse.prevPage,
                    nextKey = apiResponse.nextPage
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null,
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Hero>): Int? {
        // Most recently accessed index in the list, including placeholders.
        // null if no access in the PagingData has been made yet.
        return state.anchorPosition
    }
}