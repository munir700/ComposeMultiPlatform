package kmp.core.mobile.globalState.models

data class ChoicesPopupParams(
    val isCancellable: Boolean = true,
    val title: String? = null,
    val choices: List<String> = emptyList(),
    val onChoiceSelected: ((String, Int) -> Unit)? = null,
    val onDismiss: (() -> Unit)? = null
)