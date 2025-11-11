package test2dShapes;

import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.Rectangle;
import dataproviders2dShapes.RectangleDataProvider;
import org.testng.annotations.Test;

public class RectangleTests extends AbstractPolygonTest {

    @Test(dataProviderClass = RectangleDataProvider.class,
            dataProvider = "validRectangleAreaSides")
    public void testParametrizedValidRectangleAreaSides(double rectangleSideLengthA,double rectangleSideLengthB, double expectedArea)throws InvalidShapeFormatException{
        validatePolygonArea(new Rectangle( rectangleSideLengthA, rectangleSideLengthB), expectedArea);
    }

    @Test(expectedExceptions = InvalidShapeFormatException.class,
            expectedExceptionsMessageRegExp = "Please input valid values to the Rectangle sides",
            dataProviderClass = RectangleDataProvider.class,
            dataProvider = "invalidRectangleAreaSides")
    public void testParametrizedInvalidRectangleAreaSides(double rectangleSideLengthA,double rectangleSideLengthB)throws InvalidShapeFormatException{
        new Rectangle( rectangleSideLengthA, rectangleSideLengthB);
    }

    @Test(dataProviderClass = RectangleDataProvider.class,
            dataProvider = "validRectanglePerimeterSide")
    public void testParametrizedValidRectanglePerimeterSides(double rectangleSideLengthA,double rectangleSideLengthB, double expectedPerimeter)throws InvalidShapeFormatException{
        validatePolygonPerimeter(new Rectangle( rectangleSideLengthA, rectangleSideLengthB), expectedPerimeter);
    }

    @Test(expectedExceptions = InvalidShapeFormatException.class,
            expectedExceptionsMessageRegExp = "Please input valid values to the Rectangle sides",
            dataProviderClass = RectangleDataProvider.class,
            dataProvider = "invalidRectanglePerimeterSide")
    public void testParametrizedInvalidRectanglePerimeterSides(double rectangleSideLengthA,double rectangleSideLengthB)throws InvalidShapeFormatException{
        new Rectangle(rectangleSideLengthA, rectangleSideLengthB);
    }
}
