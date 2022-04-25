package kurmakaeva.anastasia.marvelsuperheroes.entities

data class Hero(
    val id: Int = 0,
    val name: String = "",
    val thumbnailPath: String = "",
    val thumbnailExtension: String = "",
    val description: String = "",
    val urls: List<HeroUrl> = listOf()
)

data class HeroUrl(
    val url: String = ""
)
