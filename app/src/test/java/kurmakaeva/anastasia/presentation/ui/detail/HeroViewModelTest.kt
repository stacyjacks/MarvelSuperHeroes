package kurmakaeva.anastasia.presentation.ui.detail

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kurmakaeva.anastasia.common.logger.Logger
import kurmakaeva.anastasia.domain.entities.Hero
import kurmakaeva.anastasia.marvelsuperheroes.repository.FakeHeroRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class HeroViewModelTest {
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var repository: FakeHeroRepository
    private lateinit var viewModel: HeroViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test happy path`() = runTest {
        // Given
        repository = FakeHeroRepository(false)
        viewModel = HeroViewModel(repository, Logger(), HeroVMAssistedInjection(0))

        // When
        val hero = viewModel.hero.value

        // Then
        assertEquals(HeroViewState.Success(Hero()), hero)
    }

    @Test
    fun `test sad path`() {
        // Given
        repository = FakeHeroRepository(true)
        viewModel = HeroViewModel(repository, Logger(), HeroVMAssistedInjection(0))

        // When
        val hero = viewModel.hero.value

        // Then
        assertEquals(HeroViewState.Error, hero)
    }
}