package kurmakaeva.anastasia.data.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kurmakaeva.anastasia.data.network.MarvelService
import kurmakaeva.anastasia.domain.HeroRepositoryInterface
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideHeroRepository(service: MarvelService): HeroRepositoryInterface = HeroRepository(service)
}