package com.kenshi.borutoapp.domain.model

import kotlinx.serialization.Serializable

//TODO domain 은 순수한 코틀린 기반이어야 되는데 @Serializable... 근데 이게 안드로이드 종속성이 맞나?
@Serializable
data class ApiResponse(
    val success: Boolean,
    val message: String? = null,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
    val heroes: List<Hero> = emptyList(),
    val lastUpdated: Long? = null
)
