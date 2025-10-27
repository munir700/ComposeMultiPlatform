package learning.gof.delegates

import kotlin.properties.Delegates

// Property delegates
class User {
    var name: String by Delegates.observable("") { prop, old, new ->
        // print the property name instead of the property reference for clearer output
        println("${prop.name}, $old -> $new")
    }
}

// Class delegation
interface Printer {
    fun print()
}

class ConsolePrinter : Printer {
    override fun print() = println("Printing...")
}

// keep the printer as a private property for clarity; delegation still works
class Document(private val printer: Printer) : Printer by printer
// Document delegates Printer implementation to printer instance