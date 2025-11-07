package testee;

import dataproviders.SquareDataProvider;
import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.Square;
import org.testng.annotations.Test;

public class SquareTests extends AbstractPolygonTest {

    @Test(dataProviderClass = SquareDataProvider.class,
            dataProvider = "validCalculationSquareData")
    public void testParametrizedValidSquareArea(double squareSideLength, double expectedArea) throws InvalidShapeFormatException {
        validatePolygonArea(new Square(squareSideLength), expectedArea);
    }

    @Test(expectedExceptions = InvalidShapeFormatException.class,
            expectedExceptionsMessageRegExp = "Please input valid values to the Square sides",
            dataProviderClass = SquareDataProvider.class,
            dataProvider = "invalidCalculationSquareData")
    public void testParametrizedInvalidSquareArea(double squareSideLength) throws InvalidShapeFormatException {
        new Square(squareSideLength);
    }

    @Test(dataProviderClass = SquareDataProvider.class,
            dataProvider = "ValidPerimeterSquare")
    public void testParametrizedValidSquarePerimeter(double squareSideLength, double expectedPerimeter) throws InvalidShapeFormatException{
        validatePolygonPerimeter(new Square(squareSideLength),expectedPerimeter);
    }

    @Test(expectedExceptions = InvalidShapeFormatException.class,
            expectedExceptionsMessageRegExp = "Please input valid values to the Square sides",
            dataProviderClass = SquareDataProvider.class,
            dataProvider = "InvalidPerimeterSquare")
    public void testParametrizedInvalidSquarePerimeter(double squareSideLength) throws InvalidShapeFormatException{
        new Square(squareSideLength);
    }

}