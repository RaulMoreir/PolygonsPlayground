package main.shapes2d;

import main.exceptions.InvalidShapeFormatException;
import java.math.BigDecimal;
import static main.util.BigDecimalFormatter.getBigDecimal;

public class Trapezoid implements AreaShape, PerimeterShape, PolygonType {

    public final double minorBase, majorBase, height, sideA, sideB;

    public Trapezoid(double minorBase, double majorBase, double height, double sideA, double sideB) throws InvalidShapeFormatException {
        this.minorBase = minorBase;
        this.majorBase = majorBase;
        this.height = height;
        this.sideA = sideA;
        this.sideB = sideB;
        validateTrapezoid();
    }
    private void validateTrapezoid() throws InvalidShapeFormatException {
        String validationMessage = "";
        if (minorBase <= 0 || majorBase <= 0 || height <= 0 || sideA <= 0 || sideB <= 0){
            validationMessage += "input values should be grater than zero. ";
        }
        if (minorBase >= majorBase) {
            validationMessage += "Minor base should be smaller than major base. ";
        }
        if (sideA < height) {
            validationMessage += "Side A should be greater or equals to the height. ";
        }
        if (sideB < height) {
            validationMessage += "Side B should be greater or equals to the height. ";
        }
        if (sideA == height && sideB == height) {
            validationMessage += "Don't you want a square or a rectangle instead. ";
        }
        if (!validationMessage.isEmpty()) {
            throw new InvalidShapeFormatException(validationMessage);
        }
    }

    @Override
    public BigDecimal getArea() {
        return getBigDecimal((( minorBase + majorBase ) * height) /2);
    }

    @Override
    public BigDecimal getPerimeter() {
        return getBigDecimal(majorBase + minorBase + sideA + sideB);
    }

    @Override
    public String determineType() {
        String determinate = "";
        if (sideA == sideB){
            determinate = "the Trapezoid is an isosceles";
        }
        if (sideA != sideB){
            determinate = "the Trapezoid is a scalene";
        }
        if (sideB == height || sideA == height){
            determinate = "the Trapezoid is a right";
        }
        return determinate;
    }
}
