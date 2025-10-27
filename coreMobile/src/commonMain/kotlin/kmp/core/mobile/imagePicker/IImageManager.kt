package kmp.core.mobile.imagePicker

import androidx.compose.ui.graphics.ImageBitmap


interface IImageManager {
    fun toBase64(imageBytes: ByteArray): String
    fun resizeImage(imageBytes: ByteArray, requiredSize: Int): ByteArray?
    fun decodeBase64AsByteArray(base64: String?): ByteArray?
    fun decodeBase64AsBitmap(base64: String?): ImageBitmap?
    fun toBase64(bitmap: ImageBitmap): String
}