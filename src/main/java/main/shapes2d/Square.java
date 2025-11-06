package main.shapes2d;

import java.math.BigDecimal;

import static main.util.BigDecimalFormatter.getBigDecimal;

public class Square implements AreaShape, PerimeterShape {

    private final double sideLength;

    public Square( double sideLength) {
        this.sideLength = sideLength;
        if (sideLength < 0){
            throw new RuntimeException("insert a valid number");
        }

    }

    public BigDecimal getArea(){
        return getBigDecimal(sideLength * sideLength);
    }

    @Override
    public BigDecimal getPerimeter() {
        return getBigDecimal(sideLength * 4);
    }
}
