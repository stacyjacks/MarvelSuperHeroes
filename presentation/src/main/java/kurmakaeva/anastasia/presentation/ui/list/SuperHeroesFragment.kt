package kurmakaeva.anastasia.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kurmakaeva.anastasia.domain.entities.Hero
import kurmakaeva.anastasia.presentation.R
import kurmakaeva.anastasia.presentation.ui.AppScaffold
import kurmakaeva.anastasia.presentation.ui.COLUMN_NUMBER
import kurmakaeva.anastasia.presentation.ui.EmptyState
import kurmakaeva.anastasia.presentation.ui.HTTP
import kurmakaeva.anastasia.presentation.ui.HTTPS
import kurmakaeva.anastasia.presentation.ui.LoadingIndicator
import kurmakaeva.anastasia.presentation.ui.REFRESH_DELAY
import kurmakaeva.anastasia.presentation.ui.RetryButton
import kurmakaeva.anastasia.presentation.ui.THUMBNAIL_PATH_XLARGE
import kurmakaeva.anastasia.presentation.ui.theme.MarvelSuperHeroesTheme
import kurmakaeva.anastasia.presentation.ui.theme.RedPrimary
import kurmakaeva.anastasia.presentation.ui.theme.Secondary
import kurmakaeva.anastasia.presentation.ui.theme.White

@AndroidEntryPoint
@ExperimentalFoundationApi
class SuperHeroesFragment : Fragment() {
    private val viewModel: SuperHeroesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MarvelSuperHeroesTheme() {
                    AppScaffold(
                        content = {
                            Surface(
                                modifier = Modifier
                                    .background(color = RedPrimary)
                                    .padding(it)
                            ) {
                                val heroes = viewModel.superHeroes.collectAsLazyPagingItems()
                                
                                SwipeRefreshList(heroes = heroes)
                            }
                        },
                        actionBarTitle = stringResource(id = R.string.main_screen_title),
                        showNavigateUp = false
                    )
                }
            }
        }
    }

    @Composable
    fun HeroItem(hero: Hero) {
        val httpsThumbnailPath = hero.thumbnailPath.replace(HTTP, HTTPS)
        val imageUrl = httpsThumbnailPath + THUMBNAIL_PATH_XLARGE + hero.thumbnailExtension

        ImageCard(hero = hero, imageUrl = imageUrl)
    }

    @Composable
    fun ImageCard(hero: Hero, imageUrl: String) {
        Box(
            modifier = Modifier
                .clickable {
                    findNavController().navigate(
                        SuperHeroesFragmentDirections
                            .actionGoToHeroFragment(
                                characterId = hero.id,
                                heroName = hero.name
                            )
                    )
                }
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = hero.name,
                modifier = Modifier.fillMaxWidth(),
                placeholder = painterResource(id = R.drawable.marvel_bw),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Secondary
                            )
                        )
                    )
            ) {
                Text(
                    text = hero.name,
                    modifier = Modifier.padding(8.dp),
                    color = White
                )
            }
        }
    }

    @Composable
    fun SwipeRefreshList(heroes: LazyPagingItems<Hero>) {
        var isRefreshing by remember { mutableStateOf(false) }

        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                delay(REFRESH_DELAY)
                isRefreshing = false
            }
        }
        
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = {
                isRefreshing = true
                heroes.retry()
            },
        ) {
            when (heroes.loadState.refresh) {
                is LoadState.NotLoading -> {
                    // do nothing
                }
                LoadState.Loading -> {
                    LoadingIndicator()
                }
                is LoadState.Error -> {
                    EmptyState()
                }
            }
            
            ListOfHeroes(heroes = heroes)
        }
    }

    @Composable
    fun ListOfHeroes(heroes: LazyPagingItems<Hero>) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(COLUMN_NUMBER),
            state = rememberLazyGridState()
        ) {
            items(heroes.itemCount) { index ->
                heroes[index]?.let { HeroItem(it) }
            }

            when (heroes.loadState.append) {
                LoadState.Loading -> {
                    item { LoadingIndicator() }
                    item { LoadingIndicator() }
                }
                is LoadState.Error -> {
                    item { RetryButton(heroes = heroes) }
                }
                is LoadState.NotLoading -> {
                    // do nothing
                }
            }
        }
    }
}
