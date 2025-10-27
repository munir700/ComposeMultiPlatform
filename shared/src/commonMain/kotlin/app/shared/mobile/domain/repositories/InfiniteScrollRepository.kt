package app.shared.mobile.domain.repositories

import app.shared.mobile.data.remote.models.ItemModel
import app.shared.mobile.data.remote.models.PaginatedResponse

interface InfiniteScrollRepository {
    suspend fun getItems(page: Int, pageSize: Int): Result<PaginatedResponse<ItemModel>>
}