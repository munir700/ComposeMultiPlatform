package kmp.core.mobile.network.errors

import kmp.core.mobile.base.ErrorType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Test suite for NetworkResponseError
 * Tests error handling and body deserialization
 */
class NetworkResponseErrorTest {

    @Test
    fun `test network response error creation with all parameters`() {
        // Arrange
        val bodyText = """{"error": "Unauthorized"}"""
        val statusCode = 401
        val statusDescription = "Unauthorized"

        // Act
        val error = NetworkResponseError(
            bodyText = bodyText,
            statusCode = statusCode,
            statusDescription = statusDescription
        )

        // Assert
        assertEquals(bodyText, error.bodyText)
        assertEquals(statusCode, error.statusCode)
        assertEquals(statusDescription, error.statusDescription)
    }

    @Test
    fun `test network response error with default status description`() {
        // Arrange
        val bodyText = """{"message": "Not Found"}"""
        val statusCode = 404

        // Act
        val error = NetworkResponseError(
            bodyText = bodyText,
            statusCode = statusCode
        )

        // Assert
        assertEquals(bodyText, error.bodyText)
        assertEquals(statusCode, error.statusCode)
        assertEquals("", error.statusDescription)
    }

    @Test
    fun `test network response error is throwable`() {
        // Arrange
        val error = NetworkResponseError(
            bodyText = "Error occurred",
            statusCode = 500
        )

        // Act & Assert
        assertTrue(error is Throwable)
    }

    @Test
    fun `test common http error codes`() {
        // Arrange & Act & Assert
        val badRequest = NetworkResponseError("", 400)
        val unauthorized = NetworkResponseError("", 401)
        val forbidden = NetworkResponseError("", 403)
        val notFound = NetworkResponseError("", 404)
        val serverError = NetworkResponseError("", 500)
        val badGateway = NetworkResponseError("", 502)

        assertEquals(400, badRequest.statusCode)
        assertEquals(401, unauthorized.statusCode)
        assertEquals(403, forbidden.statusCode)
        assertEquals(404, notFound.statusCode)
        assertEquals(500, serverError.statusCode)
        assertEquals(502, badGateway.statusCode)
    }

    @Test
    fun `test network response error with empty body`() {
        // Arrange & Act
        val error = NetworkResponseError("", 500)

        // Assert
        assertEquals("", error.bodyText)
    }

    @Test
    fun `test network response error with complex json body`() {
        // Arrange
        val complexBody = """
            {
                "error": "Validation Failed",
                "details": {
                    "field": "email",
                    "message": "Invalid email format"
                },
                "timestamp": "2024-01-01T00:00:00Z"
            }
        """.trimIndent()

        // Act
        val error = NetworkResponseError(bodyText = complexBody, statusCode = 422)

        // Assert
        assertEquals(complexBody, error.bodyText)
        assertEquals(422, error.statusCode)
        assertTrue(error.bodyText.contains("Validation Failed"))
    }

    @Test
    fun `test network response error body retrieval`() {
        // Arrange
        val bodyText = """{"message": "Server Error"}"""
        val error = NetworkResponseError(
            bodyText = bodyText,
            statusCode = 500,
            statusDescription = "Internal Server Error"
        )

        // Act & Assert
        assertEquals(bodyText, error.bodyText)
        assertTrue(error.bodyText.contains("Server Error"))
    }

    @Test
    fun `test multiple network errors with different codes`() {
        // Arrange
        val errors = listOf(
            NetworkResponseError("{}", 400),
            NetworkResponseError("{}", 401),
            NetworkResponseError("{}", 403),
            NetworkResponseError("{}", 404),
            NetworkResponseError("{}", 500)
        )

        // Act & Assert
        assertEquals(5, errors.size)
        assertTrue(errors.all { it.statusCode in 400..500 })
        assertTrue(errors.map { it.statusCode }.distinct().size == 5)
    }

    @Test
    fun `test error status code categorization`() {
        // Arrange
        val clientError = NetworkResponseError("", 400)
        val serverError = NetworkResponseError("", 500)

        // Act & Assert
        assertTrue(clientError.statusCode in 400..499)
        assertTrue(serverError.statusCode in 500..599)
    }
}

/**
 * Test suite for ErrorType enum
 * Tests error categorization
 */
class ErrorTypeTest {

    @Test
    fun `test error type popup`() {
        // Arrange & Act
        val errorType = ErrorType.Popup

        // Assert
        assertEquals(ErrorType.Popup, errorType)
    }

    @Test
    fun `test error type snackbar`() {
        // Arrange & Act
        val errorType = ErrorType.SnackBar

        // Assert
        assertEquals(ErrorType.SnackBar, errorType)
    }

    @Test
    fun `test error type no error`() {
        // Arrange & Act
        val errorType = ErrorType.NoError

        // Assert
        assertEquals(ErrorType.NoError, errorType)
    }

    @Test
    fun `test all error types exist`() {
        // Arrange & Act
        val allTypes = ErrorType.entries

        // Assert
        assertEquals(3, allTypes.size)
        assertTrue(allTypes.contains(ErrorType.Popup))
        assertTrue(allTypes.contains(ErrorType.SnackBar))
        assertTrue(allTypes.contains(ErrorType.NoError))
    }

    @Test
    fun `test error type comparison`() {
        // Arrange
        val error1 = ErrorType.Popup
        val error2 = ErrorType.Popup
        val error3 = ErrorType.SnackBar

        // Act & Assert
        assertEquals(error1, error2)
        assertTrue(error1 !== error3)
    }

    @Test
    fun `test error type string representation`() {
        // Arrange & Act
        val popup = ErrorType.Popup.toString()
        val snackbar = ErrorType.SnackBar.toString()
        val noError = ErrorType.NoError.toString()

        // Assert
        assertTrue(popup.contains("Popup"))
        assertTrue(snackbar.contains("SnackBar"))
        assertTrue(noError.contains("NoError"))
    }

    @Test
    fun `test error type enum values`() {
        // Arrange
        val popup = "Popup"
        val snackBar = "SnackBar"
        val noError = "NoError"

        // Act & Assert
        assertEquals(popup, ErrorType.Popup.name)
        assertEquals(snackBar, ErrorType.SnackBar.name)
        assertEquals(noError, ErrorType.NoError.name)
    }
}

