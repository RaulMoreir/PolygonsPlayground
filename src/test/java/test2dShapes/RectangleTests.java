package test2dShapes;

import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.Rectangle;
import dataproviders2dShapes.RectangleDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RectangleTests extends AbstractPolygonTest {

    @Test(dataProviderClass = RectangleDataProvider.class,
            dataProvider = "validRectangleAreaSides")
    public void testParametrizedValidRectangleAreaSides(double rectangleSideLengthA,double rectangleSideLengthB, double expectedArea)throws InvalidShapeFormatException{
        validatePolygonArea(new Rectangle( rectangleSideLengthA, rectangleSideLengthB), expectedArea);
    }

    @Test(dataProviderClass = RectangleDataProvider.class, dataProvider = "invalidRectangle")
    public void testParametrizedInvalidRectangle(
            double rectangleSideLengthA, double rectangleSideLengthB, String expectedMessage){
        String message = "";
        try {
            new Rectangle( rectangleSideLengthA, rectangleSideLengthB);
        } catch (InvalidShapeFormatException e) {
            message = e.getMessage();
        }
        Assert.assertEquals(expectedMessage, message );
    }

    @Test(dataProviderClass = RectangleDataProvider.class,
            dataProvider = "validRectanglePerimeterSide")
    public void testParametrizedValidRectanglePerimeterSides(double rectangleSideLengthA,double rectangleSideLengthB, double expectedPerimeter)throws InvalidShapeFormatException{
        validatePolygonPerimeter(new Rectangle( rectangleSideLengthA, rectangleSideLengthB), expectedPerimeter);
    }

}
