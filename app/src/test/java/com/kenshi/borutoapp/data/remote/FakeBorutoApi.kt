package com.kenshi.borutoapp.data.remote

import com.kenshi.borutoapp.domain.model.ApiResponse
import com.kenshi.borutoapp.domain.model.Hero

// 반드시 프로젝트에서와 동일한 패키지 구조를 따라야 함
class FakeBorutoApi: BorutoApi {

    private val heroes = listOf(
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

    override suspend fun getAllHeroes(page: Int): ApiResponse {
        return ApiResponse(
            success = false
        )
    }

    // fakeBorutoApi 를 searchHeroesSource test class 에 전달(PagingSource Test)
    override suspend fun searchHeroes(name: String): ApiResponse {
        val searchHeroes = findHeros(name = name)
        return ApiResponse(
            success = true,
            message = "ok",
            heroes = searchHeroes
        )
    }

    private fun findHeros(name: String): List<Hero> {
        val founded = mutableListOf<Hero>()
        return if (name.isNotEmpty()) {
            heroes.forEach { hero ->
                if (hero.name.lowercase().contains(name.lowercase())) {
                    founded.add(hero)
                }
            }
            founded
        } else {
            emptyList()
        }
    }
}