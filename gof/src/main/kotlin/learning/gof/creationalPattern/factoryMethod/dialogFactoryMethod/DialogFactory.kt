package learning.gof.creationalPattern.factoryMethod.dialogFactoryMethod

// Step 3: Creator abstract class
abstract class DialogFactory {
    abstract fun createDialog(): Dialog

    // Common business logic
    fun showDialog() {
        val dialog = createDialog()
        dialog.render()
    }
}