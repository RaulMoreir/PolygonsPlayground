package test2dShapes;

import dataproviders2dShapes.TriangleDataProvider;
import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.Triangle;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TriangleTests extends AbstractPolygonTypeTest {

    @Test(dataProviderClass = TriangleDataProvider.class,
            dataProvider = "validTriangleArea")
    public void testValidTriangleArea (double base, double height, double sideA, double sideB,double sideC, double expectedArea)
            throws InvalidShapeFormatException{
        validatePolygonArea(new Triangle(base, height, sideA, sideB, sideC), expectedArea);
    }

    @Test(dataProviderClass = TriangleDataProvider.class,
            dataProvider = "invalidTriangle")
    public void testInvalidTriangle (double base, double height, double sideA, double sideB,double sideC, String expectedMessage) {
        String message;
        try {
            new Triangle(base, height, sideA, sideB, sideC);
        } catch (InvalidShapeFormatException e) {
            message = e.getMessage();
            assertEquals(expectedMessage, message);
        }
    }

    @Test(dataProviderClass = TriangleDataProvider.class,
            dataProvider = "validTrianglePerimeter")
    public void testValidTrianglePerimeter (double base, double height, double sideA, double sideB,double sideC, double expectedPerimeter)
            throws InvalidShapeFormatException{
        validatePolygonPerimeter(new Triangle(base, height, sideA, sideB, sideC), expectedPerimeter);
    }

    @Test(dataProviderClass = TriangleDataProvider.class, dataProvider = "validTriangleType")
    public void testValidTriangleType(double base, double height, double sideA, double sideB, double sideC, String type) throws InvalidShapeFormatException {
        validatePolygonType(new Triangle(base,height,sideA,sideB,sideC),type);
    }
}
