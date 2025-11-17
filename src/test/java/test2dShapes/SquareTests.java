package test2dShapes;

import dataproviders2dShapes.SquareDataProvider;
import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.Square;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SquareTests extends AbstractPolygonTest {

    @Test(dataProviderClass = SquareDataProvider.class,
            dataProvider = "validCalculationSquareData")
    public void testParametrizedValidSquareArea(double squareSideLength, double expectedArea) throws InvalidShapeFormatException {
        validatePolygonArea(new Square(squareSideLength), expectedArea);
    }

    @Test(dataProviderClass = SquareDataProvider.class,
            dataProvider = "invalidCalculationSquareData")
    public void testParametrizedInvalidSquareArea(double squareSideLength, String expectedMessage) {
        String message = "";
        try {
            new Square(squareSideLength);
        } catch (InvalidShapeFormatException e) {
            message = e.getMessage();
        }
        Assert.assertEquals(expectedMessage, message);
    }

    @Test(dataProviderClass = SquareDataProvider.class,
            dataProvider = "ValidPerimeterSquare")
    public void testParametrizedValidSquarePerimeter(double squareSideLength, double expectedPerimeter) throws InvalidShapeFormatException{
        validatePolygonPerimeter(new Square(squareSideLength),expectedPerimeter);
    }

}