package main.shapes2d;

import main.exceptions.InvalidShapeFormatException;

import java.math.BigDecimal;

import static main.util.BigDecimalFormatter.getBigDecimal;

public class Triangle implements AreaShape, PerimeterShape, PolygonType {

    double base;
    double height;

    double sideA;
    double sideB;
    double sideC;

    public Triangle(double base, double height, double sideA, double sideB, double sideC) throws InvalidShapeFormatException {
        this.height = height;
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
        this.base = base;
        validateTriangle();
    }

    @Override
    public BigDecimal getArea() {
        return getBigDecimal((base * height) / 2d );
    }

    @Override
    public BigDecimal getPerimeter() {
        return getBigDecimal(sideA + sideB + sideC);
    }

    private void validateTriangle() throws InvalidShapeFormatException {
        String errorMessage = "";
        if (haveNegativeSideValues()) {
            errorMessage = errorMessage + "Please input positive values for the triangle sides; ";
        }
        if (hasInvalidSideValues()) {
            errorMessage = errorMessage + "Please input valid values to the triangle sides, SideA + SideB >= SideC; ";
        }
        if ( hasNegativeBaseAndHeight()) {
            errorMessage = errorMessage + "Please input positive values for the triangle base and height; ";
        }
        if (!errorMessage.isEmpty()) {
            throw new InvalidShapeFormatException(errorMessage);
        }
    }

    private boolean haveNegativeSideValues() {
        return ( sideA <= 0 || sideB <= 0 || sideC <= 0 );
    }

    private boolean hasInvalidSideValues() {
        return (sideA + sideB <= sideC) ||
                (sideA + sideC <= sideB) ||
                (sideB + sideC <= sideA);
    }

    private boolean hasNegativeBaseAndHeight() {
        return ( base <= 0 || height <= 0 );
    }

    @Override
    public String determineType() {
        if ( (sideA == sideB && sideB != sideC ) ||
                (sideB == sideC && sideC != sideA) ||
                (sideC == sideA && sideA != sideB) ) {
            return "the Triangle is isosceles";
        }
        if ( sideA == sideB && sideB == sideC) {
            return "the Triangle is equilateral";
        }
        if (height == sideA || height == sideB || height == sideC) {
            return "the Triangle is a rectangle";
        }
        return "the Triangle is a scalene";
    }
}
