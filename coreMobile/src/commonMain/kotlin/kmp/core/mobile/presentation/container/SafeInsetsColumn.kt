package kmp.core.mobile.presentation.container


import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kmp.core.mobile.utils.extensions.isGesturesNavBarEnabled
import kmp.core.mobile.utils.extensions.verticalScrollIf

@Composable
fun SafeInsetsColumn(
    modifier: Modifier = Modifier,
    enableSafeInsets: Boolean = true,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    isScrollable: Boolean = false,
    scrollState: ScrollState = rememberScrollState(),
    content: @Composable ColumnScope.() -> Unit,
) {
    // Prepare status bars modifier
    val statusBarsModifier = if (enableSafeInsets) {
        Modifier.windowInsetsPadding(WindowInsets.statusBars)
    } else {
        Modifier
    }

    // Prepare nav bars modifier
    val navBarsModifier = if (enableSafeInsets && isGesturesNavBarEnabled().not()) {
        Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    } else {
        Modifier
    }

    // Prepare safe drawing modifier
    val safeDrawingModifier = if (enableSafeInsets) {
        Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
    } else {
        Modifier
    }

    // Render outer box that will respect status and nav bars
    Box(

        modifier
            .then(statusBarsModifier)
            .then(navBarsModifier)

    ) {

        // Render inner column that will respect ime padding
        Column(
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            modifier = Modifier
                .verticalScrollIf(
                    scrollState = scrollState,
                    condition = { isScrollable }
                )
                .then(safeDrawingModifier)
                .windowInsetsPadding(WindowInsets.ime)
        ) {
            // Then render content
            content(this)
        }
    }
}
