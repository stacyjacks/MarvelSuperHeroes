package kurmakaeva.anastasia.marvelsuperheroes.repository

import kurmakaeva.anastasia.data.network.dtos.HeroDTO
import kurmakaeva.anastasia.data.network.dtos.HeroThumbnailDTO
import kurmakaeva.anastasia.data.network.dtos.HeroUrlDTO
import kurmakaeva.anastasia.domain.entities.Hero
import kurmakaeva.anastasia.domain.entities.HeroUrl
import kurmakaeva.anastasia.data.repository.toHero
import org.junit.Assert.assertEquals
import org.junit.Test

class HeroMappingTest {
    private val id = 1
    private val name = "Thor"
    private val description = "God of Thunder"
    private val thumbnailPath = "image"
    private val thumbnailExtension = ".jpg"
    private val urls = listOf<HeroUrlDTO>()

    @Test
    fun heroDtoToEntity() {
        val heroDTO = HeroDTO(
            id = id,
            name = name,
            description = description,
            thumbnail = HeroThumbnailDTO(thumbnailPath, thumbnailExtension),
            urls = urls
        )

        val mappedHero = heroDTO.toHero()

        assertEquals(
            Hero(id, name, thumbnailPath, thumbnailExtension, description, urls.map { HeroUrl(it.url) }),
            mappedHero
        )
    }
}