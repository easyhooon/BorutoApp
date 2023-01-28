package com.kenshi.borutoapp.domain.use_cases.get_selected_hero

import com.kenshi.borutoapp.data.repository.Repository
import com.kenshi.borutoapp.domain.model.Hero

class GetSelectedHeroUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(heroId: Int): Hero {
        return repository.getSelectedHero(heroId = heroId)
    }
}