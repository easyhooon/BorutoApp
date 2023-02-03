package com.kenshi.borutoapp.presentation.screens.splash

//@HiltViewModel
//class SplashViewModel @Inject constructor(
//    private val useCases: UseCases
//) : ViewModel() {
//
//    //한번 finished 버튼이 눌렸을 경우 onBoarding 화면이 더이상 나타나지 않도록 구현
//    private val _onBoardingCompleted = MutableStateFlow(false)
//    val onBoardingCompleted: StateFlow<Boolean> = _onBoardingCompleted
//
//    init {
//        viewModelScope.launch {
//            _onBoardingCompleted.value =
//                useCases.readOnBoardingUseCase().stateIn(viewModelScope).value
//        }
//    }
//}