package learning.gof.creationalPattern.factoryMethod.dialogFactoryMethod

// Step 2: Concrete Products
class WindowsDialog : Dialog {
    override fun render() = println("Rendering a Windows-style dialog")
}