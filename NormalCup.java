package Tower;

/**
 * Clase NormalCup - Taza de tipo normal.
 * No tiene comportamiento especial al entrar o salir de la torre.
 * Es el tipo de taza original del proyecto.
 *
 * Proyecto: stackingItems - Ciclo 4
 */
public class NormalCup extends Cup
{
    /**
     * Crea una taza normal con el número dado.
     * @param number número de la taza
     */
    public NormalCup(int number)
    {
        super(number);
    }

    @Override
    public String getType() { return "normal"; }

    /**
     * La taza normal no hace nada especial al entrar.
     * @param tower la torre donde se inserta
     */
    @Override
    public void onPush(Tower tower) {  }

    /**
     * La taza normal siempre puede ser quitada.
     * @param tower la torre donde está
     * @return true siempre
     */
    @Override
    public boolean onPop(Tower tower) { return true; }
}
