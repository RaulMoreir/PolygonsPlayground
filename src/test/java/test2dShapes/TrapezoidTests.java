package test2dShapes;

import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.Trapezoid;
import org.testng.annotations.Test;

public class TrapezoidTests extends AbstractPolygonTypeTest {

    @Test
    public void testAreaPositiveValues() throws InvalidShapeFormatException {
        validatePolygonArea(new Trapezoid(10, 20, 10, 10, 15), 150);
    }

    @Test
    public void testPerimeterPositiveValues() throws InvalidShapeFormatException {
        validatePolygonPerimeter(new Trapezoid(10, 20, 10, 10, 15), 55);
    }

    @Test (expectedExceptions = InvalidShapeFormatException.class, expectedExceptionsMessageRegExp = "Minor base should be smaller than major base. ")
    public void testInvalidBaseValues() throws InvalidShapeFormatException {
        new Trapezoid(10, 10, 10, 10, 12);
    }

    @Test (expectedExceptions = InvalidShapeFormatException.class, expectedExceptionsMessageRegExp = "Side A should be greater or equals to the height. ")
    public void testSideASmallerThanHeight() throws InvalidShapeFormatException {
        new Trapezoid(10, 20, 10, 9, 12);
    }

    @Test (expectedExceptions = InvalidShapeFormatException.class, expectedExceptionsMessageRegExp = "Side B should be greater or equals to the height. ")
    public void testSideBSmallerThanHeight() throws InvalidShapeFormatException {
        new Trapezoid(10, 20, 10, 12, 9);
    }

    @Test (expectedExceptions = InvalidShapeFormatException.class, expectedExceptionsMessageRegExp = "Don't you want a square or a rectangle instead. ")
    public void testInvalidSquareOrRectangleValues() throws InvalidShapeFormatException {
        new Trapezoid(10, 20, 10, 10, 10);
    }

    @Test (expectedExceptions = InvalidShapeFormatException.class, expectedExceptionsMessageRegExp = "Minor base should be smaller than major base. Don't you want a square or a rectangle instead. ")
    public void testAllEqualValues() throws InvalidShapeFormatException {
        new Trapezoid(10, 10, 10, 10, 10);
    }

    @Test
    public void testIsoscelesTrapezoid() throws InvalidShapeFormatException {
        validatePolygonType(new Trapezoid(9,10,15,20,20), "the Trapezoid is an isosceles");
    }

    @Test
    public void testRightTrapezoid() throws InvalidShapeFormatException {
        validatePolygonType(new Trapezoid(9,10,15,15,20), "the Trapezoid is a right");
    }
    @Test
    public void testScaleneTrapezoid() throws InvalidShapeFormatException {
        validatePolygonType(new Trapezoid(9,10,15,21,20), "the Trapezoid is a scalene");
    }


}
