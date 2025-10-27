package learning.gof.creationalPattern.factoryMethod.dialogFactoryMethod

class WebDialog : Dialog {
    override fun render() = println("Rendering a Web-style dialog")
}