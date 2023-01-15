package kurmakaeva.anastasia.data.repository

import kurmakaeva.anastasia.common.HTTP
import kurmakaeva.anastasia.common.HTTPS
import kurmakaeva.anastasia.data.network.dtos.HeroDTO
import kurmakaeva.anastasia.data.network.dtos.ResultsDTO
import kurmakaeva.anastasia.domain.entities.Hero
import kurmakaeva.anastasia.domain.entities.HeroUrl

fun ResultsDTO.toHeroes(): List<Hero> = results.map { it.toHero() }

fun HeroDTO.toHero(): Hero = Hero(
    id,
    name,
    thumbnail.path.replace(HTTP, HTTPS),
    thumbnail.extension,
    description,
    urls.map { HeroUrl(it.url.replace(HTTP, HTTPS)) }
)