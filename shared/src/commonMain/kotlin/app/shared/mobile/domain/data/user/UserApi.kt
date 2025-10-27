package app.shared.mobile.domain.data.user

import app.shared.mobile.domain.models.common.DataResponse
import app.shared.mobile.domain.models.user.Employee
import app.shared.mobile.domain.models.user.Employer
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class UserApi(
    private val httpClient: HttpClient
) {
    suspend fun getEmployeeBasicProfile(): DataResponse<Employee> {
        return httpClient
            .get("api/employee/basicprofile")
            .body()
    }

    suspend fun getEmployerBasicProfile(): DataResponse<Employer> {
        return httpClient
            .get("api/employer/basicprofile")
            .body()
    }


    suspend fun getEmployerProfile(): DataResponse<Employer> {
        return httpClient
            .get("api/v2/employer/profile")
            .body()
    }

    suspend fun getEmployeeProfile(): DataResponse<Employee> {
        return httpClient//
            .get("api/employee/profile")
            .body()
    }

    suspend fun getEmployeeProfileByCode(employeeCode: String): DataResponse<Employee> {
        return httpClient//
            .get("api/employees/$employeeCode/profile")
            .body()
    }
}