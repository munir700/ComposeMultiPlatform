package kmp.core.mobile.fileManager

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.refTo
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.create
import platform.Foundation.data
import platform.Foundation.dataWithContentsOfFile
import platform.Foundation.getBytes
import platform.Foundation.stringByStandardizingPath
import platform.Foundation.writeToFile

class FileManager : IFileManager {
    @OptIn(BetaInteropApi::class)
    override fun getFileDirPath(filePath: String): String? {
        return try {
            // Get base documents directory
            val documentsPath = NSSearchPathForDirectoriesInDomains(
                NSDocumentDirectory,
                NSUserDomainMask,
                true
            ).first() as String

            // Create full path
            val fullPath = "$documentsPath/$filePath"
            val standardizedPath = NSString.create(string = fullPath).stringByStandardizingPath

            val fileManager = NSFileManager.defaultManager

            // Create parent directories if they don't exist
            val parentDir = NSURL.fileURLWithPath(standardizedPath).URLByDeletingLastPathComponent
            if (!fileManager.fileExistsAtPath(parentDir?.path ?: "")) {
                fileManager.createDirectoryAtPath(
                    parentDir?.path ?: return null,
                    attributes = mapOf<Any?, Any>()
                )
            }

            // Create the file if it doesn't exist
            if (!fileManager.fileExistsAtPath(standardizedPath)) {
                fileManager.createFileAtPath(
                    standardizedPath,
                    contents = NSData.data(),
                    attributes = mapOf<Any?, Any>()
                )
            }

            standardizedPath
        } catch (e: Exception) {
            println("Failed to create file path: ${e.message}")
            null
        }
    }

    override fun getFileBytes(filePath: String): ByteArray? {
        return try {
            val nsData = NSData.dataWithContentsOfFile(filePath)
                ?: return null
            nsData.toByteArray()
        } catch (e: Exception) {
            println("Failed to read file bytes: ${e.message}")
            null
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun writeBytes(filePath: String, bytes: ByteArray): Boolean {
        return try {
            val nsData = bytes.toNSData()
            nsData.writeToFile(filePath, true)
        } catch (e: Exception) {
            println("Failed to write bytes: ${e.message}")
            false
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ByteArray.toNSData() = usePinned { pinned ->
        NSData.create(
            bytes = pinned.addressOf(0),
            length = this.size.toULong()
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun NSData.toByteArray(): ByteArray {
        val byteArray = ByteArray(this.length.toInt())
        memScoped {
            val buffer = byteArray.refTo(0).getPointer(memScope)
            this@toByteArray.getBytes(buffer, this@toByteArray.length)
        }
        return byteArray
    }
}