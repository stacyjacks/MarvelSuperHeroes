package kurmakaeva.anastasia.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.LazyPagingItems
import kurmakaeva.anastasia.domain.entities.Hero
import kurmakaeva.anastasia.presentation.R
import kurmakaeva.anastasia.presentation.ui.theme.RedPrimary
import kurmakaeva.anastasia.presentation.ui.theme.Secondary
import kurmakaeva.anastasia.presentation.ui.theme.White

@Composable
fun Fragment.AppScaffold(
    content: @Composable (paddingValues: PaddingValues) -> Unit,
    actionBarTitle: String,
    showNavigateUp: Boolean = false
) {
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
        }, content = { paddingValues ->  content(paddingValues) }
    )
}

@Composable
fun LoadingIndicator() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(8.dp)
                .size(width = 40.dp, height = 40.dp),
            color = RedPrimary
        )
    }
}

@Composable
fun RetryButton(heroes: LazyPagingItems<Hero>) {
    Button(
        onClick = { heroes.retry() },
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.retry_button),
            color = White
        )
    }
}

@Composable
fun EmptyState() {
    Surface(modifier = Modifier.fillMaxSize(), color = White) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState(), enabled = true)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = stringResource(id = R.string.error_content_description)
            )
            Text(
                text = stringResource(id = R.string.empty_state), modifier = Modifier
                    .padding(25.dp)
                    .fillMaxSize(), textAlign = TextAlign.Center
            )
        }
    }
}
