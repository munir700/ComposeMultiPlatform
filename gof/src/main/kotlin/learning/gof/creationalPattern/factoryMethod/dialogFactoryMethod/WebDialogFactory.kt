package learning.gof.creationalPattern.factoryMethod.dialogFactoryMethod

class WebDialogFactory : DialogFactory() {
    override fun createDialog(): Dialog = WebDialog()
}