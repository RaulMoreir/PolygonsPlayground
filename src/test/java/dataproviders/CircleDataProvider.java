package dataproviders;

import org.testng.annotations.DataProvider;

public class CircleDataProvider {
    @DataProvider(name = "validCircleArea")
    public static Object[][] validCircleArea(){
        return new Object[][] {{10,314.1593},{30,2827.4334},{30.5,2922.4666}};
    }

    @DataProvider(name = "invalidCircleArea")
    public static Object[][] invalidCircleArea(){
        return new Object[][] {{-10},{-2.5},{0}};
    }

    @DataProvider(name = "validCirclePerimeter")
    public static Object[][] validCirclePerimeter(){
        return new Object[][] {{10,62.8319},{30,188.4956},{30.5,191.6372}};
    }

    @DataProvider(name = "invalidCirclePerimeter")
    public static Object[][] invalidCirclePerimeter(){
        return new Object[][] {{-10},{-2.5},{0}};
    }
}
