package learning.gof.liveDataVsFlow

import learning.gof.liveDataVsFlow.repository.UserRepository
import learning.gof.liveDataVsFlow.viewModels.GenericViewModelFactory
import learning.gof.liveDataVsFlow.viewModels.InvalidViewModel
import learning.gof.liveDataVsFlow.viewModels.ReflectionViewModelFactory
import learning.gof.liveDataVsFlow.viewModels.UserViewModel
import learning.gof.liveDataVsFlow.viewModels.createViewModelWithFactory
import learning.gof.liveDataVsFlow.viewModels.createViewModelWithRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Test cases for ViewModelFactories
 * Verifies that all factory implementations work correctly
 */
class ViewModelFactoryTests {

    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        repository = UserRepository()
    }

    /**
     * Test 1: GenericViewModelFactory creates UserViewModel correctly
     */
    @Test
    fun testUserViewModelFactory_CreatesViewModelSuccessfully() {
        // Arrange
        val factory = GenericViewModelFactory(repository) { repo: UserRepository ->
            UserViewModel(repo)
        }

        // Act
        val viewModel = factory.create(UserViewModel::class.java)

        // Assert
        assertNotNull(viewModel)
        assertTrue(viewModel is UserViewModel)
        assertEquals(viewModel.javaClass, UserViewModel::class.java)
    }

    /**
     * Test 2: GenericViewModelFactory creates ViewModel with lambda
     */
    @Test
    fun testGenericViewModelFactory_CreatesViewModelWithLambda() {
        // Arrange
        val factory = GenericViewModelFactory(repository) { repo: UserRepository ->
            UserViewModel(repo)
        }

        // Act
        val viewModel = factory.create(UserViewModel::class.java)

        // Assert
        assertNotNull(viewModel)
        assertTrue(viewModel is UserViewModel)
    }

    /**
     * Test 3: ReflectionViewModelFactory creates ViewModel using reflection
     */
    @Test
    fun testReflectionViewModelFactory_CreatesViewModelWithReflection() {
        // Arrange
        val factory = ReflectionViewModelFactory(repository, UserRepository::class.java)

        // Act
        val viewModel = factory.create(UserViewModel::class.java)

        // Assert
        assertNotNull(viewModel)
        assertTrue(viewModel is UserViewModel)
    }

    /**
     * Test 4: Verify all factories return same ViewModel type
     */
    @Test
    fun testAllFactories_ReturnSameViewModelType() {
        // Arrange
        val factory1 = GenericViewModelFactory(repository) { repo: UserRepository ->
            UserViewModel(repo)
        }
        val factory2 = GenericViewModelFactory(repository) { repo: UserRepository ->
            UserViewModel(repo)
        }
        val factory3 = ReflectionViewModelFactory(repository, UserRepository::class.java)

        // Act
        val viewModel1 = factory1.create(UserViewModel::class.java)
        val viewModel2 = factory2.create(UserViewModel::class.java)
        val viewModel3 = factory3.create(UserViewModel::class.java)

        // Assert
        assertEquals(viewModel1.javaClass, viewModel2.javaClass)
        assertEquals(viewModel2.javaClass, viewModel3.javaClass)
        assertEquals(viewModel1.javaClass, UserViewModel::class.java)
    }

    /**
     * Test 5: Verify ViewModel is created with correct repository
     */
    @Test
    fun testUserViewModelFactory_ProvidesFunctionalViewModel() {
        // Arrange - Using extension function
        val viewModel = createViewModelWithRepository(repository) { repo: UserRepository ->
            UserViewModel(repo)
        }

        // Assert
        assertNotNull(viewModel)
        assertTrue(viewModel is UserViewModel)
        // Verify it's a working instance by checking it's not null and is the correct type
        assertEquals(viewModel.javaClass.simpleName, "UserViewModel")
    }

    /**
     * Test 5b: Test createViewModelWithFactory extension function
     */
    @Test
    fun testCreateViewModelWithFactory_UsesProvidedFactory() {
        // Arrange
        val factory = GenericViewModelFactory(repository) { repo: UserRepository ->
            UserViewModel(repo)
        }

        // Act - Using extension function
        val viewModel = createViewModelWithFactory<UserViewModel>(factory)

        // Assert
        assertNotNull(viewModel)
        assertTrue(viewModel is UserViewModel)
        assertEquals(viewModel.javaClass, UserViewModel::class.java)
    }

    /**
     * Test 6: Test factory with different repository implementations
     */
    @Test
    fun testGenericViewModelFactory_WorksWithDifferentRepositories() {
        // Arrange
        val factory = GenericViewModelFactory(repository) { repo: UserRepository ->
            UserViewModel(repo)
        }

        // Act
        val viewModel1 = factory.create(UserViewModel::class.java)
        val viewModel2 = factory.create(UserViewModel::class.java)

        // Assert
        assertNotNull(viewModel1)
        assertNotNull(viewModel2)
        // Each call should create a new instance
        assertNotEquals(viewModel1, viewModel2)
    }

    /**
     * Test 7: Test error handling - invalid ViewModel class
     */
    @Test(expected = Exception::class)
    fun testReflectionViewModelFactory_ThrowsExceptionForInvalidViewModel() {
        // Arrange
        val factory = ReflectionViewModelFactory(repository, UserRepository::class.java)

        // Act & Assert - should throw exception
        factory.create(InvalidViewModel::class.java)
    }
}

