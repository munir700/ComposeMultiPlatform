package app.shared.mobile.presentation.splash.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import app.shared.mobile.presentation.theme.colors
import app.shared.mobile.presentation.theme.spacings
import app.shared.mobile.presentation.theme.typography
import app.shared.mobile.resources.Res
import app.shared.mobile.resources.bg_splash
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun SplashContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(Res.drawable.bg_splash),
                contentScale = ContentScale.FillBounds
            )
    ) {

        Text(
            text = "CMP Mobile learning",
            style = typography.primaryRegular18,
            color = colors.darkElectricBlue.copy(alpha = 0.5f),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = spacings.spacing100)
        )
    }
}

