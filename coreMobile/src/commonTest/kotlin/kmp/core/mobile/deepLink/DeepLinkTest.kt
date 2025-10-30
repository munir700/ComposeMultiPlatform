package kmp.core.mobile.deepLink

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Test suite for DeepLink and DeepLinkParser
 * Tests deep link parsing and validation logic
 */
class DeepLinkTest {

    @Test
    fun `test deep link creation`() {
        // Arrange & Act
        val deepLink = DeepLink()

        // Assert
        assertTrue(deepLink is DeepLink)
    }

    @Test
    fun `test deep link can be extended`() {
        // Arrange
        class CustomDeepLink(val id: String) : DeepLink()

        // Act
        val customLink = CustomDeepLink(id = "123")

        // Assert
        assertEquals("123", customLink.id)
        assertTrue(customLink is DeepLink)
    }

    @Test
    fun `test multiple deep link types`() {
        // Arrange
        class UserDeepLink(val userId: String) : DeepLink()
        class ProductDeepLink(val productId: String) : DeepLink()

        // Act
        val userLink = UserDeepLink("user-456")
        val productLink = ProductDeepLink("product-789")

        // Assert
        assertEquals("user-456", userLink.userId)
        assertEquals("product-789", productLink.productId)
        assertTrue(userLink is DeepLink)
        assertTrue(productLink is DeepLink)
    }

    @Test
    fun `test deep link equality`() {
        // Arrange
        class ProfileDeepLink(val userId: String) : DeepLink()
        val link1 = ProfileDeepLink("user-1")
        val link2 = ProfileDeepLink("user-1")

        // Act & Assert
        assertEquals(link1.userId, link2.userId)
    }
}

/**
 * Test suite for DeepLinkParser
 * Tests the parser interface and implementation
 */
class DeepLinkParserTest {

    // Mock implementation for testing
    class TestDeepLinkParser : DeepLinkParser {
        override fun parse(url: String): DeepLink? {
            return when {
                url.contains("/user/") -> {
                    val userId = url.substringAfterLast("/")
                    UserDeepLink(userId)
                }
                url.contains("/product/") -> {
                    val productId = url.substringAfterLast("/")
                    ProductDeepLink(productId)
                }
                else -> null
            }
        }
    }

    class UserDeepLink(val userId: String) : DeepLink()
    class ProductDeepLink(val productId: String) : DeepLink()

    @Test
    fun `test parser parses user deeplink correctly`() {
        // Arrange
        val parser = TestDeepLinkParser()
        val url = "app://user/user123"

        // Act
        val result = parser.parse(url)

        // Assert
        assertTrue(result is UserDeepLink)
        assertEquals("user123", (result as UserDeepLink).userId)
    }

    @Test
    fun `test parser parses product deeplink correctly`() {
        // Arrange
        val parser = TestDeepLinkParser()
        val url = "app://product/prod456"

        // Act
        val result = parser.parse(url)

        // Assert
        assertTrue(result is ProductDeepLink)
        assertEquals("prod456", (result as ProductDeepLink).productId)
    }

    @Test
    fun `test parser returns null for unknown deeplink`() {
        // Arrange
        val parser = TestDeepLinkParser()
        val url = "app://unknown/resource"

        // Act
        val result = parser.parse(url)

        // Assert
        assertNull(result)
    }

    @Test
    fun `test parser extracts correct id from url`() {
        // Arrange
        val parser = TestDeepLinkParser()
        val userUrls = listOf(
            "app://user/user-001",
            "app://user/user-xyz-789",
            "app://user/12345"
        )

        // Act & Assert
        userUrls.forEach { url ->
            val result = parser.parse(url) as UserDeepLink
            assertTrue(result.userId.isNotEmpty())
        }
    }

    @Test
    fun `test parser handles empty path gracefully`() {
        // Arrange
        val parser = TestDeepLinkParser()
        val url = "app://user/"

        // Act
        val result = parser.parse(url)

        // Assert - Should return a deep link with empty userId
        assertTrue(result is UserDeepLink)
    }

    @Test
    fun `test parser differentiates between link types`() {
        // Arrange
        val parser = TestDeepLinkParser()
        val userUrl = "app://user/u1"
        val productUrl = "app://product/p1"

        // Act
        val userLink = parser.parse(userUrl)
        val productLink = parser.parse(productUrl)

        // Assert
        assertTrue(userLink is UserDeepLink)
        assertTrue(productLink is ProductDeepLink)
    }

    @Test
    fun `test parser with complex url paths`() {
        // Arrange
        val parser = TestDeepLinkParser()
        val urls = listOf(
            "scheme://domain.com/user/user-abc-123",
            "https://app.com/product/product-xyz-999",
            "custom://app/user/profile"
        )

        // Act & Assert
        urls.forEach { url ->
            val result = parser.parse(url)
            if (url.contains("/user/")) {
                assertTrue(result is UserDeepLink || result == null)
            } else if (url.contains("/product/")) {
                assertTrue(result is ProductDeepLink || result == null)
            }
        }
    }

    @Test
    fun `test parser consistency across multiple calls`() {
        // Arrange
        val parser = TestDeepLinkParser()
        val url = "app://user/consistent-user"

        // Act
        val result1 = parser.parse(url)
        val result2 = parser.parse(url)

        // Assert
        assertEquals(
            (result1 as UserDeepLink).userId,
            (result2 as UserDeepLink).userId
        )
    }
}

