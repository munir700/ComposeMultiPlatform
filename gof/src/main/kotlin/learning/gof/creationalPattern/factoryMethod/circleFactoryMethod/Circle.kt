package learning.gof.creationalPattern.factoryMethod.circleFactoryMethod

import learning.gof.creationalPattern.factoryMethod.circleFactoryMethod.Shape

// Concrete Prototypes
data class Circle(val radius: Int) : Shape {
    override fun draw() = println("Drawing Circle with radius $radius")
    override fun clone() = this.copy()
}