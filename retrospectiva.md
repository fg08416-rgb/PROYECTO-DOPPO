# Documento de Retrospectiva
## Francisco Javier Gomez Rubiano - Oscar Daniel Lopez Cruz
### Proyecto: StackingItems - Todos los Ciclos

# Ciclo 1 — Construcción del Simulador

## 1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.

Se definieron 3 mini-ciclos:
- **Mini-ciclo 1:** Construcción de la torre y gestión básica de tazas (pushCup, popCup, removeCup). Se priorizó porque es la base del simulador.
- **Mini-ciclo 2:** Gestión de tapas (pushLid, popLid, removeLid) y vinculación taza-tapa. Se separó porque depende del mini-ciclo anterior.
- **Mini-ciclo 3:** Reorganización de la torre (orderTower, reverseTower) y consulta de información (height, lidedCups, stackingItems). Se dejó al final por ser funcionalidades secundarias.

## 2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿Por qué?

Al finalizar el ciclo 1, los 3 mini-ciclos fueron completados satisfactoriamente. La torre permite crear, gestionar tazas y tapas, reorganizarse y consultar su estado. Se logró el modo invisible requerido para las pruebas.

## 3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)

- **Francisco Javier Gomez Rubiano:** 14 horas
- **Oscar Daniel Lopez Cruz:** 10 horas
- **Total:** 24 horas

## 4. ¿Cuál consideran fue el mayor logro? ¿Por qué?

El mayor logro fue implementar correctamente el vínculo entre taza y tapa, garantizando que ambas se movieran juntas al reorganizar la torre. Esto requirió un diseño cuidadoso de las estructuras de datos paralelas (arrays **cups** y **lids**).

## 5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?

El mayor problema fue el manejo visual de las figuras con el paquete **Shapes**, especialmente el cálculo de posiciones al redibujar la torre. Se resolvió creando el método **redraw()** centralizado que recalcula todas las posiciones desde la base.

## 6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?

Se distribuyó el trabajo por mini-ciclos de forma clara. Para mejorar, nos comprometemos a escribir pruebas de unidad desde el inicio del desarrollo (TDD) en lugar de al final.

## 7. Considerando las prácticas XP incluidas en los laboratorios, ¿cuál fue la más útil? ¿Por qué?

La práctica más útil fue el **diseño simple**: mantener la clase Tower con responsabilidades claras y delegar el dibujo a las clases Cup y Lid evitó código duplicado y facilitó la extensión posterior.

## 8. ¿Qué referencias usaron? ¿Cuál fue la más útil? Incluyan citas con estándares adecuados.

- Barnes, D. J., & Kölling, M. (2016). Objects First with Java: A Practical Introduction Using BlueJ (6th ed.). Pearson.
- Oracle. (2024). Java SE Documentation. https://docs.oracle.com/en/java/
- Bloch, J. (2018). Effective Java (3rd ed.). Addison-Wesley.

La más útil fue la documentación oficial de Java para entender el manejo de **java.awt.Shape** en el paquete **Shapes**.


# Ciclo 2 — Refactoring y Extensión (swap, cover, swapToReduce)

## 1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.

- **Mini-ciclo 1:** Implementación de **Tower(int cups)** y **swap()**. Se priorizó porque son la base de los nuevos requisitos.
- **Mini-ciclo 2:** Implementación de **cover()** y **swapToReduce()**. Se separó porque depende de swap.
- **Mini-ciclo 3:** Pruebas de unidad TowerC2Test y pruebas de aceptación.

## 2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿Por qué?

Los 3 mini-ciclos fueron completados. El método **swapToReduce()** fue el más complejo ya que requirió simular intercambios sin modificar el estado real de la torre.

## 3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)

- **Francisco Javier Gomez Rubiano:** 12 horas
- **Oscar Daniel Lopez Cruz:** 10 horas
- **Total:** 22 horas

## 4. ¿Cuál consideran fue el mayor logro? ¿Por qué?

El mayor logro fue implementar **swapToReduce()** correctamente, que requiere simular intercambios y calcular la altura resultante sin afectar el estado de la torre.

## 5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?

El mayor problema fue el método **swap()** cuando involucra tapas y tazas de forma mixta. Se resolvió identificando claramente los índices de cada objeto y manejando los cuatro casos posibles (cup-cup, lid-lid, cup-lid, lid-cup).

## 6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?

Se comunicó bien la separación de responsabilidades. Nos comprometemos a documentar mejor los métodos privados para facilitar el mantenimiento.

## 7. Considerando las prácticas XP incluidas en los laboratorios, ¿cuál fue la más útil? ¿Por qué?

La práctica más útil fue la **refactorización continua**: al agregar **swap()**, se identificó código duplicado en los métodos de ordenamiento y se extrajo en métodos auxiliares privados.

## 8. ¿Qué referencias usaron? ¿Cuál fue la más útil?

- Barnes, D. J., & Kölling, M. (2016). Objects First with Java (6th ed.). Pearson.
- Fowler, M. (2018). Refactoring: Improving the Design of Existing Code (2nd ed.). Addison-Wesley.

La más útil fue el libro de Fowler para identificar los patrones de refactorización aplicados.

# Ciclo 3 — Resolución del Problema de la Maratón (TowerContest)

## 1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.

- **Mini-ciclo 1:** Análisis del problema de la maratón y diseño del algoritmo **solve()**.
- **Mini-ciclo 2:** Implementación de **solve()** y **simulate()**.
- **Mini-ciclo 3:** Pruebas de unidad TowerContestTest.

## 2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿Por qué?

Los 3 mini-ciclos fueron completados. El algoritmo **solve()** maneja correctamente los casos imposibles (alturas pares, fuera de rango) y encuentra permutaciones válidas para los casos posibles.

## 3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)

