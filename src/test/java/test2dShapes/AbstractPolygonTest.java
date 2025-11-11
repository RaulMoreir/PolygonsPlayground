package test2dShapes;

import main.shapes2d.AreaShape;
import main.shapes2d.PerimeterShape;
import main.util.BigDecimalFormatter;
import org.testng.Assert;

import java.math.BigDecimal;

public abstract class AbstractPolygonTest {

    protected void validatePolygonArea(AreaShape areaShape, double expectedArea) {
        BigDecimal expectedBig = BigDecimalFormatter.getBigDecimal(expectedArea);
        Assert.assertEquals(areaShape.getArea(), expectedBig);
    }

    protected void validatePolygonPerimeter(PerimeterShape perimeterShape, double expectedPerimeter) {
        BigDecimal expectedBig = BigDecimalFormatter.getBigDecimal(expectedPerimeter);
        Assert.assertEquals(perimeterShape.getPerimeter(), expectedBig);
    }

}
