package Shapes;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Canvas es una clase para dibujar figuras gráficas sobre un lienzo.
 * Modificación del Canvas general para el ejemplo "shapes" de BlueJ.
 *
 * @author Bruce Quig
 * @author Michael Kolling (mik)
 * @version 1.6 (shapes)
 */
public class Canvas {

    private static Canvas canvasSingleton;

    /** Método factory para obtener la instancia única del canvas. */
    public static Canvas getCanvas() {
        if (canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Shapes Demo", 300, 300, Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    // ─── Parte de instancia ──────────────────────────────────────────────────

    private JFrame                          frame;
    private CanvasPane                      canvas;
    private Graphics2D                      graphic;
    private Color                           backgroundColour;
    private Image                           canvasImage;
    private List<Object>                    objects;
    private HashMap<Object, ShapeDescription> shapes;

    private Canvas(String title, int width, int height, Color bgColour) {
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
        objects = new ArrayList<Object>();
        shapes  = new HashMap<Object, ShapeDescription>();
    }

    public void setVisible(boolean visible) {
        if (graphic == null) {
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D) canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    public void draw(Object referenceObject, String color, java.awt.Shape shape) {
        objects.remove(referenceObject);
        objects.add(referenceObject);
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }

    public void erase(Object referenceObject) {
        objects.remove(referenceObject);
        shapes.remove(referenceObject);
        redraw();
    }

    public void setForegroundColor(String colorString) {
        if      (colorString.equals("red"))     graphic.setColor(Color.red);
        else if (colorString.equals("black"))   graphic.setColor(Color.black);
        else if (colorString.equals("blue"))    graphic.setColor(Color.blue);
        else if (colorString.equals("yellow"))  graphic.setColor(Color.yellow);
        else if (colorString.equals("green"))   graphic.setColor(Color.green);
        else if (colorString.equals("magenta")) graphic.setColor(Color.magenta);
        else if (colorString.equals("white"))   graphic.setColor(Color.white);
        else                                    graphic.setColor(Color.black);
    }

    public void wait(int milliseconds) {
        try { Thread.sleep(milliseconds); } catch (Exception e) { }
    }

    private void redraw() {
        erase();
        for (Iterator i = objects.iterator(); i.hasNext(); ) {
            shapes.get(i.next()).draw(graphic);
        }
        canvas.repaint();
    }

    private void erase() {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }

    // ─── Clases internas ────────────────────────────────────────────────────

    private class CanvasPane extends JPanel {
        public void paint(Graphics g) { g.drawImage(canvasImage, 0, 0, null); }
    }

    private class ShapeDescription {
        private java.awt.Shape shape;
        private String         colorString;

        public ShapeDescription(java.awt.Shape shape, String color) {
            this.shape       = shape;
            this.colorString = color;
        }

        public void draw(Graphics2D graphic) {
            setForegroundColor(colorString);
            graphic.draw(shape);
            graphic.fill(shape);
        }
    }
}
