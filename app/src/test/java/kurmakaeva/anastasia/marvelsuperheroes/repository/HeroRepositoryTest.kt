package kurmakaeva.anastasia.marvelsuperheroes.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kurmakaeva.anastasia.data.network.MarvelService
import kurmakaeva.anastasia.data.network.dtos.HeroDTO
import kurmakaeva.anastasia.data.network.dtos.ResponseDTO
import kurmakaeva.anastasia.data.network.dtos.ResultsDTO
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HeroRepositoryTest {
    private lateinit var repository: HeroRepository

    @Mock
    private val service = Mockito.mock(MarvelService::class.java)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(service)
    }

    @After
    fun tearDown() {
        MockitoAnnotations.openMocks(service).close()
    }

    @Test
    fun heroesListRepository_dataReturningOk() = runTest {
        repository = HeroRepository(service)

        val listDTO = emptyList<HeroDTO>()
        val resultsDTO = ResultsDTO(results = listDTO)
        val responseDTO = ResponseDTO(data = resultsDTO)

        `when`(service.loadAllCharacters(0, 0)).thenReturn(responseDTO)

        val repositoryList = repository.loadCharacters(0, 0)

        assertEquals(repositoryList.size, listDTO.size)
    }
}