package kurmakaeva.anastasia.marvelsuperheroes.repository

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kurmakaeva.anastasia.domain.HeroRepositoryInterface
import kurmakaeva.anastasia.domain.entities.Hero
import org.junit.jupiter.api.fail

class FakeHeroRepository(private val errorLoading: Boolean): HeroRepositoryInterface {
    val list = mutableListOf<Hero>()

    override suspend fun loadCharacters(loadSize: Int, key: Int?): List<Hero> {
        return flow { emit(list) }.single()
    }

    override suspend fun loadSingleCharacter(characterId: Int): Hero {
        list.add(Hero())
        return if (errorLoading) {
            fail("Error")
        } else {
            flow { emit(list[characterId]) }.single()

        }
    }
}