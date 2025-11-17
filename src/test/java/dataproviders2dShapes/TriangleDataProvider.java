package dataproviders2dShapes;

import org.testng.annotations.DataProvider;

public class TriangleDataProvider {
    @DataProvider(name = "validTriangleArea" )
    public static Object[][] validTriangleArea(){
        return new Object[][] {
                {20,20,10,10,10,200},
                {40,30,10,10,10,600},
                {10.5,15,10,10,10,78.75}};
    }
    @DataProvider(name = "invalidTriangle")
    public static Object[][] invalidTriangle(){
        return new Object[][] {
                {0,0,10,10,10,"Please input positive values for the triangle base and height; "},
                {-2,5,10,10,10,"Please input positive values for the triangle base and height; "},
                {-10,8,10,10,10,"Please input positive values for the triangle base and height; "},
                {0,3,10,10,10,"Please input positive values for the triangle base and height; "},
                {4,0,10,10,10,"Please input positive values for the triangle base and height; "},
                {1,1,10,10,-10,"Please input positive values for the triangle sides; Please input valid values to the triangle sides, SideA + SideB >= SideC; "},
                {1,1,10,10,-2.5,"Please input positive values for the triangle sides; Please input valid values to the triangle sides, SideA + SideB >= SideC; "},
                {1,1,0,10,10,"Please input positive values for the triangle sides; Please input valid values to the triangle sides, SideA + SideB >= SideC; "}};
    }
    @DataProvider (name = "validTrianglePerimeter")
    public static Object[][] validTrianglePerimeter(){
        return new Object[][] {
                {1,1,15,20,10,45},
                {1,1,10,10,10,30},
                {1,1,40.5,20,30,90.5}};
    }

    @DataProvider (name = "validTriangleType")
    public static Object[][] validTriangleType(){
        return new Object[][] {
                {20,10,16,15,17, "the Triangle is a scalene"},
                {15,10,15,15,15, "the Triangle is equilateral"},
                {20,20,16,20,17, "the Triangle is a rectangle"},
                {20,10,15,15,17, "the Triangle is isosceles"}};
    }

}
