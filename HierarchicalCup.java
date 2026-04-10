package Tower;

/**
 * Clase HierarchicalCup - Taza de tipo jerárquico.
 * Comportamiento especial:
 * - Al entrar a la torre, desplaza hacia arriba todos los objetos
 *   (tazas y tapas) de menor tamaño que ella para insertarse debajo de ellos.
 * - Si logra llegar al fondo de la torre (posición 0), no se puede quitar.
 * Se distingue visualmente por su color dorado/especial.
 *
 * Proyecto: stackingItems - Ciclo 4
 * Autores: GL
 */
public class HierarchicalCup extends Cup
{
    private boolean reachedBottom;

    /**
     * Crea una taza hierarchical con el número dado.
     * @param number número de la taza
     */
    public HierarchicalCup(int number)
    {
        super(number);
        this.reachedBottom = false;
        this.color = "white"; 
    }

    @Override
    public String getType() { return "hierarchical"; }

    /**
     * Al entrar a la torre, se inserta debajo de todas las tazas
     * más pequeñas (número menor), desplazándolas hacia arriba.
     * Tower llama este método para determinar la posición de inserción.
     * @param tower la torre donde se inserta
     */
    @Override
    public void onPush(Tower tower)
    {
        reachedBottom = tower.hierarchicalReachedBottom(this);
    }

    /**
     * Solo puede quitarse si no llegó al fondo de la torre.
     * @param tower la torre donde está
     * @return false si está en el fondo, true en caso contrario
     */
    @Override
    public boolean onPop(Tower tower)
    {
        if (reachedBottom) return false;
        return true;
    }

    /**
     * Indica si esta taza logró llegar al fondo de la torre.
     * @return true si está bloqueada en el fondo
     */
    public boolean isReachedBottom() { return reachedBottom; }

    /**
     * Marca que esta taza llegó al fondo (llamado por Tower).
     */
    public void setReachedBottom(boolean value) { this.reachedBottom = value; }
}
