package main;

import main.exceptions.InvalidShapeFormatException;
import main.shapes2d.*;

public class MainPlayground {

    public static void main(String[] args) throws InvalidShapeFormatException {

        AreaShape squareLado3 = new Square(3);
        AreaShape squareLado1 = new Square(1);
        AreaShape squareLado6 = new Square(6);
        AreaShape squareLado33 = new Square(33);

        AreaShape footBallGround = new Rectangle(120, 90);

        AreaShape pizzaSlice = new Triangle(120, 90, 120, 90, 45);

        AreaShape fullPizza = new Circle(30);
        AreaShape strangeRectangle = new Trapezoid(9,10,10,15,10);

        Trapezoid trapezio = new Trapezoid(10, 20, 10, 15, 15);

        System.out.println(trapezio.getPerimeter());
        System.out.println(trapezio.getArea());


        System.out.println(squareLado1.getArea());
        System.out.println(squareLado3.getArea());
        System.out.println(squareLado6.getArea());
        System.out.println(squareLado33.getArea());
        System.out.println(footBallGround.getArea());
        System.out.println(pizzaSlice.getArea());
        System.out.println(fullPizza.getArea());
        System.out.println(trapezio.determineType());

    }
}


