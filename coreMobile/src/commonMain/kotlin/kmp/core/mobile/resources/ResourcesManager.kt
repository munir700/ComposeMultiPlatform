package kmp.core.mobile.resources


import app.core.mobile.resources.Res
import app.core.mobile.resources.allStringResources
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource

@OptIn(ExperimentalResourceApi::class)
object ResourcesManager : IResourcesManager {

    override fun getString(res: StringResource, vararg args: Any): String {
        return runBlocking {
            org.jetbrains.compose.resources.getString(res, *args)
        }
    }

    override fun getString(resName: String): String? {
        return runBlocking {
            Res.allStringResources[resName]?.let {
                getString(it)
            }
        }
    }

    override fun getPluralString(
        res: PluralStringResource,
        quantity: Int,
        vararg args: Any
    ): String {
        return runBlocking {
            org.jetbrains.compose.resources.getPluralString(res, quantity, *args)
        }
    }

    override suspend fun getBytes(res: String) = try {
        Res.readBytes(res)
    } catch (_: Throwable) {
        null
    }
}