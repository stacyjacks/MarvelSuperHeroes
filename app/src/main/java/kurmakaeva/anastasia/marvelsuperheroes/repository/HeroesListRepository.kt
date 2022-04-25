package kurmakaeva.anastasia.marvelsuperheroes.repository

import kurmakaeva.anastasia.marvelsuperheroes.entities.Hero
import kurmakaeva.anastasia.marvelsuperheroes.network.MarvelService
import javax.inject.Inject

class HeroesListRepository @Inject constructor(private val service: MarvelService) {
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
}