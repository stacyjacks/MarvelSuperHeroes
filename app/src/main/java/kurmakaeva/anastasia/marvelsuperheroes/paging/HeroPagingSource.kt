package kurmakaeva.anastasia.marvelsuperheroes.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kurmakaeva.anastasia.marvelsuperheroes.entities.Hero
import kurmakaeva.anastasia.marvelsuperheroes.repository.HeroRepository
import java.lang.Exception

class HeroPagingSource(private val repository: HeroRepository): PagingSource<Int, Hero>() {
    private var offset = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hero> {
        return try {
            offset += params.loadSize
            LoadResult.Page(
                data = repository.loadCharacters(params.loadSize, params.key ?: 0),
                prevKey = null,
                nextKey = offset
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Hero>): Int? = state.anchorPosition
}