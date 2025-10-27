package kmp.core.mobile.imagePicker

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.util.decodeBase64Bytes
import io.ktor.util.encodeBase64
import kmp.core.mobile.utils.extensions.scaleUIImageToSize
import kmp.core.mobile.utils.extensions.toByteArray
import kmp.core.mobile.utils.extensions.toUIImage
import org.jetbrains.skia.EncodedImageFormat
import org.jetbrains.skia.Image
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIImage

class ImageManager : IImageManager {
    override fun toBase64(imageBytes: ByteArray): String {
        return imageBytes.encodeBase64()
    }

    override fun resizeImage(imageBytes: ByteArray, requiredSize: Int): ByteArray? {
        val originUiImage = imageBytes.toUIImage()
        val resizedImage = originUiImage?.scaleUIImageToSize(requiredSize.toFloat())
        val resizedByteArrayImage = resizedImage?.toByteArray()
        return resizedByteArrayImage
    }

    override fun decodeBase64AsByteArray(base64: String?): ByteArray? {
        try {
            val validBase64 = base64?.substringAfter(",") ?: return null
            val decodedData = NSData.create(base64EncodedString = validBase64, options = 0u) ?: return null
            val originUiImage = UIImage(data = decodedData)
            return originUiImage.toByteArray()
        } catch (e: Exception) {
            return null
        }
    }

    override fun toBase64(bitmap: ImageBitmap): String {

        return try {
            val skiaBitmap = bitmap.asSkiaBitmap()
            val encodedBytes = Image.makeFromBitmap(skiaBitmap).encodeToData(EncodedImageFormat.PNG, 100)?.bytes
            // Convert to base64 string
            return  encodedBytes?.encodeBase64() ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    override fun decodeBase64AsBitmap(base64: String?): ImageBitmap? {
        val base64 = base64?.substringAfter(",") ?: return null
        var validBase64 = if (base64.contains(",")) {
            base64.split(",").getOrNull(1)
        } else {
            base64
        }
        validBase64 = validBase64?.replace("\"", "")

        validBase64 ?: return null

        val bytes = validBase64.decodeBase64Bytes()
       val bitmap =  Image.makeFromEncoded(bytes).toComposeImageBitmap()
        return bitmap
    }
}