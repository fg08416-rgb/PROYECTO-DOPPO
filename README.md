# StackingItems — Simulador de Torre de Tazas

**Escuela Colombiana de Ingeniería Julio Garavito**  
Desarrollo Orientado por Objetos [DOPO-POOB] — 2026-1  
**Autores:** Francisco Javier Gomez Rubiano - Oscar Daniel Lopez Cruz

## Descripción del Proyecto

StackingItems es un simulador visual inspirado en el **Problem J de la maratón de programación internacional ICPC 2025 — Stacking Cups**. El simulador permite apilar tazas y tapas en una torre, reorganizarlas y resolver el problema matemático de encontrar una permutación de tazas que produzca una altura exacta.

El proyecto está organizado en dos paquetes:

- **tower** — Contiene la lógica principal del simulador: la torre, los tipos de tazas y tapas, y el solucionador del problema de la maratón.
- **Shapes** — Contiene las figuras geométricas usadas para la representación visual del simulador.

### Tipos de tazas soportados
- **NormalCup** — Taza estándar sin comportamiento especial.
- **OpenerCup** — Al entrar a la torre elimina la tapa que le impide el paso.
- **HierarchicalCup** — Se inserta debajo de las tazas más pequeñas; si llega al fondo no puede quitarse.

### Tipos de tapas soportados
- **NormalLid** — Tapa estándar sin restricciones.
- **FearfulLid** — No entra si su taza compañera no está en la torre; no sale si está tapando a su compañera.
- **CrazyLid** — En lugar de tapar su taza, se ubica en la base de la torre.
- **TimedLid** **(tipo nuevo propuesto)** — Se autoelimica después de un número definido de operaciones en la torre.

## Cómo ejecutarlo

### Requisitos
- Java JDK 11 o superior
- Eclipse IDE for Java Developers
- JUnit 5

### Pasos


1. En eclipse selecciona la carpeta raíz del proyecto y haz click en **Finish**.

2. Agrega JUnit 5 al classpath: click derecho sobre el proyecto > **Build Path > Add Library > JUnit 5**.

3. Para correr las pruebas: click derecho sobre la carpeta **tower** > **Run As > JUnit Test**.

4. Para usar el simulador visualmente, abre el **Bloc de Código** en Eclipse y ejecuta:
```java
Tower t = new Tower(4);
```


## Retrospectiva — Ciclo Final (Cierre 2026-1)

### 1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.

Se definieron 3 mini-ciclos:
- **Mini-ciclo 1:** Migración del proyecto de BlueJ a Eclipse y configuración del entorno.
- **Mini-ciclo 2:** Análisis dinámico con EclEmma y mejora de cobertura de pruebas.
- **Mini-ciclo 3:** Análisis estático con PMD y corrección de violaciones críticas.

### 2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿Por qué?

Los 3 mini-ciclos fueron completados. Se logró una cobertura del 90,3% (superando la meta del 90%) y se corrigieron todas las violaciones críticas de PMD.

### 3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)

- **Francisco Javier Gomez Rubiano:** 14 horas
- **Oscar Daniel Lopez Cruz:** 10 horas
- **Total:** 24 horas

### 4. ¿Cuál consideran fue el mayor logro? ¿Por qué?

El mayor logro fue alcanzar el 90,3% de cobertura de pruebas, superando la meta del 90%, y eliminar todas las violaciones críticas de PMD. Esto demuestra la calidad del código desarrollado a lo largo de los ciclos.

### 5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?

El mayor problema técnico fue configurar PMD en Eclipse, ya que los plugins disponibles no mostraban resultados. Se resolvió usando PMD 7.23.0 directamente desde la línea de comandos, generando reportes HTML y de texto de manera exitosa.

### 6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?

Se trabajó de forma organizada para identificar las clases con menor cobertura y agregar pruebas específicas. Para futuros proyectos nos comprometemos a usar Eclipse desde el inicio para aprovechar las herramientas de análisis desde el primer ciclo.

### 7. Considerando las prácticas XP incluidas en los laboratorios, ¿cuál fue la más útil? ¿Por qué?

La práctica más útil fue la **integración continua**: correr las pruebas frecuentemente durante el desarrollo permitió detectar regresiones rápidamente al agregar nuevas funcionalidades.

### 8. ¿Qué referencias usaron? ¿Cuál fue la más útil?

- PMD. (2026). PMD 7.23.0 Documentation. https://pmd.github.io/pmd/
- JaCoCo. (2024). EclEmma - Java Code Coverage for Eclipse. https://www.eclemma.org/
- Martin, R. C. (2008). Clean Code: A Handbook of Agile Software Craftsmanship. Prentice Hall.

La más útil fue la documentación de PMD para entender las reglas de prioridad alta y cómo corregirlas correctamente.
