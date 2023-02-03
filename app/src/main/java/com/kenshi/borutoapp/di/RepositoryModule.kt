package com.kenshi.borutoapp.di

import android.content.Context
import com.kenshi.borutoapp.data.repository.DataStoreOperationsImpl
import com.kenshi.borutoapp.data.repository.Repository
import com.kenshi.borutoapp.domain.repository.DataStoreOperations
import com.kenshi.borutoapp.domain.use_cases.UseCases
import com.kenshi.borutoapp.domain.use_cases.get_all_heroes.GetAllHeroesUseCase
import com.kenshi.borutoapp.domain.use_cases.get_selected_hero.GetSelectedHeroUseCase
import com.kenshi.borutoapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.kenshi.borutoapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.kenshi.borutoapp.domain.use_cases.search_heroes.SearchHeroesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(@ApplicationContext context: Context): DataStoreOperations {
        return DataStoreOperationsImpl(context = context)
    }


    // UseCase 도 똑같은 방식으로 repository 를 모듈의 형태로 주입해준다
    // @Inject constructor 방법이 아니라
    @Provides
    @Singleton
    fun provideUserCases(repository: Repository) : UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository),
            getAllHeroesUseCase = GetAllHeroesUseCase(repository),
            searchHeroesUseCase = SearchHeroesUseCase(repository),
            getSelectedHeroUseCase = GetSelectedHeroUseCase(repository)
        )
    }
}