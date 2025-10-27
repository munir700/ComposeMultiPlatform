package app.shared.mobile

import androidx.compose.ui.window.ComposeUIViewController
import app.shared.mobile.presentation.app.AppContent


fun MainViewController() = ComposeUIViewController { AppContent() }