package kmp.core.mobile.language


import kmp.core.mobile.itemPicker.models.PickerItem
import kmp.core.mobile.utils.CommonParcelize
import kotlinx.serialization.Serializable

@CommonParcelize
@Serializable
data class Language(
    val isEmpty: Boolean = false,
    val code: String = "",
    val name: String = "",
    val isRtl: Boolean = false
) : PickerItem {
    override val key = code
    override val title = name
}

val Language.Companion.English: Language
    get() = Language(
        code = "en",
        name = "English",
        isRtl = false
    )

val Language.Companion.Arabic: Language
    get() = Language(
        code = "ar",
        name = "العربية",
        isRtl = true
    )

fun Language.isArabic() = code == "ar"