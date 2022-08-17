package kurmakaeva.anastasia.domain

import kurmakaeva.anastasia.domain.entities.Hero

interface HeroRepositoryInterface {
    suspend fun loadCharacters(loadSize: Int, key: Int?): List<Hero>

    suspend fun loadSingleCharacter(characterId: Int): Hero
}