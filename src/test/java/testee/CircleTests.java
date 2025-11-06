package testee;

import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.Circle;
import org.testng.annotations.Test;

public class CircleTests extends AbstractPolygonTest {

    @Test
    public void testPositiveNumber () throws InvalidShapeFormatException {
        validatePolygonArea(new Circle(10),314.1593);
    }

    @Test(expectedExceptions = InvalidShapeFormatException.class, expectedExceptionsMessageRegExp = "Please input a valid value to the Circle radius")
    public void testNegativeNumber () throws InvalidShapeFormatException {
        validatePolygonArea(new Circle(-10),314.1593);
    }

    @Test(expectedExceptions = InvalidShapeFormatException.class, expectedExceptionsMessageRegExp = "Please input a valid value to the Circle radius")
    public void testNumberZero () throws InvalidShapeFormatException {
        validatePolygonArea(new Circle(0),0 );
    }
    @Test
    public void testNumberPI () throws InvalidShapeFormatException {
        validatePolygonArea(new Circle(Math.PI),31.0063);

    }
    @Test
    public void testPerimeterPositiveRadius() throws InvalidShapeFormatException {
        validatePolygonPerimeter(new Circle(10), 62.8319);
    }

    @Test
    public void testFloatPointNumber() throws InvalidShapeFormatException {
        validatePolygonArea(new Circle(3.33d), 34.8368);
    }

    @Test
    public void testPerimeter() throws InvalidShapeFormatException {
        validatePolygonPerimeter(new Circle(2d),12.5664);
    }

    @Test
    public void testFloatPerimeter() throws InvalidShapeFormatException {
        validatePolygonPerimeter(new Circle(2.7d),16.9646);
    }
    @Test(expectedExceptions = InvalidShapeFormatException.class, expectedExceptionsMessageRegExp = "Please input a valid value to the Circle radius")
    public void testPerimeterZero() throws InvalidShapeFormatException {
        validatePolygonPerimeter(new Circle(0d),0);
    }
}
