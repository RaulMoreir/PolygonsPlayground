package main.shapes2d;

import main.exceptions.InvalidShapeFormatException;

import java.math.BigDecimal;

import static main.util.BigDecimalFormatter.getBigDecimal;

public class Rectangle implements AreaShape, PerimeterShape {

    private final double sideA;
    private final double sideB;

    public Rectangle(double sideA, double sideB) throws InvalidShapeFormatException {
        this.sideA = sideA;
        this.sideB = sideB;
        validateRectangle();
    }

    private void validateRectangle() throws InvalidShapeFormatException {
        if(sideA <= 0 || sideB <= 0){
            throw new InvalidShapeFormatException("Please input valid values to the Rectangle sides");
        }
    }


    public BigDecimal getArea() {
           return getBigDecimal(sideA * sideB);
    }

    @Override
    public BigDecimal getPerimeter() {
        return getBigDecimal((sideA + sideB) * 2);
    }
}
