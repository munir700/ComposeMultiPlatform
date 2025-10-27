package learning.gof.creationalPattern.factoryMethod.dialogFactoryMethod

// Step 4: Concrete Creators
class WindowsDialogFactory : DialogFactory() {
    override fun createDialog(): Dialog = WindowsDialog()
}