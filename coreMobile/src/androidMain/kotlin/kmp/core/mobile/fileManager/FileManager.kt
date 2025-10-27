package kmp.core.mobile.fileManager

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

class FileManager(private val context: Context) : IFileManager {

    override fun getFileDirPath(filePath: String): String? {
        // Assuming filePath is a relative path, or just a filename
        return try {
            // Get the cache directory
            val cacheFile = File(context.cacheDir, filePath)

            // Create parent directories if they don't exist
            cacheFile.parentFile?.mkdirs()

            // Create the file if it doesn't exist
            if (!cacheFile.exists()) {
                cacheFile.createNewFile()
            }

            cacheFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun getFileBytes(filePath: String): ByteArray? {
        val uri = getFileUri(filePath = filePath) ?: return null

        return try {

            // Open an InputStream using the ContentResolver
            context.contentResolver.openInputStream(uri)?.use {
                it.readBytes()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun writeBytes(filePath: String, bytes: ByteArray): Boolean {
        return try {
            File(filePath).writeBytes(bytes)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    private fun getFileUri(filePath: String): Uri? {
        return try {
            val file = File(filePath)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                Uri.fromFile(file);
            } else {
                FileProvider.getUriForFile(
                    context,
                    context.packageName + ".fileprovider",
                    file
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}