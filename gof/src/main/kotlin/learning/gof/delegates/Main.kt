package learning.gof.delegates

fun main() {
    val user = User()
    user.name = "Alice"
    user.name = "Bob"

    val doc = Document(ConsolePrinter())
    doc.print()
}

