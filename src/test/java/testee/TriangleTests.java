package testee;

import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.Triangle;
import org.testng.annotations.Test;

public class TriangleTests extends AbstractPolygonTest {

   /* private void validateTriangle(int base, int height,double sideA,double sideB,double sideC, double expectedValue) {
        Triangle triangle = new Triangle(base, height, sideA, sideB,sideC);
        Assert.assertEquals(triangle.getArea(), expectedValue);
    }*/

    @Test
    public void testPositiveSides() throws InvalidShapeFormatException {
        validatePolygonArea( new Triangle( 120, 90,10,10,10) ,5400.0000);
    }

    @Test
    public void testPositiveSidesInverse() throws InvalidShapeFormatException {
        validatePolygonArea( new Triangle( 90, 120,10,10,10) ,5400);
    }

    @Test
    public void testPositiveEqualSides() throws InvalidShapeFormatException {
        validatePolygonArea( new Triangle( 90, 90,10,10,10) ,4050);
    }

    @Test (expectedExceptions = InvalidShapeFormatException.class, expectedExceptionsMessageRegExp = "Please input positive values for the triangle base and height; ")
    public void testNegativeEqualSides() throws InvalidShapeFormatException {
        validatePolygonArea( new Triangle( -90, -90,10,10,10) ,4050.0000);
    }
    @Test (expectedExceptions = InvalidShapeFormatException.class, expectedExceptionsMessageRegExp = "Please input positive values for the triangle base and height; ")
    public void testNegativeDiffSides() throws InvalidShapeFormatException {
        validatePolygonArea( new Triangle( -90, 120,10,10,10) ,-5400);
    }
    @Test
    public void testPerimeter() throws InvalidShapeFormatException {
        validatePolygonPerimeter(new Triangle(12,12,12,12,12), 12*3 );
    }
    @Test
    public void testFloatPerimeter() throws InvalidShapeFormatException {
        validatePolygonPerimeter(new Triangle(12,12,12.2,12.2,12.2), 36.6000 );
    }
    @Test(expectedExceptions = InvalidShapeFormatException.class, expectedExceptionsMessageRegExp = "Please input valid values to the triangle sides, SideA \\+ SideB >= SideC; ")
    public void testInvalidInputValues() throws InvalidShapeFormatException {
        new Triangle(12,12,60,12.2,12.9);
    }
    @Test(expectedExceptions = InvalidShapeFormatException.class, expectedExceptionsMessageRegExp = "Please input positive values for the triangle sides; Please input valid values to the triangle sides, SideA \\+ SideB >= SideC; ")
    public void testSidesZero() throws InvalidShapeFormatException {
        new Triangle(12,12,0,0,0);
    }

}
