package kurmakaeva.anastasia.data.repository

import kurmakaeva.anastasia.data.network.MarvelService
import kurmakaeva.anastasia.domain.HeroRepositoryInterface
import kurmakaeva.anastasia.domain.entities.Hero

class HeroRepository(private val service: MarvelService): HeroRepositoryInterface {
    override suspend fun loadCharacters(loadSize: Int, key: Int?): List<Hero> {
        val response = service.loadAllCharacters(loadSize, key ?: 0)
        val listOfCharacters = response.data

        return listOfCharacters.toHeroes()
    }

    override suspend fun loadSingleCharacter(characterId: Int): Hero {
        val character = service.loadCharacter(characterId).data.results.first()
        return character.toHero()
    }
}