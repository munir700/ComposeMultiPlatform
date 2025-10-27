package app.shared.mobile.domain.data


import app.shared.mobile.domain.models.uploadImage.UploadImageResponse
import app.shared.mobile.domain.models.uploadImage.UploadResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType

class UploadImageApi(private val httpClient: HttpClient) {


    suspend fun deletePhoto(
        uploadFileId: Int,
        deletedBy: String
    ): UploadResponse<Unit> {
        return httpClient.get("api/UpdateInfo/DeleteEstablishmentPhoto") {
            parameter("UploadFileID", uploadFileId)
            parameter("Deletedby", deletedBy)
        }.body()
    }

    suspend fun uploadPhoto(params: Map<String, String>): UploadImageResponse {
        return httpClient.post("/api/UpdateInfo/UploadEstablishmentPhoto") {

            // Set content type to application/x-www-form-urlencoded
            contentType(ContentType.Application.FormUrlEncoded)

            // Encode parameters as form data
            setBody(FormDataContent(Parameters.build {
                params.forEach { (key, value) ->
                    append(key, value)
                }
            }))
        }.body()
    }

}