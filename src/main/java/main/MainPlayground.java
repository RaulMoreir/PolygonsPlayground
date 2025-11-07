package main;

import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.*;

public class MainPlayground {

    public static void main(String[] args) throws InvalidShapeFormatException {

        AreaShape squareLado3 = new Square(3);
        AreaShape squareLado1 = new Square(1);
        AreaShape squareLado6 = new Square(6);
        AreaShape squareLado33 = new Square(33);
        AreaShape footBallGround = null;
        try {
            footBallGround = new Rectangle(120, 90);
        } catch (InvalidShapeFormatException e) {
            throw new RuntimeException(e);
        }
        AreaShape pizzaSlice = null;
        try {
            pizzaSlice = new Triangle(120, 90, 120, 90, 45);
        } catch (InvalidShapeFormatException e) {
            throw new RuntimeException(e);
        }
        AreaShape fullPizza = null;
        try {
            fullPizza = new Circle(30);
        } catch (InvalidShapeFormatException e) {
            throw new RuntimeException(e);
        }


        System.out.println(squareLado1.getArea());
        System.out.println(squareLado3.getArea());
        System.out.println(squareLado6.getArea());
        System.out.println(squareLado33.getArea());
        System.out.println(footBallGround.getArea());
        System.out.println(pizzaSlice.getArea());
        System.out.println(fullPizza.getArea());

    }
}


