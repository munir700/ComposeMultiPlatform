package learning.gof.creationalPattern.factoryMethod.circleFactoryMethod

// Factory Method — creates Shapes using Prototypes
abstract class ShapeFactory {
    abstract fun createShape(): Shape
}