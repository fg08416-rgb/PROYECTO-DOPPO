package Shapes;

import java.awt.*;

/**
 * Un triángulo que puede manipularse y dibujarse en el canvas.
 * Hereda el estado y comportamiento común de Shape.
 *
 * @author  Michael Kolling and David J. Barnes (refactorizado)
 * @version 2.0
 */
public class Triangle extends Shape {

    public static int VERTICES = 3;

    private int height;
    private int width;

    /**
     * Crea un triángulo con posición y tamaño por defecto.
     */
    public Triangle() {
        super(140, 15, "green");
        height = 30;
        width  = 40;
    }

    /**
     * Cambia las dimensiones del triángulo.
     * @param newHeight nueva altura en píxeles (>= 0)
     * @param newWidth  nuevo ancho  en píxeles (>= 0)
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width  = newWidth;
        draw();
    }

    /**
     * Retorna el polígono que representa al triángulo.
     */
    @Override
    protected java.awt.Shape getShape() {
        int[] xpoints = { xPosition, xPosition + (width / 2), xPosition - (width / 2) };
        int[] ypoints = { yPosition, yPosition + height,      yPosition + height       };
        return new Polygon(xpoints, ypoints, 3);
    }
}
