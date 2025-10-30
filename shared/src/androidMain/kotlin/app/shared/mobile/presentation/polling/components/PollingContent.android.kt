package app.shared.mobile.presentation.polling.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.shared.mobile.presentation.polling.PollingContract.Event
import app.shared.mobile.presentation.polling.PollingContract.State

@Preview
@Composable
internal fun PollingContentPreview() {
    val state = State(
        isInitialized = true,
        items = listOf(1, 2, 3, 4, 5)
    )
    val onEvent: (Event) -> Unit = {}

    PollingContent(
        state = state,
        onEvent = onEvent
    )
}