package kmp.core.mobile.fileManager

interface IFileManager {
    fun getFileDirPath(filePath: String): String?
    fun getFileBytes(filePath: String): ByteArray?
    fun writeBytes(filePath: String, bytes: ByteArray): Boolean
}