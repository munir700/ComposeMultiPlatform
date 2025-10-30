package app.shared.mobile.data.remote.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Test suite for ItemModel
 * Tests data model creation and properties
 */
class ItemModelTest {

    @Test
    fun `test item model creation with all fields`() {
        // Arrange & Act
        val item = ItemModel(
            id = 1,
            title = "Test Item",
            description = "Test Description"
        )

        // Assert
        assertEquals(1, item.id)
        assertEquals("Test Item", item.title)
        assertEquals("Test Description", item.description)
    }

    @Test
    fun `test item model with different ids`() {
        // Arrange & Act
        val items = listOf(
            ItemModel(1, "Item 1", "Desc 1"),
            ItemModel(2, "Item 2", "Desc 2"),
            ItemModel(3, "Item 3", "Desc 3")
        )

        // Assert
        assertEquals(3, items.size)
        assertTrue(items[0].id == 1)
        assertTrue(items[1].id == 2)
        assertTrue(items[2].id == 3)
    }

    @Test
    fun `test item model equality`() {
        // Arrange
        val item1 = ItemModel(1, "Title", "Description")
        val item2 = ItemModel(1, "Title", "Description")

        // Act & Assert
        assertEquals(item1, item2)
    }

    @Test
    fun `test item model inequality with different id`() {
        // Arrange
        val item1 = ItemModel(1, "Title", "Description")
        val item2 = ItemModel(2, "Title", "Description")

        // Act & Assert
        assertFalse(item1 == item2)
    }

    @Test
    fun `test item model copy function`() {
        // Arrange
        val original = ItemModel(1, "Original", "Original Desc")

        // Act
        val modified = original.copy(title = "Modified")

        // Assert
        assertEquals("Original", original.title)
        assertEquals("Modified", modified.title)
        assertEquals(1, modified.id)
    }

    @Test
    fun `test item model with empty strings`() {
        // Arrange & Act
        val item = ItemModel(1, "", "")

        // Assert
        assertEquals("", item.title)
        assertEquals("", item.description)
    }

    @Test
    fun `test item model with special characters`() {
        // Arrange & Act
        val item = ItemModel(
            1,
            "Title @#$%",
            "Description with\nnewlines & special chars"
        )

        // Assert
        assertTrue(item.title.contains("@#$%"))
        assertTrue(item.description.contains("newlines"))
    }

    @Test
    fun `test item model hashcode consistency`() {
        // Arrange
        val item1 = ItemModel(1, "Title", "Description")
        val item2 = ItemModel(1, "Title", "Description")

        // Act & Assert
        assertEquals(item1.hashCode(), item2.hashCode())
    }

    @Test
    fun `test item model string representation`() {
        // Arrange
        val item = ItemModel(1, "Test", "Desc")

        // Act
        val itemString = item.toString()

        // Assert
        assertTrue(itemString.contains("1") || itemString.contains("ItemModel"))
    }

    @Test
    fun `test item model with large numbers`() {
        // Arrange & Act
        val item = ItemModel(Int.MAX_VALUE, "Title", "Description")

        // Assert
        assertEquals(Int.MAX_VALUE, item.id)
    }

    @Test
    fun `test item model with long strings`() {
        // Arrange
        val longString = "A".repeat(1000)

        // Act
        val item = ItemModel(1, longString, longString)

        // Assert
        assertEquals(1000, item.title.length)
        assertEquals(1000, item.description.length)
    }

    @Test
    fun `test multiple items in collection`() {
        // Arrange & Act
        val items = (1..10).map { id ->
            ItemModel(id, "Title $id", "Description $id")
        }

        // Assert
        assertEquals(10, items.size)
        items.forEachIndexed { index, item ->
            assertEquals(index + 1, item.id)
        }
    }
}

/**
 * Test suite for PaginatedResponse
 * Tests pagination model and data handling
 */
class PaginatedResponseTest {

    @Test
    fun `test paginated response creation`() {
        // Arrange
        val items = listOf(
            ItemModel(1, "Item 1", "Desc 1"),
            ItemModel(2, "Item 2", "Desc 2")
        )

        // Act
        val response = PaginatedResponse(
            data = items,
            page = 1,
            hasMore = true
        )

        // Assert
        assertEquals(2, response.data.size)
        assertEquals(1, response.page)
        assertEquals(true, response.hasMore)
    }

