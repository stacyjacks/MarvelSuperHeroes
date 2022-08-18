package kurmakaeva.anastasia.presentation.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kurmakaeva.anastasia.domain.entities.Hero
import kurmakaeva.anastasia.presentation.R
import kurmakaeva.anastasia.presentation.ui.AppScaffold
import kurmakaeva.anastasia.presentation.ui.EmptyState
import kurmakaeva.anastasia.presentation.ui.HTTP
import kurmakaeva.anastasia.presentation.ui.HTTPS
import kurmakaeva.anastasia.presentation.ui.IMAGE_PATH_FANTASTIC
import kurmakaeva.anastasia.presentation.ui.LoadingIndicator
import kurmakaeva.anastasia.presentation.ui.REFRESH_DELAY
import kurmakaeva.anastasia.presentation.ui.ReadMoreButton
import kurmakaeva.anastasia.presentation.ui.theme.RedPrimary
import javax.inject.Inject

@AndroidEntryPoint
class HeroFragment : Fragment() {
    private val args by navArgs<HeroFragmentArgs>()

    @Inject
    lateinit var assistedFactory: HeroViewModel.AssistedFactory
    private val viewModel: HeroViewModel by viewModels {
        HeroViewModel.provideFactory(
            assistedFactory,
            HeroVMAssistedInjection(args.characterId)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppScaffold(
                    showNavigateUp = true,
                    actionBarTitle = args.heroName,
                    content = {
                        Surface(modifier = Modifier
                            .fillMaxSize()
                            .background(color = RedPrimary)
                        ) {
                            val state by viewModel.hero.collectAsState()
                            
                            SwipeRefreshDetail(state = state)
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun HeroDetailView(imageUrl: String, hero: Hero) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            AsyncImage(
                model = imageUrl,
                contentDescription = hero.name,
                modifier = Modifier.fillMaxWidth(),
                placeholder = painterResource(id = R.drawable.marvel_bw),
                contentScale = ContentScale.Crop
            )

            Row(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = hero.description.ifEmpty { stringResource(id = R.string.no_description) },
                    fontSize = 18.sp
                )
            }

            Row(modifier = Modifier.padding(8.dp)) {
                val viewInBrowserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(hero.urls.first().url.replace(HTTP, HTTPS))
                )

                ReadMoreButton { startActivity(viewInBrowserIntent) }
            }
        }
    }
    
    @Composable
    fun SwipeRefreshDetail(state: HeroViewState) {
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
                viewModel.retry()
            },
        ) {
            when (state) {
                HeroViewState.Loading -> {
                    LoadingIndicator()
                }
                HeroViewState.Error -> {
                    EmptyState()
                }
                is HeroViewState.Success -> {
                    val httpsThumbnailPath = state.hero.thumbnailPath.replace(HTTP, HTTPS)
                    val imageUrl = httpsThumbnailPath + IMAGE_PATH_FANTASTIC +
                            state.hero.thumbnailExtension

                    HeroDetailView(
                        imageUrl = imageUrl,
                        hero = state.hero
                    )
                }
            }
        }
    }
}