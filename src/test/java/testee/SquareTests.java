package testee;

import main.shapes2d.Square;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SquareTests extends AbstractPolygonTest {


    @Test
    public void quadradoTestValorPositivoPequeno(){
        int value = 3;
        double expectedValue = 9d;
        validatePolygonArea(new Square(value), expectedValue);

    }
    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "insert a valid number")
    public void quadradoTestValorNegativoPequeno(){
        int valor = -3;
        double expectedValue = 9d;
        validatePolygonArea(new Square(valor), expectedValue);

    }
    @Test
    public void quadradoTestValorMaxInt(){
        double valor = Integer.MAX_VALUE;
        double expectedValue = 4611686014132420608.0000;
        validatePolygonArea(new Square(valor), expectedValue);
    }

    @Test
    public void quadradoTestValorZero(){
        int valor = 0;
        double expectedValue = 0d;
        validatePolygonArea(new Square(valor), expectedValue);
    }

    @Test
    public void testFloatArea(){
        double valor = 6.5;
        double expectedValue = 42.2500d;
        validatePolygonArea(new Square(valor), expectedValue);
    }
    @Test
    public void testFloatPerimeter(){
        double valor = 6.5;
        double expectedValue = 26.0000;
        validatePolygonPerimeter(new Square(valor), expectedValue);
    }
    @Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "insert a valid number")
    public void testNegativeFloatPerimeter(){
        double valor = -6.5;
        double expectedValue = -26.0000;
        validatePolygonPerimeter(new Square(valor), expectedValue);
    }

}




