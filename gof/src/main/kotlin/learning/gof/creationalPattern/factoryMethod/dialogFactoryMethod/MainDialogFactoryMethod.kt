package learning.gof.creationalPattern.factoryMethod.dialogFactoryMethod

/*
🧠 What’s Happening

The client only knows about DialogFactory, not the concrete product classes.

Each subclass (WebDialogFactory, WindowsDialogFactory) decides which dialog type to create.

The client can switch between platforms just by changing the factory type — no other code changes.

✅ Benefits
Advantage	Description
Loose coupling	The client depends on abstractions, not concrete classes.
Open/Closed Principle	You can add new product types without changing existing code.
Reusability	Common logic (like showDialog()) is reused in the base class.
Flexibility	Creation logic is easily customizable in subclasses.
 */
// Step 5: Client code
fun main() {
    val factory: DialogFactory = WebDialogFactory()  // Can switch to WindowsDialogFactory()
    factory.showDialog()
}