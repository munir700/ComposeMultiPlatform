package kmp.core.mobile.imagePicker.models

enum class DocType(val type: String) {
    PDF("pdf"),
    IMAGE("image");

    companion object {
        fun fromType(type: String): DocType? {
            return entries.find { it.type == type }
        }
    }
}