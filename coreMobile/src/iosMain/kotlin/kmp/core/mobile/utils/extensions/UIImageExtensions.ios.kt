package kmp.core.mobile.utils.extensions

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.refTo
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import platform.CoreGraphics.CGFloat
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImageOrientation
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
fun UIImage.toByteArray(compressionQuality: CGFloat = 1.0): ByteArray? {
    // Convert UIImage to NSData (JPEG format with specified quality)
    val imageData = UIImageJPEGRepresentation(this, compressionQuality)
        ?: return null

    // Convert NSData to Byte Array
    val byteArray = ByteArray(imageData.length.toInt())
    memcpy(byteArray.refTo(0), imageData.bytes, imageData.length)
    return byteArray
}

@OptIn(ExperimentalForeignApi::class)
fun ByteArray.toUIImage(): UIImage? {
    return usePinned { pinnedArray ->
        // Create NSData from the byte array
        val nsData = NSData.dataWithBytes(pinnedArray.addressOf(0), this.size.toULong())

        // Create UIImage from NSData
        return UIImage(nsData)
    }
}

@OptIn(ExperimentalForeignApi::class)
fun UIImage.normalizedImage(): UIImage {
    if (this.imageOrientation == UIImageOrientation.UIImageOrientationUp) return this

    val size = this.size
    var width = 0.0
    var height = 0.0
    size.useContents {
        width = this.width
        height = this.height
    }
    val scale = this.scale

    UIGraphicsBeginImageContextWithOptions(size, false, scale)
    this.drawInRect(CGRectMake(0.0, 0.0, width, height))
    val normalizedImage = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()

    return normalizedImage ?: this
}

@OptIn(ExperimentalForeignApi::class)
fun UIImage.scaleUIImageToSize(maxSize: Float): UIImage? {
    var size = this.size
    var width = 0.0
    var height = 0.0
    size.useContents {
        width = this.width
        height = this.height
    }

    if (maxOf(width, height) <= maxSize) {

        return this
    }

    val aspect: CGFloat = width / height
    val newSize = if (width > height) {
        CGSizeMake(maxSize.toDouble(), maxSize / aspect)
    } else {
        CGSizeMake(maxSize * aspect, maxSize.toDouble())
    }


    UIGraphicsBeginImageContextWithOptions(newSize, false, 1.0)
    drawInRect(CGRectMake(0.0, 0.0, newSize.useContents { this.width }, newSize.useContents { this.height }))
    val scaledImage = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()

    return scaledImage
}

