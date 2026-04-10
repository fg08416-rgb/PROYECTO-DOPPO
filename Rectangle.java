package Shapes;

/**
 * Un rectángulo que puede manipularse y dibujarse en el canvas.
 * Hereda el estado y comportamiento común de Shape.
 *
 * @author  Michael Kolling and David J. Barnes (refactorizado)
 * @version 2.0
 */
public class Rectangle extends Shape {

    public static int EDGES = 4;

    private int height;
    private int width;

    /**
     * Crea un rectángulo con posición y tamaño por defecto.
     */
    public Rectangle() {
        super(70, 15, "magenta");
        height = 30;
        width  = 40;
    }

    /**
     * Cambia las dimensiones del rectángulo.
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
     * Retorna el rectángulo AWT que representa a esta figura.
     */
    @Override
    protected java.awt.Shape getShape() {
        return new java.awt.Rectangle(xPosition, yPosition, width, height);
    }
}
