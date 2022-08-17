package kurmakaeva.anastasia.marvelsuperheroes.paging

import kurmakaeva.anastasia.domain.entities.Hero
import java.util.concurrent.atomic.AtomicInteger

class FakeHeroFactory {
    private val counter = AtomicInteger(0)

    fun createFakeUser(name: String, thumbnailPath: String): Hero {
        val id = counter.incrementAndGet()
        return Hero(
            id = id,
            name = name,
            thumbnailPath = thumbnailPath,
            thumbnailExtension = ".jpg",
            description = "Dummy description",
            urls = listOf()
        )
    }
}