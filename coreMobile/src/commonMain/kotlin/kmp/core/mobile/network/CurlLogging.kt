package kmp.core.mobile.network

import kmp.core.mobile.utils.extensions.formatJson
import kmp.core.mobile.utils.extensions.isJson
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.statement.HttpReceivePipeline
import io.ktor.client.statement.bodyAsText
import io.ktor.content.ByteArrayContent
import io.ktor.content.TextContent
import io.ktor.http.formUrlEncode
import io.ktor.util.AttributeKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class CurlLogging private constructor(private val log: (String) -> Unit) {

    class Config {
        var logger: (String) -> Unit = ::println
    }

    companion object Plugin : HttpClientPlugin<Config, CurlLogging> {
        override val key: AttributeKey<CurlLogging> = AttributeKey("CurlLogging")

        override fun prepare(block: Config.() -> Unit): CurlLogging {
            val config = Config().apply(block)
            return CurlLogging(config.logger)
        }

        override fun install(plugin: CurlLogging, scope: HttpClient) {
            // Log the curl command before sending the request
            scope.plugin(HttpSend).intercept { requestBuilder ->
                withContext(Dispatchers.IO) {
                    val curlCommand = plugin.createCurlCommand(requestBuilder)
                    val message = """
                        |🟢 🌐 =======================================
                        |🌟 REQUEST INITIATED 🚀
                        |👉 CURL Command : 
                        |$curlCommand
                        |🟢 🌐 =======================================
                    """.trimMargin()
                    plugin.log(message)
                }
                execute(requestBuilder)
            }

            // Log the response after it is received
            scope.receivePipeline.intercept(HttpReceivePipeline.After) { response ->
                withContext(Dispatchers.IO) {
                    val curlCommand = plugin.createCurlCommand(response.call.request)
                    val responseBody = response.bodyAsText()
                    val formattedBody = responseBody.formatJson() ?: responseBody // Beautify JSON or keep as-is
                    val message = """
                        |🔵 🌐 =======================================
                        |✅ RESPONSE RECEIVED 📬
                        |👉 Related CURL Command: 
                        |$curlCommand
                        |----------------------------------------
                        |🔖 Status : ${response.status} 🎉
                        |📦 Body   : 
                        |$formattedBody
                        |🔵 🌐 =======================================
                    """.trimMargin()
                    plugin.log(message)
                }
                proceedWith(response)
            }
        }
    }

    private fun createCurlCommand(requestBuilder: HttpRequestBuilder): String {
        return createCurlCommand(
            method = requestBuilder.method.value,
            url = requestBuilder.url.buildString(),
            headers = requestBuilder.headers.entries(),
            body = when (val content = requestBuilder.body) {
                is TextContent -> content.text
                is ByteArrayContent -> content.bytes().decodeToString()
                is FormDataContent -> content.formData.formUrlEncode()
                else -> null
            }
        )
    }

    private fun createCurlCommand(request: HttpRequest): String {
        return createCurlCommand(
            method = request.method.value,
            url = request.url.toString(),
            headers = request.headers.entries(),
            body = when (val content = request.content) {
                is TextContent -> content.text
                is ByteArrayContent -> content.bytes().decodeToString()
                is FormDataContent -> content.formData.formUrlEncode()
                else -> null
            }
        )
    }

    private fun createCurlCommand(
        method: String,
        url: String,
        headers: Set<Map.Entry<String, List<String>>>,
        body: String?
    ): String {
        val headerString = headers.joinToString(" ") { (key, values) ->
            values.joinToString(" ") { value ->
                val formattedValue = if (value.isJson()) {
                    value.replace("\"", "\\\"") // Escape quotes in JSON
                } else {
                    value.replace("\"", "\\\"") // Escape quotes for other values
                }
                "-H \"$key: $formattedValue\""
            }
        }
        val bodyString = body?.let { "--data '${it.replace("'", "\\'")}'" } ?: ""
        return "curl -X $method $headerString $bodyString '$url'"
    }
}