package learning.gof.liveDataVsFlow.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import learning.gof.liveDataVsFlow.repository.BaseRepository

/**
 * Generic ViewModelFactory for creating ViewModels with dependencies
 * This factory can be reused for any ViewModel that takes a single repository parameter
 */
class GenericViewModelFactory<T : ViewModel, R : BaseRepository>(
    private val repository: R,
    private val creator: (R) -> T
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return creator(repository) as T
    }
}

/**
 * Alternative: Generic factory using reflection
 * This approach automatically instantiates the ViewModel constructor
 */
class ReflectionViewModelFactory<R : BaseRepository>(
    private val repository: R,
    private val repositoryClass: Class<out R>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return modelClass.getConstructor(repositoryClass)
            .newInstance(repository) as T
    }
}


/**
 * Best Practice: Extension function for easier ViewModel creation
 */
inline fun <reified VM : ViewModel> createViewModelWithFactory(
    factory: ViewModelProvider.Factory
): VM {
    return factory.create(VM::class.java)
}

/**
 * Extension function for creating ViewModel with lambda
 */
inline fun <reified VM : ViewModel, R : BaseRepository> createViewModelWithRepository(
    repository: R,
    noinline creator: (R) -> VM
): VM {
    val factory = GenericViewModelFactory(repository, creator)
    return factory.create(VM::class.java)
}

