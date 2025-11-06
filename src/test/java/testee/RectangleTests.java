package testee;

import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.Rectangle;
import org.testng.annotations.Test;

public class RectangleTests extends AbstractPolygonTest {

    @Test
    public void testPositiveSides() throws InvalidShapeFormatException {
        validatePolygonArea(new Rectangle(120, 90), 10800);
    }

    @Test
    public void testPositiveSidesInverse() throws InvalidShapeFormatException {
        validatePolygonArea(new Rectangle(90, 120), 10800d);
    }

    @Test(expectedExceptions = InvalidShapeFormatException.class, expectedExceptionsMessageRegExp = "Please input valid values to the Rectangle sides")
    public void testNegativeEqualSides() throws InvalidShapeFormatException {
        validatePolygonArea(new Rectangle(-90, -90), 8100d);
    }

    @Test(expectedExceptions = InvalidShapeFormatException.class,expectedExceptionsMessageRegExp = "Please input valid values to the Rectangle sides")
    public void testNegativeOposSides() throws InvalidShapeFormatException {
        validatePolygonArea(new Rectangle(-90, 90), -8100d);
    }
    @Test
    public void testPerimeter() throws InvalidShapeFormatException {
        validatePolygonPerimeter(new Rectangle(90, 90), 360);
    }
    @Test
    public void testFloatPerimeter() throws InvalidShapeFormatException {
        validatePolygonPerimeter(new Rectangle(90.3d, 90.5d), 361.6d);
    }

    @Test(expectedExceptions = InvalidShapeFormatException.class, expectedExceptionsMessageRegExp = "Please input valid values to the Rectangle sides")
    public void testNegativeFloatPerimeter() throws InvalidShapeFormatException {
        validatePolygonPerimeter(new Rectangle(-90.3d, 90.5d), 0.4d);
    }
}
