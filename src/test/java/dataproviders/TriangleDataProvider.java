package dataproviders;

import org.testng.annotations.DataProvider;

public class TriangleDataProvider {
    @DataProvider(name = "validTriangleArea" )
    public static Object[][] validTriangleArea(){
        return new Object[][] {{20,20,10,10,10,200},{40,30,10,10,10,600},{10.5,15,10,10,10,78.75}};
    }
    @DataProvider(name = "invalidTriangleArea")
    public static Object[][] invalidTriangleArea(){
        return new Object[][] {{0,0,10,10,10},{-2,5,10,10,10},{-10,8,10,10,10},{0,3,10,10,10},{4,0,10,10,10}};
    }
    @DataProvider (name = "validTrianglePerimeter")
    public static Object[][] validTrianglePerimeter(){
        return new Object[][] {{1,1,15,20,10,45},{1,1,10,10,10,30},{1,1,40,20,30,90}};
    }
    @DataProvider (name = "invalidTrianglePerimeter")
    public static Object[][] invalidTrianglePerimeter(){
        return new Object[][] {{1,1,10,10,-10},{1,1,10,10,-2.5},{1,1,0,10,10}};
    }
}
