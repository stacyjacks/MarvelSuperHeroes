package kurmakaeva.anastasia.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kurmakaeva.anastasia.common.logger.Logger
import kurmakaeva.anastasia.domain.HeroRepositoryInterface
import kurmakaeva.anastasia.domain.entities.Hero

class HeroViewModel @AssistedInject constructor(
    private val repository: HeroRepositoryInterface,
    private val logger: Logger,
    @Assisted private val assistedInjection: HeroVMAssistedInjection
): ViewModel() {

    private val _hero = MutableStateFlow<HeroViewState>(HeroViewState.Loading)
    val hero: StateFlow<HeroViewState> = _hero

    init {
        getHero()
    }

    fun retry() {
        getHero()
    }

    private fun getHero() {
        viewModelScope.launch {
            runCatching {
                repository.loadSingleCharacter(assistedInjection.characterId)
            }.onSuccess { hero ->
                _hero.value = HeroViewState.Success(hero = hero)
            }.onFailure { e ->
                logger.log(e)
                _hero.value = HeroViewState.Error
            }
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(heroVMAssistedInjection: HeroVMAssistedInjection): HeroViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            heroVMAssistedInjection: HeroVMAssistedInjection
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(heroVMAssistedInjection) as T
            }
        }
    }

}

sealed class HeroViewState() {
    object Loading : HeroViewState()
    object Error : HeroViewState()
    data class Success(val hero: Hero) : HeroViewState()
}

data class HeroVMAssistedInjection(
    val characterId: Int
)