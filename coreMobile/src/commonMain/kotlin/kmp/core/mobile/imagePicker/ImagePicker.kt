package kmp.core.mobile.imagePicker

import androidx.compose.runtime.Composable

expect class ImagePicker {
    val enableCropping: Boolean
    val aspectRatioX: Int?
    val aspectRatioY: Int?
    val maxImageSize: Float

    @Composable
    fun registerPicker(onImagePicked: (ByteArray, String?) -> Unit)

    fun pickFromGallery()

    fun captureUsingCamera()

}

@Composable
expect fun rememberImagePicker(
    enableCropping: Boolean,
    aspectRatioX: Int? = null,
    aspectRatioY: Int? = null
): ImagePicker