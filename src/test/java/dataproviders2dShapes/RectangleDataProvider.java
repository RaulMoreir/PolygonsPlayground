package dataproviders2dShapes;

import org.testng.annotations.DataProvider;

public class RectangleDataProvider {
    @DataProvider(name = "validRectangleAreaSides" )
    public static Object[][] validRectangleAreaSides(){
        return new Object[][] {{120,90,10800},{90,120,10800},{10.5,20,210}};
    }
    @DataProvider(name = "invalidRectangle")
    public static Object[][] invalidRectangleSides(){
        return new Object[][] {
                {0, 0, "Please input valid values to the Rectangle sides"},
                {-2, 5, "Please input valid values to the Rectangle sides"},
                {-10, 8, "Please input valid values to the Rectangle sides"},
                {0, 3, "Please input valid values to the Rectangle sides"},
                {4, 0, "Please input valid values to the Rectangle sides"}};
    }
    @DataProvider (name = "validRectanglePerimeterSide")
    public static Object[][] validRectanglePerimeterSide(){
        return new Object[][] {{120,90,420},{90,120,420},{10.5,20,61}};
    }
}
