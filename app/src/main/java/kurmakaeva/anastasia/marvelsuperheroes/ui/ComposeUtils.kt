package kurmakaeva.anastasia.marvelsuperheroes.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kurmakaeva.anastasia.marvelsuperheroes.ui.theme.RedPrimary
import kurmakaeva.anastasia.marvelsuperheroes.ui.theme.Secondary
import kurmakaeva.anastasia.marvelsuperheroes.ui.theme.White

@Composable
fun Fragment.AppScaffold(content: @Composable () -> Unit, actionBarTitle: String, showNavigateUp: Boolean = false) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = actionBarTitle,
                        color = White
                    )
                },
                navigationIcon = if (showNavigateUp) {
                    {
                        IconButton(onClick = {
                            findNavController().popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = White
                            )
                        }
                    }
                } else {
                    null
                },
                backgroundColor = RedPrimary,
                contentColor = Secondary
            )
        }, content = { content() }
    )
}