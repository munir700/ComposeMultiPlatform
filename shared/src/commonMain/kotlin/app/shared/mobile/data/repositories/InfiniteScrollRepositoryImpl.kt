package app.shared.mobile.data.repositories

import app.shared.mobile.data.remote.models.ItemModel
import app.shared.mobile.data.remote.models.PaginatedResponse
import app.shared.mobile.domain.repositories.InfiniteScrollRepository

class InfiniteScrollRepositoryImpl(
    // Inject your API service here
) : InfiniteScrollRepository {

    override suspend fun getItems(
        page: Int,
        pageSize: Int
    ): Result<PaginatedResponse<ItemModel>> {
        return try {
            // Simulate network delay
            kotlinx.coroutines.delay(1500)

            // Simulate error on page 5
            if (page == 5) {
                return Result.failure(Exception("Network error occurred"))
            }

            // Generate mock data
            val items = (0 until pageSize).map { index ->
                val id = page * pageSize + index
                ItemModel(
                    id = id,
                    title = "Item #$id",
                    description = "This is the description for item $id"
                )
            }

            // Simulate end of data after 10 pages
            val hasMore = page < 10

            Result.success(
                PaginatedResponse(
                    data = items,
                    page = page,
                    hasMore = hasMore
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
