package learning.gof.creationalPattern.builder

class HouseBuilder {
    private var windows: Int = 0
    private var doors: Int = 0
    private var hasGarage: Boolean = false
    private var porchStyle: String? = null

    fun setWindows(count: Int) = apply { this.windows = count }
    fun setDoors(count: Int) = apply { this.doors = count }
    fun setGarage(hasGarage: Boolean) = apply { this.hasGarage = hasGarage }
    fun setPorch(style: String) = apply { this.porchStyle = style }

    fun build() = House(windows, doors, hasGarage, porchStyle)
}
