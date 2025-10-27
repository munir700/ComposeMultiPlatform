package learning.gof.creationalPattern.builder

fun main() {
// Usage
    val luxuryHouse = HouseBuilder()
        .setWindows(8)
        .setDoors(4)
        .setGarage(true)
        .setPorch("Modern")
        .build()

    println(luxuryHouse.doors)

}