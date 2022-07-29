package kurmakaeva.anastasia.domain.repository

import kurmakaeva.anastasia.data.network.dtos.HeroDTO
import kurmakaeva.anastasia.data.network.dtos.ResultsDTO
import kurmakaeva.anastasia.domain.repository.entities.Hero
import kurmakaeva.anastasia.domain.repository.entities.HeroUrl

fun ResultsDTO.toHeroes(): List<Hero> = results.map { it.toHero() }

fun HeroDTO.toHero(): Hero = Hero(
    id,
    name,
    thumbnail.path,
    thumbnail.extension,
    description,
    urls.map { HeroUrl(it.url) }
)