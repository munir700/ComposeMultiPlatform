package app.shared.mobile.data.remote.models

// Mock API response
data class PaginatedResponse<T>(
    val data: List<T>,
    val page: Int,
    val hasMore: Boolean
)