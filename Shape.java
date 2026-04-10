package Shapes;

import java.awt.*;

/**
 * Clase abstracta que representa una figura geométrica genérica.
 * Contiene el estado y comportamiento común a todas las figuras del paquete.
 *
 * @author  Refactorización basada en Kolling & Barnes
 * @version 2.0
 */
public abstract class Shape {

    protected int     xPosition;
    protected int     yPosition;
    protected String  color;
    protected boolean isVisible;

    /**
     * Constructor base: inicializa posición, color y visibilidad.
     */
    public Shape(int xPosition, int yPosition, String color) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.color     = color;
        this.isVisible = false;
    }

    // ─── Visibilidad ────────────────────────────────────────────────────────

    /** Hace visible la figura. */
    public void makeVisible() {
        isVisible = true;
        draw();
    }

    /** Hace invisible la figura. */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    // ─── Movimiento ─────────────────────────────────────────────────────────

    /** Mueve la figura 20 px a la derecha. */
    public void moveRight()  { moveHorizontal(20);  }

    /** Mueve la figura 20 px a la izquierda. */
    public void moveLeft()   { moveHorizontal(-20); }

    /** Mueve la figura 20 px hacia arriba. */
    public void moveUp()     { moveVertical(-20);   }

    /** Mueve la figura 20 px hacia abajo. */
    public void moveDown()   { moveVertical(20);    }

    /**
     * Mueve la figura horizontalmente la distancia indicada.
     * @param distance distancia en píxeles (puede ser negativa)
     */
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Mueve la figura verticalmente la distancia indicada.
     * @param distance distancia en píxeles (puede ser negativa)
     */
    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Mueve la figura horizontalmente de forma animada (paso a paso).
     * @param distance distancia en píxeles
     */
    public void slowMoveHorizontal(int distance) {
        int delta = (distance < 0) ? -1 : 1;
        distance = Math.abs(distance);
        for (int i = 0; i < distance; i++) {
            xPosition += delta;
            draw();
        }
    }

    /**
     * Mueve la figura verticalmente de forma animada (paso a paso).
     * @param distance distancia en píxeles
     */
    public void slowMoveVertical(int distance) {
        int delta = (distance < 0) ? -1 : 1;
        distance = Math.abs(distance);
        for (int i = 0; i < distance; i++) {
            yPosition += delta;
            draw();
        }
    }

    // ─── Color ──────────────────────────────────────────────────────────────

    /**
     * Cambia el color de la figura.
     * @param newColor color nuevo ("red","yellow","blue","green","magenta","black","white")
     */
    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }

    // ─── Dibujo (template method) ────────────────────────────────────────────

    /**
     * Dibuja la figura en el canvas.
     * Cada subclase define la forma geométrica concreta.
     */
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, getShape());
            canvas.wait(10);
        }
    }

    /**
     * Borra la figura del canvas.
     */
    protected void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }

    /**
     * Retorna la forma geométrica AWT concreta que representa esta figura.
     * Cada subclase debe implementar este método (Template Method Pattern).
     * @return objeto java.awt.Shape listo para dibujar
     */
    protected abstract java.awt.Shape getShape();
}
