package main.shapes2d;

import main.exceptions.InvalidShapeFormatException;

import java.math.BigDecimal;

import static main.util.BigDecimalFormatter.getBigDecimal;

public class Square implements AreaShape, PerimeterShape {

    private final double sideLength;

    public Square( double sideLength) throws InvalidShapeFormatException {
        this.sideLength = sideLength;
        if (sideLength <= 0){
            throw new InvalidShapeFormatException("Please input valid values to the Square sides");
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
