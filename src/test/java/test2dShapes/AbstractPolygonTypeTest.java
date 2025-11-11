package test2dShapes;

import main.shapes2d.PolygonType;
import org.testng.Assert;

public abstract class AbstractPolygonTypeTest extends AbstractPolygonTest{

    protected void validatePolygonType(PolygonType type, String expectedType){
        Assert.assertEquals(type.determineType(), expectedType);
    }

}
