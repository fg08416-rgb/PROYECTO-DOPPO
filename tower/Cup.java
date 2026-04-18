package tower;

import Shapes.Rectangle;

/**
 * Clase abstracta Cup - Representa una taza en la torre de tazas.
 * Define el contrato común para todos los tipos de taza.
 * Subclases: NormalCup, OpenerCup, HierarchicalCup.
 *
 * Proyecto: stackingItems - Ciclo 4
 * Autores: GL
 */
public abstract class Cup
{
    public static final int SCALE    = 20;
    public static final int CUP_STEP = 25;
    public static final int WALL_W   = 12;
    public static final int BASE_H   =  8;
    public static final int BORN_X   = 70;
    public static final int BORN_Y   = 15;

    protected int     number;
    protected String  color;
    protected boolean visible;

    protected Rectangle leftWall;
    protected Rectangle rightWall;
    protected Rectangle baseRect;

    protected int lwX, lwY;
    protected int rwX, rwY;
    protected int brX, brY;

    /**
     * Constructor base para una taza.
     * @param number número de la taza (define su tamaño y color)
     */
    public Cup(int number)
    {
        this.number    = number;
        this.color     = colorFor(number);
        this.visible   = false;
        this.leftWall  = new Rectangle();
        this.rightWall = new Rectangle();
        this.baseRect  = new Rectangle();
        this.lwX = BORN_X; this.lwY = BORN_Y;
        this.rwX = BORN_X; this.rwY = BORN_Y;
        this.brX = BORN_X; this.brY = BORN_Y;
    }

    // ─── Getters ─────────────────────────────────────────────────

    public int    getNumber()   { return number; }
    public String getColor()    { return color; }
    public int    getHeight()   { return 2 * number - 1; }
    public int    getHeightPx() { return getHeight() * SCALE; }
    public int    getWidthPx()  { return number * CUP_STEP; }
    public boolean isVisible()  { return visible; }

    /**
     * Retorna el tipo de taza como String.
     * Cada subclase debe implementar este método.
     * @return "normal", "opener" o "hierarchical"
     */
    public abstract String getType();

    /**
     * Comportamiento especial al entrar a la torre.
     * Cada subclase define su lógica de inserción.
     * @param tower la torre donde se inserta
     */
    public abstract void onPush(Tower tower);

    /**
     * Comportamiento especial al intentar quitar la taza.
     * @param tower la torre donde está
     * @return true si se permite quitar, false si no
     */
    public abstract boolean onPop(Tower tower);

    // ─── Dibujo ──────────────────────────────────────────────────

    public void draw(int centerX, int bottomY)
    {
        int totalW = getWidthPx();
        int totalH = getHeightPx();
        int wallH  = totalH - BASE_H;
        int leftX  = centerX - totalW / 2;
        int topY   = bottomY - totalH;

        leftWall.makeInvisible();
        leftWall.changeSize(wallH, WALL_W);
        leftWall.changeColor(color);

        rightWall.makeInvisible();
        rightWall.changeSize(wallH, WALL_W);
        rightWall.changeColor(color);

        baseRect.makeInvisible();
        baseRect.changeSize(BASE_H, totalW);
        baseRect.changeColor(color);

        leftWall.moveHorizontal(leftX - lwX);
        leftWall.moveVertical(topY - lwY);
        lwX = leftX; lwY = topY;

        int rwTargetX = leftX + totalW - WALL_W;
        rightWall.moveHorizontal(rwTargetX - rwX);
        rightWall.moveVertical(topY - rwY);
        rwX = rwTargetX; rwY = topY;

        int brTargetY = topY + wallH;
        baseRect.moveHorizontal(leftX - brX);
        baseRect.moveVertical(brTargetY - brY);
        brX = leftX; brY = brTargetY;

        leftWall.makeVisible();
        rightWall.makeVisible();
        baseRect.makeVisible();
        visible = true;
    }

    public void undraw()
    {
        leftWall.makeInvisible();
        rightWall.makeInvisible();
        baseRect.makeInvisible();
        visible = false;
    }

    //  Color por número 

    private String colorFor(int n)
    {
        switch (n % 6) {
            case 1:  return "cyan";
            case 2:  return "green";
            case 3:  return "red";
            case 4:  return "blue";
            case 5:  return "yellow";
            case 0:  return "magenta";
            default: return "blue";
        }
    }

    @Override
    public String toString()
    {
        return getType() + "Cup #" + number +
               " (h=" + getHeight() + "cm, color=" + color + ")";
    }
}
