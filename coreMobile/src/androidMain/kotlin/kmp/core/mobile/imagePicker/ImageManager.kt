package kmp.core.mobile.imagePicker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.ktor.util.decodeBase64Bytes
import io.ktor.util.encodeBase64
import java.io.ByteArrayOutputStream
import kotlin.io.encoding.ExperimentalEncodingApi
import androidx.core.graphics.scale

class ImageManager : IImageManager {

    override fun toBase64(imageBytes: ByteArray): String {
        return imageBytes.encodeBase64()
    }

    override fun resizeImage(imageBytes: ByteArray, requiredSize: Int): ByteArray? {
        return try {

            // First, decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size, options)

            // Calculate scale factor
            var targetWidth = options.outWidth
            var targetHeight = options.outHeight

            var scale = 1
            while (targetWidth / 2 >= requiredSize && targetHeight / 2 >= requiredSize) {
                targetWidth /= 2
                targetHeight /= 2
                scale *= 2
            }

            // Decode with inSampleSize
            val scaleOptions = BitmapFactory.Options().apply {
                inSampleSize = scale
            }
            val resizedBitmap =
                BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size, scaleOptions)

            // Ensure the bitmap is valid
            resizedBitmap?.let {
                // Resize the bitmap to the exact desired dimensions
                val finalBitmap = it.scale(targetWidth, targetHeight)

                // Convert the resized bitmap back to a ByteArray
                val outputStream = ByteArrayOutputStream()
                finalBitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    100,
                    outputStream
                ) // Compress to JPEG format

                // Recycle the bitmaps to free up memory
                if (finalBitmap != it) it.recycle()
                finalBitmap.recycle()

                outputStream.toByteArray()
            }
        } catch (exception: Exception) {
            handleException(exception)
            // Return null if resizing fails
            null
        }
    }

    override fun decodeBase64AsByteArray(base64: String?): ByteArray? {
        try {
            val validBase64 = base64?.substringAfter(",")
            return validBase64?.decodeBase64Bytes() ?: return null
        } catch (e: Exception) {
            handleException(exception = e)
            return null
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    override fun decodeBase64AsBitmap(base64: String?): ImageBitmap? {
        if (base64.isNullOrEmpty()) return null

        // Clean the base64 string
        var validBase64 = if (base64.contains(",")) {
            base64.split(",").getOrNull(1)
        } else {
            base64
        }
        validBase64 = validBase64?.replace("\"", "")

        validBase64 ?: return null

        return try {
            val decodedString = validBase64.decodeBase64Bytes()
            val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            bitmap?.asImageBitmap()
        } catch (e: Exception) {
            null
        }
    }

    // Function to convert an ImageBitmap to a Base64-encoded String
    override fun toBase64(bitmap: ImageBitmap): String {
        return try {
            val androidBitmap = bitmap.asAndroidBitmap()
            ByteArrayOutputStream().use { output ->
                androidBitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
                val byteArray = output.toByteArray()
                byteArray.encodeBase64()
            }
        } catch (e: Exception) {
            ""
        }
    }

    private fun handleException(exception: Exception) {
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.recordException(exception)
    }
}