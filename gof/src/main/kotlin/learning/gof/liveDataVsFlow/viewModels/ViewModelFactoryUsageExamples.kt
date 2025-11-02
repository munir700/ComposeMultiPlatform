package learning.gof.liveDataVsFlow.viewModels

import learning.gof.liveDataVsFlow.repository.UserRepository

/**
 * Usage examples demonstrating each factory pattern
 */
object ViewModelFactoryUsageExamples {


    /**
     * Example 2: Using GenericViewModelFactory with lambda
     * This is more flexible and reusable
     */
    fun example2_GenericFactory() {
        val repository = UserRepository()
        val factory = GenericViewModelFactory(repository) { repo: UserRepository ->
            UserViewModel(repo)
        }

        // Create ViewModel
        val viewModel = factory.create(UserViewModel::class.java)

        // Use ViewModel
        viewModel.getUserWithCoroutines("user456")
    }

    /**
     * Example 3: Using ReflectionViewModelFactory (most reusable)
     * This factory uses reflection to automatically instantiate the ViewModel
     */
    fun example3_ReflectionFactory() {
        val repository = UserRepository()
        val factory = ReflectionViewModelFactory(repository, UserRepository::class.java)

        // Create ViewModel
        val viewModel = factory.create(UserViewModel::class.java)

        // Use ViewModel
        viewModel.getUserWithCoroutines("user789")
    }

    /**
     * Example 4: Creating a custom ViewModel with generic factory
     * Shows how to reuse the factory with different ViewModels
     */
    fun example4_CustomViewModel() {
        // Assuming you have another ViewModel like:
        // class ProductViewModel(private val repository: ProductRepository) : ViewModel() { ... }

        // You can use the same generic factory:
        // val productRepository = ProductRepository()
        // val factory = GenericViewModelFactory(productRepository) { repo: ProductRepository ->
        //     ProductViewModel(repo)
        // }
        // val productViewModel = factory.create(ProductViewModel::class.java)
    }
}


