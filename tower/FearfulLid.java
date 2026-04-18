package tower;

/**
 * Clase FearfulLid - Tapa de tipo temerosa.
 * Comportamiento especial:
 * - No puede entrar a la torre si su taza compañera no está en ella.
 * - No puede salir si está actualmente tapando a su taza compañera.
 * Se distingue visualmente con un color rayado (representado con prefijo "striped").
 *
 * Proyecto: stackingItems - Ciclo 4
 * Autores: GL
 */
public class FearfulLid extends Lid
{
    private int companionCupNumber;

    /**
     * Crea una tapa fearful asociada a una taza compañera.
     * @param color             color de la tapa
     * @param companionCupNumber número de la taza compañera
     */
    public FearfulLid(String color, int companionCupNumber)
    {
        super(color);
        this.companionCupNumber = companionCupNumber;
    }

    @Override
    public String getType() { return "fearful"; }

    /**
     * Solo puede entrar si su taza compañera está en la torre.
     * @param tower     la torre
     * @param cupNumber número de la taza donde se intenta colocar
     * @return true si la taza compañera está en la torre
     */
    @Override
    public boolean canPush(Tower tower, int cupNumber)
    {
        return tower.containsCup(companionCupNumber);
    }

    /**
     * No puede salir si está tapando a su taza compañera.
     * @param tower     la torre
     * @param cupNumber número de la taza que está tapando actualmente
     * @return false si cupNumber es su taza compañera, true en otro caso
     */
    @Override
    public boolean canPop(Tower tower, int cupNumber)
    {
        return cupNumber != companionCupNumber;
    }

    /**
     * Retorna el número de la taza compañera.
     * @return número de taza compañera
     */
    public int getCompanionCupNumber() { return companionCupNumber; }
}
