package kurmakaeva.anastasia.marvelsuperheroes.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyGridState
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
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kurmakaeva.anastasia.marvelsuperheroes.R
import kurmakaeva.anastasia.marvelsuperheroes.entities.Hero
import kurmakaeva.anastasia.marvelsuperheroes.ui.AppScaffold
import kurmakaeva.anastasia.marvelsuperheroes.ui.EmptyState
import kurmakaeva.anastasia.marvelsuperheroes.ui.LoadingIndicator
import kurmakaeva.anastasia.marvelsuperheroes.ui.RetryButton
import kurmakaeva.anastasia.marvelsuperheroes.ui.theme.*

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
                                modifier = Modifier.background(color = RedPrimary),
                            ) {
                                val heroes = viewModel.superHeroes.collectAsLazyPagingItems()
                                var isRefreshing by remember { mutableStateOf(false) }

                                LaunchedEffect(isRefreshing) {
                                    if (isRefreshing) {
                                        delay(2000)
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
                                            LoadingIndicator()
                                        }
                                        LoadState.Loading -> {
                                            LoadingIndicator()
                                        }
                                        is LoadState.Error -> {
                                            EmptyState()
                                        }
                                    }

                                    LazyVerticalGrid(
                                        cells = GridCells.Fixed(2),
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
        val httpsThumbnailPath = hero.thumbnailPath.replace("http", "https")
        val imageUrl = httpsThumbnailPath + "/standard_xlarge." + hero.thumbnailExtension

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
            GlideImage(
                imageModel = imageUrl,
                contentScale = ContentScale.Crop,
                placeHolder = painterResource(id = R.drawable.marvel_bw)
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
}
