package learning.gof.creationalPattern.factoryMethod.circleFactoryMethod

// Usage
fun main() {
    val circlePrototype = Circle(10)
    val rectanglePrototype = Rectangle(5, 8)

    val circleFactory = CircleFactory(circlePrototype)
    val rectangleFactory = RectangleFactory(rectanglePrototype)

    val shape1 = circleFactory.createShape() // clone of circlePrototype
    val shape2 = rectangleFactory.createShape() // clone of rectanglePrototype

    shape1.draw()
    shape2.draw()
}