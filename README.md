# Entrega-5_MarcoSoloj
# Implementación Árboles AVL en Scala (Paradigma Funcional)

Este repositorio contiene la implementación inmutable de un árbol AVL (búsqueda y ordenamiento avanzado), desarrollada en el marco del curso de Algoritmos y Estructura de Datos (Fase 5 de Investigación de Lenguajes). 

## Requisitos Previos

Para ejecutar el código necesitarás:
* [Scala 3](https://www.scala-lang.org/download/) (o Scala 2.13) instalado localmente.
* **Visual Studio Code** configurado.
* Extensión **Metals** instalada en Visual Studio Code para habilitar el soporte completo del lenguaje Scala.

## Estructura del Código
* `sealed trait AVLTree[+A]`: Define la jerarquía inmutable de los datos.
* `object AVLTree`: Agrupa la lógica pura funcional del árbol (cálculo de altura, balanceo, rotaciones algorítmicas O(1), inserción inmutable y búsqueda).
* `object Main`: Punto de entrada con tres casos de prueba diseñados (inserciones que fuerzan el balanceo, pruebas de éxito/fallo en búsquedas y manejo de colisiones por duplicados).

## Instrucciones para Ejecutar en VS Code con Metals

1. Abre la carpeta del proyecto en Visual Studio Code.
2. Asegúrate de tener el archivo `AVLTree.scala` cargado en tu entorno y deja que Metals inicialice el servidor de compilación de Scala (SBT o Bloop). Deberías ver el indicador de Metals activo en la barra inferior de VS Code.
3. Ubícate dentro de `object Main extends App`.
4. En el editor, Metals colocará un botón virtual con la leyenda **`run`** o **`debug`** justo encima de `object Main`. Haz clic en **`run`**.
5. Revisa la terminal integrada en Visual Studio Code, donde verás los resultados de los 3 casos de prueba en consola, incluyendo las validaciones del comportamiento del árbol AVL.