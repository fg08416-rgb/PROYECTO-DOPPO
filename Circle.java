package Shapes;

import java.awt.geom.*;

/**
 * Un círculo que puede manipularse y dibujarse en el canvas.
 * Hereda el estado y comportamiento común de Shape.
 *
 * @author  Michael Kolling and David J. Barnes (refactorizado)
 * @version 2.0
 */
public class Circle extends Shape {

    public static final double PI    = 3.1416;

    private int diameter;

    /**
     * Crea un círculo con posición y tamaño por defecto.
     */
    public Circle() {
        super(20, 15, "blue");
        diameter = 30;
    }

    /**
     * Cambia el diámetro del círculo.
     * @param newDiameter nuevo diámetro en píxeles (>= 0)
     */
    public void changeSize(int newDiameter) {
        erase();
        diameter = newDiameter;
        draw();
    }

    /**
     * Retorna la elipse que representa al círculo.
     */
    @Override
    protected java.awt.Shape getShape() {
        return new Ellipse2D.Double(xPosition, yPosition, diameter, diameter);
    }
}
