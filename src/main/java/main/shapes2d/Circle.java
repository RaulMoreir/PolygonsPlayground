package main.shapes2d;

import main.exceptions.InvalidShapeFormatException;

import java.math.BigDecimal;

import static main.util.BigDecimalFormatter.getBigDecimal;

public class Circle implements AreaShape, PerimeterShape {

    double radius;

    public Circle (double radius) throws InvalidShapeFormatException {
        this.radius = radius;
        ValidateCircle();
    }

    private void ValidateCircle() throws InvalidShapeFormatException{
        if(radius <= 0){
            throw new InvalidShapeFormatException("Please input a valid value to the Circle radius");
        }
    }
    @Override
    public BigDecimal getArea() {
        return getBigDecimal(Math.PI * (radius * radius));
    }

    @Override
    public BigDecimal getPerimeter() {
        return getBigDecimal(2 * Math.PI * radius);
    }
}