    @Test
    fun `test paginated response with empty data`() {
        // Arrange & Act
        val response = PaginatedResponse<ItemModel>(
            data = emptyList(),
            page = 1,
            hasMore = false
        )

        // Assert
        assertEquals(0, response.data.size)
        assertEquals(false, response.hasMore)
    }

    @Test
    fun `test paginated response pagination flow`() {
        // Arrange
        val page1 = PaginatedResponse(
            data = listOf(ItemModel(1, "Item 1", "Desc 1")),
            page = 1,
            hasMore = true
        )
        val page2 = PaginatedResponse(
            data = listOf(ItemModel(2, "Item 2", "Desc 2")),
            page = 2,
            hasMore = true
        )
        val page3 = PaginatedResponse(
            data = listOf(ItemModel(3, "Item 3", "Desc 3")),
            page = 3,
            hasMore = false
        )

        // Act & Assert
        assertEquals(1, page1.page)
        assertEquals(true, page1.hasMore)
        assertEquals(2, page2.page)
        assertEquals(true, page2.hasMore)
        assertEquals(3, page3.page)
        assertEquals(false, page3.hasMore)
    }

    @Test
    fun `test paginated response with multiple pages`() {
        // Arrange
        val responses = mutableListOf<PaginatedResponse<ItemModel>>()

        // Act - Create paginated responses
        for (pageNum in 1..5) {
            val items = listOf(ItemModel(pageNum, "Item $pageNum", "Desc $pageNum"))
            responses.add(PaginatedResponse(
                data = items,
                page = pageNum,
                hasMore = pageNum < 5
            ))
        }

        // Assert
        assertEquals(5, responses.size)
        responses.forEachIndexed { index, response ->
            assertEquals(index + 1, response.page)
            if (index < 4) {
                assertEquals(true, response.hasMore)
            } else {
                assertEquals(false, response.hasMore)
            }
        }
    }

    @Test
    fun `test paginated response equality`() {
        // Arrange
        val items = listOf(ItemModel(1, "Title", "Desc"))
        val response1 = PaginatedResponse(items, 1, true)
        val response2 = PaginatedResponse(items, 1, true)

        // Act & Assert
        assertEquals(response1, response2)
    }

    @Test
    fun `test paginated response copy function`() {
        // Arrange
        val originalItems = listOf(ItemModel(1, "Item", "Desc"))
        val original = PaginatedResponse(originalItems, 1, true)

        // Act
        val modified = original.copy(page = 2, hasMore = false)

        // Assert
        assertEquals(1, original.page)
        assertEquals(2, modified.page)
        assertEquals(true, original.hasMore)
        assertEquals(false, modified.hasMore)
    }

    @Test
    fun `test paginated response generic type`() {
        // Arrange
        data class CustomData(val value: String)

        val customItems = listOf(CustomData("value1"), CustomData("value2"))

        // Act
        val response = PaginatedResponse(
            data = customItems,
            page = 1,
            hasMore = false
        )

        // Assert
        assertEquals(2, response.data.size)
        assertEquals("value1", response.data[0].value)
    }

    @Test
    fun `test paginated response last page detection`() {
        // Arrange
        val lastPage = PaginatedResponse(
            data = listOf(ItemModel(1, "Last", "Last Item")),
            page = 10,
            hasMore = false
        )

        // Act & Assert
        assertEquals(false, lastPage.hasMore)
    }

    @Test
    fun `test paginated response intermediate page`() {
        // Arrange
        val middlePage = PaginatedResponse(
            data = listOf(ItemModel(1, "Middle", "Middle Item")),
            page = 5,
            hasMore = true
        )

        // Act & Assert
        assertEquals(5, middlePage.page)
        assertEquals(true, middlePage.hasMore)
    }

    @Test
    fun `test paginated response collect all data`() {
        // Arrange
        val allData = mutableListOf<ItemModel>()
        val responses = listOf(
            PaginatedResponse(
                listOf(ItemModel(1, "Item 1", "Desc 1")),
                1,
                true
            ),
            PaginatedResponse(
                listOf(ItemModel(2, "Item 2", "Desc 2")),
                2,
                true
            ),
            PaginatedResponse(
                listOf(ItemModel(3, "Item 3", "Desc 3")),
                3,
                false
            )
        )

        // Act
        responses.forEach { response ->
            allData.addAll(response.data)
        }

        // Assert
        assertEquals(3, allData.size)
        allData.forEachIndexed { index, item ->
            assertEquals(index + 1, item.id)
        }
    }
}

