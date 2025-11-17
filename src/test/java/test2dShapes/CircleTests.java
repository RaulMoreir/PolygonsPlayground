package test2dShapes;

import dataproviders2dShapes.CircleDataProvider;
import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.Circle;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CircleTests extends AbstractPolygonTest {

    @Test(dataProviderClass = CircleDataProvider.class, dataProvider = "validCircleArea")
    public void validCircleArea(double radius, double expectedArea) throws InvalidShapeFormatException{
        validatePolygonArea(new Circle(radius),expectedArea);
    }

    @Test(dataProviderClass = CircleDataProvider.class,
            dataProvider = "invalidCircle")
    public void invalidCircleArea(double radius, String expectedMessage) {
        String message = "";
        try {
            new Circle(radius);
        } catch (InvalidShapeFormatException e) {
            message = expectedMessage;
        }
        Assert.assertEquals(expectedMessage, message);
    }

    @Test(dataProviderClass = CircleDataProvider.class, dataProvider = "validCirclePerimeter")
    public void validCirclePerimeter(double radius, double expectedPerimeter) throws InvalidShapeFormatException{
        validatePolygonPerimeter(new Circle(radius), expectedPerimeter);
    }
}
