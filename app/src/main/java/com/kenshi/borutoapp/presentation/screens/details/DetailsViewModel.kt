package com.kenshi.borutoapp.presentation.screens.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenshi.borutoapp.domain.model.Hero
import com.kenshi.borutoapp.domain.use_cases.UseCases
import com.kenshi.borutoapp.util.Constants.DETAILS_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // State와 StateFlow 의 차이 -> 해결
    // private val _selectedHero: MutableState<Hero?> = mutableStateOf(null)
    private val _selectedHero: MutableStateFlow<Hero?> = MutableStateFlow(null)
    val selectedHero: StateFlow<Hero?> = _selectedHero.asStateFlow()

    init {
        // Dispatchers.IO Do not need? -> need
        viewModelScope.launch(Dispatchers.IO) {
            // viewModelScope.launch {
            val heroId = savedStateHandle.get<Int>(DETAILS_ARGUMENT_KEY)
            _selectedHero.value = heroId?.let { useCases.getSelectedHeroUseCase(heroId = heroId) }
            // _selectedHero.value?.name?.let { Log.d("Hero", it) }
        }
    }

    // sharedFlow 를 사용해서 이벤트를 한번만 실행하도록(Configuration change가 일어나도 이벤트가 실행되지 않음)
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val _colorPalette: MutableState<Map<String, String>> = mutableStateOf(mapOf())
    val colorPalette: State<Map<String, String>> = _colorPalette

    // will emit the new value to our UI event, which is a generate color palette
    fun generateColorPalette() = viewModelScope.launch {
        _uiEvent.emit(UiEvent.GenerateColorPalette)
    }

    fun setColorPalette(colors: Map<String, String>) {
        _colorPalette.value = colors
    }
}

sealed class UiEvent {
    object GenerateColorPalette : UiEvent()
}