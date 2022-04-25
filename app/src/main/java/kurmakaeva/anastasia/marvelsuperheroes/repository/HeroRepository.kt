package kurmakaeva.anastasia.marvelsuperheroes.repository

import kurmakaeva.anastasia.marvelsuperheroes.entities.Hero
import kurmakaeva.anastasia.marvelsuperheroes.entities.HeroUrl
import kurmakaeva.anastasia.marvelsuperheroes.network.MarvelService

class HeroRepository(private val service: MarvelService) {
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