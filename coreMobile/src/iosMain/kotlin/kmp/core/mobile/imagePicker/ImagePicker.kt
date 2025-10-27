@file:OptIn(ExperimentalForeignApi::class)

package kmp.core.mobile.imagePicker


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.LocalUIViewController
import kmp.core.mobile.utils.extensions.normalizedImage
import kmp.core.mobile.utils.extensions.scaleUIImageToSize
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerEditedImage
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UIModalPresentationFullScreen
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import platform.posix.memcpy

actual class ImagePicker(
    private val rootController: UIViewController,
    actual val enableCropping: Boolean,
    actual val aspectRatioX: Int?,
    actual val aspectRatioY: Int?,
    actual val maxImageSize: Float = 720f
) {
    private val imagePickerController = UIImagePickerController()
    private var onImagePicked: (imageBytes: ByteArray, fileName: String?) -> Unit = { _, _ -> }

    private val delegate = object : NSObject(), UIImagePickerControllerDelegateProtocol,
        UINavigationControllerDelegateProtocol {
        override fun imagePickerController(
            picker: UIImagePickerController, didFinishPickingMediaWithInfo: Map<Any?, *>
        ) {
            val image =
                didFinishPickingMediaWithInfo.getValue(UIImagePickerControllerEditedImage) as? UIImage
                    ?: didFinishPickingMediaWithInfo.getValue(
                        UIImagePickerControllerOriginalImage
                    ) as? UIImage
            val imageSized = image?.scaleUIImageToSize(maxImageSize)
            val imageNsData =
                imageSized?.let { UIImageJPEGRepresentation(it.normalizedImage(), 1.0) }
                    ?: return
            val bytes = ByteArray(imageNsData.length.toInt())
            memcpy(bytes.refTo(0), imageNsData.bytes, imageNsData.length)
            onImagePicked(bytes, null)
            picker.dismissViewControllerAnimated(true, null)
        }

        override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
            picker.dismissViewControllerAnimated(true, null)
        }
    }

    @Composable
    actual fun registerPicker(onImagePicked: (imageBytes: ByteArray, fileName: String?) -> Unit) {
        this.onImagePicked = onImagePicked
    }

    actual fun pickFromGallery() {
        pickImage(
            UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
        )
    }

    actual fun captureUsingCamera() {
        pickImage(
            UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
        )
    }

    private fun pickImage(source: UIImagePickerControllerSourceType) {
        imagePickerController.sourceType = source
        imagePickerController.allowsImageEditing = enableCropping
        imagePickerController.setModalPresentationStyle(UIModalPresentationFullScreen)
        rootController.presentViewController(imagePickerController, true) {
            imagePickerController.delegate = delegate
        }
    }
}

@Composable
actual fun rememberImagePicker(
    enableCropping: Boolean,
    aspectRatioX: Int?,
    aspectRatioY: Int?
): ImagePicker {
    val rootController = LocalUIViewController.current
    return remember(enableCropping, aspectRatioX, aspectRatioY) {
        ImagePicker(
            rootController = rootController,
            enableCropping = enableCropping,
            aspectRatioX = aspectRatioX,
            aspectRatioY = aspectRatioY
        )
    }
}




