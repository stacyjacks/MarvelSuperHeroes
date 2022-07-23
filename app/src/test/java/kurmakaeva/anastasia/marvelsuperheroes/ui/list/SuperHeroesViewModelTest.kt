package kurmakaeva.anastasia.marvelsuperheroes.ui.list

import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kurmakaeva.anastasia.domain.repository.entities.Hero
import kurmakaeva.anastasia.data.network.MarvelService
import kurmakaeva.anastasia.marvelsuperheroes.paging.FakeHeroFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SuperHeroesViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: kurmakaeva.anastasia.presentation.ui.list.SuperHeroesViewModel
    private lateinit var repository: kurmakaeva.anastasia.domain.repository.HeroRepository

    private val fakeHeroFactory = FakeHeroFactory()
    private val fakeHeroes = mutableListOf(
        fakeHeroFactory.createFakeUser("Captain America", "captain_america"),
        fakeHeroFactory.createFakeUser("Thor", "thor"),
        fakeHeroFactory.createFakeUser("Black Widow", "black_widow")
    )

    @Mock
    private val service = Mockito.mock(MarvelService::class.java)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        repository = kurmakaeva.anastasia.domain.repository.HeroRepository(service)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getFirstCharacter_dataLoadingOk() = runTest {
        // Given a VM
        viewModel = kurmakaeva.anastasia.presentation.ui.list.SuperHeroesViewModel(repository)

        // When
        viewModel.superHeroes.first().map { hero ->
            Hero(
                hero.id,
                hero.name,
                hero.thumbnailPath,
                hero.thumbnailExtension,
                hero.description,
                hero.urls
            )

            // Then
            assertEquals(hero.name, fakeHeroes.first().name)
        }
    }
}