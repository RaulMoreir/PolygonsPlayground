package dataproviders2dShapes;

import org.testng.annotations.DataProvider;

public class CircleDataProvider {
    @DataProvider(name = "validCircleArea")
    public static Object[][] validCircleArea(){
        return new Object[][] {{10, 314.1593},{30, 2827.4334},{30.5, 2922.4666}};
    }

    @DataProvider(name = "invalidCircle")
    public static Object[][] invalidCircle(){
        return new Object[][] {
                {-10, "Please input a valid value to the Circle radius"},
                {-2.5, "Please input a valid value to the Circle radius"},
                {0, "Please input a valid value to the Circle radius"}};
    }

    @DataProvider(name = "validCirclePerimeter")
    public static Object[][] validCirclePerimeter(){
        return new Object[][] {{10,62.8319},{30,188.4956},{30.5,191.6372}};
    }
}