- **Francisco Javier Gomez Rubiano:** 16 horas
- **Oscar Daniel Lopez Cruz:** 14 horas
- **Total:** 30 horas

## 4. ¿Cuál consideran fue el mayor logro? ¿Por qué?

El mayor logro fue resolver correctamente el problema de la maratón, identificando que solo las alturas impares son alcanzables y diseñando un algoritmo eficiente para encontrar la permutación correcta.

## 5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?

El mayor problema fue entender la fórmula de altura acumulada del problema de la maratón. Se resolvió analizando ejemplos manualmente y verificando con los casos de prueba provistos.

## 6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?

Se separó claramente la lógica de resolución (TowerContest) de la simulación visual (Tower), siguiendo el requisito del enunciado. Nos comprometemos a mejorar la cobertura de pruebas.

## 7. Considerando las prácticas XP incluidas en los laboratorios, ¿cuál fue la más útil? ¿Por qué?

La práctica más útil fue **Test-Driven Development (TDD)**: escribir primero los casos de prueba del problema de la maratón ayudó a entender mejor los requisitos antes de implementar.

## 8. ¿Qué referencias usaron? ¿Cuál fue la más útil?

- ICPC. (2025). Problem J: Stacking Cups - ICPC World Finals 2025. https://icpc.global
- Sedgewick, R., & Wayne, K. (2011). Algorithms (4th ed.). Addison-Wesley.

La más útil fue el enunciado original del problema de la maratón para entender exactamente los casos de entrada y salida esperados.

# Ciclo 4 — Refactoring y Extensión con Tipos (NormalCup, OpenerCup, HierarchicalCup, etc.)

## 1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.

- **Mini-ciclo 1:** Refactorización de Cup y Lid como clases abstractas con NormalCup y NormalLid.
- **Mini-ciclo 2:** Implementación de OpenerCup, HierarchicalCup, FearfulLid y CrazyLid.
- **Mini-ciclo 3:** Implementación del nuevo tipo propuesto (TimedLid) y pruebas.

## 2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿Por qué?

Los 3 mini-ciclos fueron completados. Se propuso e implementó **TimedLid** como nuevo tipo de tapa que se autoelimica después de un número de operaciones definido.

## 3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)

- **Francisco Javier Gomez Rubiano:** 16 horas
- **Oscar Daniel Lopez Cruz:** 16 horas
- **Total:** 32 horas

## 4. ¿Cuál consideran fue el mayor logro? ¿Por qué?

El mayor logro fue la refactorización a jerarquías de herencia para Cup y Lid, que permitió agregar los nuevos tipos sin modificar la clase Tower, demostrando el principio Open/Closed.

## 5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?

El mayor problema fue implementar HierarchicalCup correctamente, ya que debe insertarse en la posición correcta de la torre desplazando elementos. Se resolvió con el método **insertHierarchical()** que calcula la posición y desplaza el arreglo.

## 6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?

Se diseñó bien la jerarquía de clases usando polimorfismo. Nos comprometemos a mejorar la documentación de los métodos.

## 7. Considerando las prácticas XP incluidas en los laboratorios, ¿cuál fue la más útil? ¿Por qué?

La práctica más útil fue el **Test-Driven Development (TDD)**: escribir primero los casos de prueba y despues el codigo

## 8. ¿Qué referencias usaron? ¿Cuál fue la más útil?

- Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). Design Patterns: Elements of Reusable Object-Oriented Software. Addison-Wesley.
- Barnes, D. J., & Kölling, M. (2016). Objects First with Java (6th ed.). Pearson.

La más útil fue el libro de Design Patterns para aplicar correctamente el patrón Strategy en los tipos de tazas y tapas.

# Ciclo Final — Cierre (Eclipse, Análisis Dinámico y Estático)

## 1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.

- **Mini-ciclo 1:** Migración del proyecto de BlueJ a Eclipse y configuración del entorno.
- **Mini-ciclo 2:** Análisis dinámico con EclEmma y mejora de cobertura de pruebas.
- **Mini-ciclo 3:** Análisis estático con PMD y corrección de violaciones críticas.

## 2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿Por qué?

Los 3 mini-ciclos fueron completados. Se logró una cobertura del 90,3% (superando la meta del 90%) y se corrigieron todas las violaciones críticas de PMD.

## 3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)

- **Francisco Javier Gomez Rubiano:** 14 horas
- **Oscar Daniel Lopez Cruz:** 10 horas
- **Total:** 24 horas

## 4. ¿Cuál consideran fue el mayor logro? ¿Por qué?

El mayor logro fue alcanzar el 90,3% de cobertura de pruebas, superando la meta del 90%, y eliminar todas las violaciones críticas de PMD. Esto demuestra la calidad del código desarrollado a lo largo de los ciclos.

## 5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?

El mayor problema técnico fue configurar PMD en Eclipse, ya que los plugins disponibles no mostraban resultados. Se resolvió usando PMD 7.23.0 directamente desde la línea de comandos, generando reportes HTML y de texto de manera exitosa.

## 6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?

Se trabajó de forma organizada para identificar las clases con menor cobertura y agregar pruebas específicas. Para futuros proyectos nos comprometemos a usar Eclipse desde el inicio para aprovechar las herramientas de análisis desde el primer ciclo.

## 8. ¿Qué referencias usaron? ¿Cuál fue la más útil?

- PMD. (2026). PMD 7.23.0 Documentation. https://pmd.github.io/pmd/
- JaCoCo. (2024). EclEmma - Java Code Coverage for Eclipse. https://www.eclemma.org/
- Martin, R. C. (2008). Clean Code: A Handbook of Agile Software Craftsmanship. Prentice Hall.

La más útil fue la documentación de PMD para entender las reglas de prioridad alta y cómo corregirlas correctamente.
