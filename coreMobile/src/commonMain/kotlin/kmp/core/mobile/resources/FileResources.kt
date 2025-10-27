package kmp.core.mobile.resources

import app.core.mobile.resources.Res


internal val Res.file: FileResources
    get() = FileResources

fun String.asFileResource(): IFileResource = FileResource(path = "files/$this")

object FileResources {
    val loading_indicator: IFileResource = "loading_indicator.json".asFileResource()
}