package kurmakaeva.anastasia.data.network.dtos

data class ResponseDTO(
    val data: ResultsDTO
)

data class ResultsDTO(
    val results: List<HeroDTO>
)

data class HeroDTO(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: HeroThumbnailDTO,
    val urls: List<HeroUrlDTO>
)

data class HeroThumbnailDTO(
    val path: String,
    val extension: String
)

data class HeroUrlDTO(
    val url: String
)