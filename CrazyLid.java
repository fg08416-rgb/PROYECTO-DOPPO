package Tower;

/**
 * Clase CrazyLid - Tapa de tipo loca.
 * Comportamiento especial:
 * - En lugar de tapar a su taza compañera, se ubica en la base de la torre
 *   (posición 0, debajo de todas las tazas).
 * Tower debe manejar esta lógica especial en pushLid().
 * Se distingue visualmente con un color especial (magenta brillante).
 *
 * Proyecto: stackingItems - Ciclo 4
 * Autores: GL
 */
public class CrazyLid extends Lid
{
    /**
     * Crea una tapa crazy con el color dado.
     * @param color color de la tapa
     */
    public CrazyLid(String color)
    {
        super(color);
        this.color = "magenta"; 
    }

    @Override
    public String getType() { return "crazy"; }

    /**
     * La tapa crazy siempre puede entrar; Tower la reubicará en la base.
     */
    @Override
    public boolean canPush(Tower tower, int cupNumber) { return true; }

    /**
     * La tapa crazy puede salir normalmente.
     */
    @Override
    public boolean canPop(Tower tower, int cupNumber) { return true; }

    /**
     * Indica que esta tapa debe colocarse en la base de la torre.
     * Tower consulta este método en pushLid() para determinar la posición.
     * @return true siempre (comportamiento definitorio de CrazyLid)
     */
    public boolean goesToBase() { return true; }
}
