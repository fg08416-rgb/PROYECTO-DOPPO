package tower;

/**
 * Clase OpenerCup - Taza de tipo opener (abridor).
 * Al entrar a la torre, elimina todas las tapas que le impiden el paso,
 * es decir, las tapas de las tazas que estén por encima de su posición
 * de inserción (en el tope de la torre).
 * Se distingue visualmente porque su color base se muestra con borde negro
 * (representado internamente con el prefijo "dark" en el color).
 *
 * Proyecto: stackingItems - Ciclo 4
 */
public class OpenerCup extends Cup
{
    /**
     * Crea una taza opener con el número dado.
     * @param number número de la taza
     */
    public OpenerCup(int number)
    {
        super(number);
        
        this.color = darkColorFor(number);
    }

    @Override
    public String getType() { return "opener"; }

    /**
     * Al entrar a la torre, elimina la tapa de la taza que está en el tope
     * (la que le impediría el paso si la hubiera).
     * Tower llama este método justo antes de insertar la taza.
     * @param tower la torre donde se inserta
     */
    @Override
    public void onPush(Tower tower)
    {
        
        tower.removeTopLidIfPresent();
    }

    /**
     * La taza opener siempre puede ser quitada.
     * @param tower la torre donde está
     * @return true siempre
     */
    @Override
    public boolean onPop(Tower tower) { return true; }

    /** Colores oscuros para distinguir la OpenerCup visualmente */
    private String darkColorFor(int n)
    {
        switch (n % 6) {
            case 1:  return "black";
            case 2:  return "darkGreen";
            case 3:  return "darkRed";
            case 4:  return "darkBlue";
            case 5:  return "orange";
            case 0:  return "darkMagenta";
            default: return "black";
        }
    }
}
