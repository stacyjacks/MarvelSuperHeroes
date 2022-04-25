package kurmakaeva.anastasia.marvelsuperheroes.network

import kurmakaeva.anastasia.marvelsuperheroes.network.dtos.HeroDTO
import kurmakaeva.anastasia.marvelsuperheroes.network.dtos.ResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {
    @GET("/v1/public/characters")
    suspend fun loadAllCharacters(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int
    ): ResponseDTO

    @GET("/v1/public/characters/{characterId}")
    suspend fun loadCharacter(
        @Path("characterId") characterId: Int
    ): ResponseDTO
}
