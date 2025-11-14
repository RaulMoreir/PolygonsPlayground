package dataproviders2dShapes;

import org.testng.annotations.DataProvider;

public class TrapezoidDataProvider {

    @DataProvider(name = "validTrapezoidArea")
    public static Object[][] validTrapezoidArea(){
        return new Object[][] {{10, 20, 10, 10, 15, 150},{10.5,20.5,10,10,15,155}};
    }

    @DataProvider(name = "invalidTrapezoidArea")
    public static Object[][] invalidTrapezoidArea(){
        return new Object[][] {{0,20.5,10,10,15},{-2.5,20.5,10,10,15}};
    }

    @DataProvider(name = "validTrapezoidPerimeter")
    public static Object[][] validTrapezoidPerimeter(){
        return new Object[][] {{10, 20, 10, 10, 15, 55},{10.5, 20, 10, 10.5, 15, 56}};
    }

    @DataProvider(name = "invalidTrapezoidPerimeter")
    public static Object[][] invalidTrapezoidPerimeter(){
        return new Object[][] {
                {0,20.5,10,10,15,"input values should be grater than zero. "},
                {-2.5,-20.5,10,10,15,"input values should be grater than zero. Minor base should be smaller than major base. "}};
    }

    @DataProvider(name = "invalidTrapezoidBaseInput")
    public static Object[][] invalidTrapezoidBaseInput(){
        return new Object[][] {
                {10, 10, 10, -10, 12, "input values should be grater than zero. Minor base should be smaller than major base. Side A should be greater or equals to the height. "},
                {10, -4, 10, 10, 12, "input values should be grater than zero. Minor base should be smaller than major base. "}};
    }

    @DataProvider(name = "trapezoidValidation")
    public static Object[][] trapezoidValidation(){
        return new Object[][]{
                {10, 10, 10, 10, 12, "Minor base should be smaller than major base. "},
                {10, 20, 10, 9, 12,"Side A should be greater or equals to the height. "},
                {10, 20, 10, 12, 9,"Side B should be greater or equals to the height. "},
                {10, 20, 10, 10, 10,"Don't you want a square or a rectangle instead. "},
                {10, 10, 10, 10, 10,"Minor base should be smaller than major base. Don't you want a square or a rectangle instead. "}
        };
    }
    @DataProvider (name = "validateTrapezoidVariety")
    public static Object[][] validateTrapezoidVariety(){
        return new Object[][] {
                {10,11,16,20,17, "the Trapezoid is a scalene"},
                {15,20,15,20,20, "the Trapezoid is an isosceles"},
                {10,20,16,16,17, "the Trapezoid is a right"},
                {20,25,15,15,17, "the Trapezoid is a right"}};
    }


}
