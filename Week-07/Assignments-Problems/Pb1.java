
abstract class Shape {
    private static int shapeCounter = 1000;
    private final String shapeId;

    public Shape() {
        shapeCounter++;
        shapeId = "SH-" + shapeCounter;
    }

    public abstract double calculateArea();

    public abstract void scale(double factor);

    public abstract void scale(double xFactor, double yFactor);

    public String getShapeId() {
        return shapeId;
    }
}

class CircleShape extends Shape {
    private double radius;

    public CircleShape(double radius) {
        super();
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void scale(double factor) {
        radius *= factor;
    }

    @Override
    public void scale(double xFactor, double yFactor) {
        radius *= Math.sqrt(xFactor * yFactor);
    }
}

class SquareShape extends Shape {
    private double side;

    public SquareShape(double side) {
        super();
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    public void scale(double factor) {
        side *= factor;
    }

    @Override
    public void scale(double xFactor, double yFactor) {
        side *= Math.sqrt(xFactor * yFactor);
    }
}

public class Pb1 {

    static void printArea(Shape s) {
        System.out.println("Area: " + s.calculateArea());
    }

    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);

        System.out.printf("Circle Area: %.2f%n", c.calculateArea());

        SquareShape sq = new SquareShape(4.0);

        System.out.println("Square Area: " + sq.calculateArea());

        sq.scale(2.0);

        System.out.println("Square Area after scaling: "
                + sq.calculateArea());

        printArea(c);

        System.out.println("Circle ID: " + c.getShapeId());
        System.out.println("Square ID: " + sq.getShapeId());
    }
}
