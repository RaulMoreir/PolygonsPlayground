package testee;

import dataproviders.CircleDataProvider;
import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.Circle;
import org.testng.annotations.Test;

public class CircleTests extends AbstractPolygonTest {

    @Test(dataProviderClass = CircleDataProvider.class, dataProvider = "validCircleArea")
    public void validCircleArea(double radius, double expectedArea) throws InvalidShapeFormatException{
        validatePolygonArea(new Circle(radius),expectedArea);
    }

    @Test(expectedExceptions = InvalidShapeFormatException.class,
            expectedExceptionsMessageRegExp = "Please input a valid value to the Circle radius",
            dataProviderClass = CircleDataProvider.class,
            dataProvider = "invalidCircleArea")
    public void invalidCircleArea(double radius) throws InvalidShapeFormatException{
        new Circle(radius);
    }

    @Test(dataProviderClass = CircleDataProvider.class, dataProvider = "validCirclePerimeter")
    public void validCirclePerimeter(double radius, double expectedPerimeter) throws InvalidShapeFormatException{
        validatePolygonPerimeter(new Circle(radius), expectedPerimeter);
    }

    @Test(expectedExceptions = InvalidShapeFormatException.class,
            expectedExceptionsMessageRegExp = "Please input a valid value to the Circle radius",
            dataProviderClass = CircleDataProvider.class,
            dataProvider = "invalidCirclePerimeter")
    public void invalidCirclePerimeter(double radius) throws InvalidShapeFormatException{
        new Circle(radius);
    }
}
