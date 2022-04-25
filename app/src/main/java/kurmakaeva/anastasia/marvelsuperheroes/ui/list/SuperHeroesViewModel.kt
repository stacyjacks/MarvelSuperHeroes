package kurmakaeva.anastasia.marvelsuperheroes.ui.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kurmakaeva.anastasia.marvelsuperheroes.entities.Hero
import kurmakaeva.anastasia.marvelsuperheroes.paging.HeroPagingSource
import kurmakaeva.anastasia.marvelsuperheroes.repository.HeroesListRepository
import javax.inject.Inject

@HiltViewModel
class SuperHeroesViewModel @Inject constructor(private val repository: HeroesListRepository): ViewModel() {
    val superHeroes: Flow<PagingData<Hero>> =
        Pager(PagingConfig(pageSize = 10)) { HeroPagingSource(repository) }
        .flow
        .cachedIn(viewModelScope)
}