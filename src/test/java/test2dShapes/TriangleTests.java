package test2dShapes;

import dataproviders2dShapes.TriangleDataProvider;
import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.Triangle;
import org.testng.annotations.Test;

public class TriangleTests extends AbstractPolygonTypeTest {

    @Test(dataProviderClass = TriangleDataProvider.class,
            dataProvider = "validTriangleArea")
    public void testValidTriangleArea (double base, double height, double sideA, double sideB,double sideC, double expectedArea)
            throws InvalidShapeFormatException{
        validatePolygonArea(new Triangle(base, height, sideA, sideB, sideC), expectedArea);
    }

    @Test(expectedExceptions = InvalidShapeFormatException.class,
            expectedExceptionsMessageRegExp = "Please input positive values for the triangle base and height; ",
            dataProviderClass = TriangleDataProvider.class,
            dataProvider = "invalidTriangleArea")
    public void testInvalidTriangleArea (double base, double height, double sideA, double sideB,double sideC) throws InvalidShapeFormatException{
        new Triangle(base, height, sideA, sideB, sideC);
    }

    @Test(dataProviderClass = TriangleDataProvider.class,
            dataProvider = "validTrianglePerimeter")
    public void testValidTrianglePerimeter (double base, double height, double sideA, double sideB,double sideC, double expectedPerimeter)
            throws InvalidShapeFormatException{
        validatePolygonPerimeter(new Triangle(base, height, sideA, sideB, sideC), expectedPerimeter);
    }

    @Test(expectedExceptions = InvalidShapeFormatException.class,
            expectedExceptionsMessageRegExp = "Please input positive values for the triangle sides; Please input valid values to the triangle sides, SideA \\+ SideB >= SideC; ",
            dataProviderClass = TriangleDataProvider.class,
            dataProvider = "invalidTrianglePerimeter")
    public void testInvalidTrianglePerimeter (double base, double height, double sideA, double sideB, double sideC) throws InvalidShapeFormatException{
        new Triangle(base, height, sideA, sideB, sideC);
    }

    @Test(dataProviderClass = TriangleDataProvider.class, dataProvider = "validTriangleType")
    public void testValidTriangleType(double base, double height, double sideA, double sideB, double sideC, String type) throws InvalidShapeFormatException {
        validatePolygonType(new Triangle(base,height,sideA,sideB,sideC),type);
    }
}
