package kurmakaeva.anastasia.domain.repository

import kurmakaeva.anastasia.data.network.MarvelService
import kurmakaeva.anastasia.domain.repository.entities.Hero
import kurmakaeva.anastasia.domain.repository.entities.HeroUrl

class HeroRepository(private val service: MarvelService) {
    suspend fun loadCharacters(loadSize: Int, key: Int?): List<Hero> {
        val response = service.loadAllCharacters(loadSize, key ?: 0)
        val listOfCharacters = response.data

        return listOfCharacters.results.map {
            Hero(
                id = it.id,
                name = it.name,
                thumbnailPath = it.thumbnail.path,
                thumbnailExtension = it.thumbnail.extension
            )
        }
    }

    suspend fun loadSingleCharacter(characterId: Int): Hero {
        val character = service.loadCharacter(characterId).data.results.first()
        return Hero(
            character.id,
            character.name,
            character.thumbnail.path,
            character.thumbnail.extension,
            character.description,
            character.urls.map { HeroUrl(it.url) }
        )
    }
}