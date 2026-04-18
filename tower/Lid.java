package tower;

import Shapes.Rectangle;

/**
 * Clase abstracta Lid - Representa una tapa en la torre de tazas.
 * Define el contrato común para todos los tipos de tapa.
 * Subclases: NormalLid, FearfulLid, CrazyLid, TimedLid.
 *
 * Proyecto: stackingItems - Ciclo 4
 * Autores: GL
 */
public abstract class Lid
{
    public static final int LID_H = 1 * Cup.SCALE;

    protected String    color;
    protected boolean   visible;
    protected Rectangle rect;
    protected int curX, curY;

    /**
     * Constructor base para una tapa.
     * @param color color de la tapa
     */
    public Lid(String color)
    {
        this.color   = color;
        this.visible = false;
        this.rect    = new Rectangle();
        this.curX    = Cup.BORN_X;
        this.curY    = Cup.BORN_Y;
    }


    public String  getColor()  { return color; }
    public boolean isVisible() { return visible; }

    /**
     * Retorna el tipo de tapa como String.
     * @return "normal", "fearful", "crazy" o "timed"
     */
    public abstract String getType();

    /**
     * Lógica de si esta tapa puede entrar a la torre en la posición dada.
     * @param tower   la torre
     * @param cupNumber número de la taza compañera
     * @return true si puede entrar, false si no
     */
    public abstract boolean canPush(Tower tower, int cupNumber);

    /**
     * Lógica de si esta tapa puede salir de la torre.
     * @param tower   la torre
     * @param cupNumber número de la taza compañera
     * @return true si puede salir, false si no
     */
    public abstract boolean canPop(Tower tower, int cupNumber);

    /**
     * Notificación de que se realizó una operación en la torre.
     * Útil para tapas con comportamiento basado en conteo (como TimedLid).
     * @param tower la torre
     * @param cupNumber número de la taza compañera
     */
    public void onOperation(Tower tower, int cupNumber) { }

    

    public void draw(Cup cup, int centerX, int bottomY)
    {
        int w       = cup.getWidthPx();
        int leftX   = centerX - w / 2;
        int targetY = bottomY - cup.getHeightPx() - LID_H;

        rect.makeInvisible();
        rect.changeSize(LID_H, w);
        rect.changeColor(color);
        rect.moveHorizontal(leftX  - curX);
        rect.moveVertical(targetY  - curY);
        curX = leftX;
        curY = targetY;
        rect.makeVisible();
        visible = true;
    }

    public void undraw()
    {
        rect.makeInvisible();
        visible = false;
    }

    @Override
    public String toString()
    {
        return getType() + "Lid(color=" + color + ")";
    }
}
