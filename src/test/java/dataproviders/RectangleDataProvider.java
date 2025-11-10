package dataproviders;

import org.testng.annotations.DataProvider;

public class RectangleDataProvider {
    @DataProvider(name = "validRectangleAreaSides" )
    public static Object[][] validRectangleAreaSides(){
        return new Object[][] {{120,90,10800},{90,120,10800},{10.5,20,210}};
    }
    @DataProvider(name = "invalidRectangleAreaSides")
    public static Object[][] invalidRectangleSides(){
        return new Object[][] {{0,0},{-2,5},{-10,8},{0,3},{4,0}};
    }
    @DataProvider (name = "validRectanglePerimeterSide")
    public static Object[][] validRectanglePerimeterSide(){
        return new Object[][] {{120,90,420},{90,120,420},{10.5,20,61}};
    }
    @DataProvider (name = "invalidRectanglePerimeterSide")
    public static Object[][] invalidRectanglePerimeterSide(){
        return new Object[][] {{0,0},{-2,5},{-10,8},{0,3},{4,0}};
    }
}
