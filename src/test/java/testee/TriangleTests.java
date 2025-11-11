package testee;

import dataproviders.TriangleDataProvider;
import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.Triangle;
import org.testng.annotations.Test;

public class TriangleTests extends AbstractPolygonTest {

    @Test(dataProviderClass = TriangleDataProvider.class,
            dataProvider = "validTriangleArea")
    public void validTriangleArea (double base, double height, double sideA, double sideB,double sideC, double expectedArea)
            throws InvalidShapeFormatException{
        validatePolygonArea(new Triangle(base, height, sideA, sideB, sideC), expectedArea);
    }

    @Test(expectedExceptions = InvalidShapeFormatException.class,
            expectedExceptionsMessageRegExp = "Please input positive values for the triangle base and height; ",
            dataProviderClass = TriangleDataProvider.class,
            dataProvider = "invalidTriangleArea")
    public void invalidTriangleArea (double base, double height, double sideA, double sideB,double sideC) throws InvalidShapeFormatException{
        new Triangle(base, height, sideA, sideB, sideC);
    }

    @Test(dataProviderClass = TriangleDataProvider.class,
            dataProvider = "validTrianglePerimeter")
    public void validTrianglePerimeter (double base, double height, double sideA, double sideB,double sideC, double expectedPerimeter)
            throws InvalidShapeFormatException{
        validatePolygonPerimeter(new Triangle(base, height, sideA, sideB, sideC), expectedPerimeter);
    }

    @Test(expectedExceptions = InvalidShapeFormatException.class,
            expectedExceptionsMessageRegExp = "Please input positive values for the triangle sides; Please input valid values to the triangle sides, SideA \\+ SideB >= SideC; ",
            dataProviderClass = TriangleDataProvider.class,
            dataProvider = "invalidTrianglePerimeter")
    public void invalidTrianglePerimeter (double base, double height, double sideA, double sideB, double sideC) throws InvalidShapeFormatException{
        new Triangle(base, height, sideA, sideB, sideC);
    }
}
