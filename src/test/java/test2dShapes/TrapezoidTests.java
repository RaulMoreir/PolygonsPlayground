package test2dShapes;

import dataproviders2dShapes.TrapezoidDataProvider;
import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.Trapezoid;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TrapezoidTests extends AbstractPolygonTypeTest {

    @Test(dataProviderClass = TrapezoidDataProvider.class, dataProvider = "validTrapezoidArea")
    public void validTrapezoidArea(double minorBase, double majorBase,
                                   double height, double sideA,
                                   double sideB, double expectedArea)
            throws InvalidShapeFormatException{
        validatePolygonArea(new Trapezoid(minorBase,majorBase,height,sideA,sideB),expectedArea);
    }

    @Test(dataProviderClass = TrapezoidDataProvider.class, dataProvider = "validTrapezoidPerimeter")
    public void validTrapezoidPerimeter(double minorBase, double majorBase,
                                   double height, double sideA,
                                   double sideB, double expectedPerimeter)
            throws InvalidShapeFormatException{
        validatePolygonPerimeter(new Trapezoid(minorBase,majorBase,height,sideA,sideB),expectedPerimeter);
    }

    @Test(dataProviderClass = TrapezoidDataProvider.class, dataProvider = "trapezoidValidation")
    public void trapezoidValidation(double minorBase, double majorBase,
                               double height, double sideA,
                               double sideB, String expectedMessage) {
        String actualMessage = "";
        try {
            new Trapezoid(minorBase,majorBase,height,sideA,sideB);
        } catch (InvalidShapeFormatException exceptionThrown) {
            actualMessage = exceptionThrown.getMessage();
        }
        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @Test(dataProviderClass = TrapezoidDataProvider.class, dataProvider = "validateTrapezoidVariety" )
    public void validateTrapezoidVariety(double minorBase, double majorBase,
                                         double height, double sideA,
                                         double sideB, String expectedType) throws InvalidShapeFormatException {
        validatePolygonType(new Trapezoid(minorBase,majorBase,height,sideA,sideB),expectedType);
    }

}
