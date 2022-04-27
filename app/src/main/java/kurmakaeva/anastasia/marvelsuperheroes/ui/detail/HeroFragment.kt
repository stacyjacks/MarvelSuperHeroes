package kurmakaeva.anastasia.marvelsuperheroes.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import kurmakaeva.anastasia.marvelsuperheroes.R
import kurmakaeva.anastasia.marvelsuperheroes.entities.Hero
import kurmakaeva.anastasia.marvelsuperheroes.ui.AppScaffold
import kurmakaeva.anastasia.marvelsuperheroes.ui.theme.RedPrimary
import kurmakaeva.anastasia.marvelsuperheroes.ui.theme.White
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
                        val state by viewModel.hero.collectAsState()

                        when (state) {
                            HeroViewState.Loading -> {
                                // handle loading
                            }
                            HeroViewState.Error -> {
                                // handle error
                            }
                            is HeroViewState.Success -> {
                                val httpsThumbnailPath =
                                    (state as HeroViewState.Success).hero.thumbnailPath
                                        .replace("http", "https")
                                val imageUrl = httpsThumbnailPath +
                                        "/standard_fantastic." +
                                        (state as HeroViewState.Success).hero.thumbnailExtension

                                HeroDetailView(
                                    imageUrl = imageUrl,
                                    hero = (state as HeroViewState.Success).hero
                                )
                            }
                        }
                    })
            }
        }
    }

    @Composable
    fun HeroDetailView(imageUrl: String, hero: Hero) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            GlideImage(
                imageModel = imageUrl,
                placeHolder = painterResource(id = R.drawable.ic_error)
            )

            Row(modifier = Modifier.padding(8.dp)) {
                Text(
                    text =
                    if (hero.description.isNotEmpty()) {
                        hero.description
                    } else {
                        stringResource(id = R.string.no_description)
                    },
                    fontSize = 18.sp
                )
            }

            Row(modifier = Modifier.padding(8.dp)) {
                Button(
                    onClick = {
                        val resourceUrl = hero.urls.first().url.replace("http", "https")
                        val viewInBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(resourceUrl))
                        startActivity(viewInBrowserIntent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    content = {
                        Text(text = stringResource(id = R.string.read_more))
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = RedPrimary,
                        contentColor = White
                    )
                )
            }
        }
    }
}