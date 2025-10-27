package learning.gof.creationalPattern.factoryMethod.circleFactoryMethod

data class Rectangle(val width: Int, val height: Int) : Shape {
    override fun draw() = println("Drawing Rectangle ${width}x$height")
    override fun clone() = this.copy()
}