package alireza.nezami.truecallertestproject

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Created by Alireza Nezami on 4/14/2022.
 */

@Composable
fun MainViews(
    viewModel: MainViewModel
) {

    val first10thCharacter = viewModel.first10thCharacter.collectAsState("")
    val every10thCharacter = viewModel.every10thCharacter.collectAsState("")
    val uniqueWordsCount = viewModel.uniqueWordsCount.collectAsState("")
    val buttonState = viewModel.buttonState.collectAsState(true)


    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {

            Button(
                enabled = buttonState.value,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                onClick = {
                    viewModel.start()
                }) {
                Text(text = stringResource(R.string.start))
            }

            Text(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = first10thCharacter.value.toString()
            )
        }

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {

            val horState: ScrollState = rememberScrollState()
            val verState: ScrollState = rememberScrollState()
            Text(
                modifier = Modifier
                    .fillMaxHeight()
                    .horizontalScroll(horState, true)
                    .verticalScroll(verState, true)
                    .weight(1f),
                text = every10thCharacter.value
            )

            val horState2: ScrollState = rememberScrollState()
            val verState2: ScrollState = rememberScrollState()
            Text(
                modifier = Modifier
                    .fillMaxHeight()
                    .horizontalScroll(horState2, true)
                    .verticalScroll(verState2, true)
                    .weight(1f),
                text = uniqueWordsCount.value
            )
        }
    }
}
