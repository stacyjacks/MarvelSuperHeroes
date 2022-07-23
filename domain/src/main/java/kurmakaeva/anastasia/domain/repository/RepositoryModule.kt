package kurmakaeva.anastasia.domain.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kurmakaeva.anastasia.data.network.MarvelService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideHeroRepository(service: MarvelService) = HeroRepository(service)
}