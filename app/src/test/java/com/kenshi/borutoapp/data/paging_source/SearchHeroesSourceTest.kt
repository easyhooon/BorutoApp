package com.kenshi.borutoapp.data.paging_source

import androidx.paging.PagingSource
import com.kenshi.borutoapp.data.local.paging_source.SearchHeroesSource
import com.kenshi.borutoapp.data.remote.BorutoApi
import com.kenshi.borutoapp.data.remote.FakeBorutoApi
import com.kenshi.borutoapp.domain.model.Hero
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class SearchHeroesSourceTest {

    private lateinit var borutoApi: BorutoApi
    private lateinit var heroes: List<Hero>

    @Before
    fun setup() {
        borutoApi = FakeBorutoApi()
        heroes = listOf(
            Hero(
                id = 1,
                name = "Sasuke",
                image = "",
                about = "",
                rating = 5.0,
                power = 0,
                month = "",
                day = "",
                family = listOf(),
                abilities = listOf(),
                natureTypes = listOf(),
            ),
            Hero(
                id = 2,
                name = "Naruto",
                image = "",
                about = "",
                rating = 5.0,
                power = 0,
                month = "",
                day = "",
                family = listOf(),
                abilities = listOf(),
                natureTypes = listOf(),
            ),
            Hero(
                id = 3,
                name = "Sakura",
                image = "",
                about = "",
                rating = 5.0,
                power = 0,
                month = "",
                day = "",
                family = listOf(),
                abilities = listOf(),
                natureTypes = listOf(),
            )
        )
    }

    @Test
    // 이 함수가 실제로 무엇을 하는지, 우리가 기대하는 결과와 주장할 내용을 작성하고 설명(마침표는 사용할 수 없음)
    // load function 이 suspend function 이기 때문에 runBlocking 으로 감싸줌
    fun `Search api with existing hero name, expect single hero result, assert LoadResul_Page`() =
        //runBlockingTest is deprecated
        runTest {
            //setup에서 정의한 borutoApi 제공
            val heroSource = SearchHeroesSource(borutoApi = borutoApi, query = "Sasuke")
            //어떤 타입의 파라미터를 사용할지 명시해야함
            assertEquals<PagingSource.LoadResult<Int, Hero>>(
                expected = PagingSource.LoadResult.Page(
                    // test 의 결과로 query == Sasuke 만 성공하도록 설계
                    // test 가 실패했을 경우 로그에 CLick to see diffenece 하이퍼링크를 클릭하여 결과를 비교해볼 수 있다.
                    data = listOf(heroes.first()),
                    prevKey = null,
                    nextKey = null
                ),
                //expected result
                actual = heroSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 3,
                        placeholdersEnabled = false
                    )
                )
            )
        }

    @Test
    fun `Search api with existing hero name, expect mutiple hero result, assert LoadResul_Page`() =
        //runBlockingTest is deprecated
        runTest {
            //setup에서 정의한 borutoApi 제공
            val heroSource = SearchHeroesSource(borutoApi = borutoApi, query = "Sa")
            //어떤 타입의 파라미터를 사용할지 명시해야함
            assertEquals<PagingSource.LoadResult<Int, Hero>>(
                expected = PagingSource.LoadResult.Page(
                    data = listOf(heroes.first(), heroes[2]),
                    prevKey = null,
                    nextKey = null
                ),
                //expected result
                actual = heroSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 3,
                        placeholdersEnabled = false
                    )
                )
            )
        }

    @Test
    fun `Search api with empty hero name, assert empty heroes list and LoadResul_Page`() =
        //runBlockingTest is deprecated
        runTest {
            //setup에서 정의한 borutoApi 제공
            val heroSource = SearchHeroesSource(borutoApi = borutoApi, query = "")
            val loadResult = heroSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )

            val result = borutoApi.searchHeroes("").heroes

            assertTrue { result.isEmpty() }
            assertTrue { loadResult is PagingSource.LoadResult.Page}
        }

    @Test
    fun `Search api with non-existing hero name, assert empty heroes list and LoadResul_Page`() =
        //runBlockingTest is deprecated
        runTest {
            //setup에서 정의한 borutoApi 제공
            //query 에 Sas를 넣으면 fail 빈결과가 뜨지 않기때문에
            val heroSource = SearchHeroesSource(borutoApi = borutoApi, query = "Unknown")
            val loadResult = heroSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )

            val result = borutoApi.searchHeroes("Unknown").heroes

            assertTrue { result.isEmpty() }
            assertTrue { loadResult is PagingSource.LoadResult.Page}
        }
}