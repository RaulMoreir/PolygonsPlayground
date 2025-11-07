package dataproviders;

import org.testng.annotations.DataProvider;

public class SquareDataProvider {

    @DataProvider(name = "validCalculationSquareData")
    public static Object[][] validCalculationSquareData() {
        return new Object[][] {
                {3, 9d}, {Integer.MAX_VALUE, 4611686014132420608.0000}, {6.5, 42.25}, {22, 484}, {23, 529}};
    }

    @DataProvider(name = "invalidCalculationSquareData")
    public static Object[][] invalidCalculationSquareData() {
        return new Object[][] {{-2}, {-2.5}, {0}};
    }

    @DataProvider(name = "ValidPerimeterSquare")
    public static Object[][] ValidPerimeterSquare(){
        return new Object[][] {{2, 8}, {10, 40}, {2.5, 10}};
    }
    @DataProvider(name = "InvalidPerimeterSquare")
    public static Object[][] InvalidPerimeterSquare(){
        return new Object[][] {{-2}, {-2.5}, {0}};
    }
}
