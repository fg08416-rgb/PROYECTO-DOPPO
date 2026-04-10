package Tower;

/**
 * Clase NormalLid - Tapa de tipo normal.
 * No tiene restricciones especiales para entrar o salir.
 * Es el tipo de tapa original del proyecto.
 *
 * Proyecto: stackingItems - Ciclo 4
 * Autores: GL
 */
public class NormalLid extends Lid
{
    /**
     * Crea una tapa normal con el color dado.
     * @param color color de la tapa
     */
    public NormalLid(String color)
    {
        super(color);
    }

    @Override
    public String getType() { return "normal"; }

    /** La tapa normal siempre puede entrar. */
    @Override
    public boolean canPush(Tower tower, int cupNumber) { return true; }

    /** La tapa normal siempre puede salir. */
    @Override
    public boolean canPop(Tower tower, int cupNumber) { return true; }
}
