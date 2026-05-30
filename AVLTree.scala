sealed trait AVLTree[+A]
case object Empty extends AVLTree[Nothing]
case class Node[A](value: A, height: Int, left: AVLTree[A], right: AVLTree[A]) extends AVLTree[A]

object AVLTree {
  // Retorna un árbol vacío
  def empty[A]: AVLTree[A] = Empty

  // Obtiene la altura de un nodo de forma segura
  def height[A](tree: AVLTree[A]): Int = tree match {
    case Empty => 0
    case Node(_, h, _, _) => h
  }

  // Calcula el factor de equilibrio (balance factor)
  def balanceFactor[A](tree: AVLTree[A]): Int = tree match {
    case Empty => 0
    case Node(_, _, left, right) => height(left) - height(right)
  }

  // Helper funcional para crear o actualizar un nodo y recalcular su altura
  private def createNode[A](value: A, left: AVLTree[A], right: AVLTree[A]): Node[A] = {
    Node(value, 1 + math.max(height(left), height(right)), left, right)
  }

  // Rotación Simple a la Derecha (LL)
  def rotateRight[A](y: Node[A]): Node[A] = y.left match {
    case x @ Node(_, _, t1, t2) =>
      createNode(x.value, t1, createNode(y.value, t2, y.right))
    case _ => y
  }

  // Rotación Simple a la Izquierda (RR)
  def rotateLeft[A](x: Node[A]): Node[A] = x.right match {
    case y @ Node(_, _, t2, t3) =>
      createNode(y.value, createNode(x.value, x.left, t2), t3)
    case _ => x
  }

  // Lógica principal para mantener el árbol balanceado
  def balance[A](node: Node[A]): Node[A] = {
    val bf = balanceFactor(node)
    
    // Desbalanceado hacia la izquierda (Left Heavy)
    if (bf > 1) {
      if (balanceFactor(node.left) < 0) {
        // Caso Doble LR
        val newLeft = node.left match {
          case n: Node[A] => rotateLeft(n)
          case _ => node.left
        }
        rotateRight(createNode(node.value, newLeft, node.right))
      } else {
        // Caso Simple LL
        rotateRight(node)
      }
    } 
    // Desbalanceado hacia la derecha (Right Heavy)
    else if (bf < -1) {
      if (balanceFactor(node.right) > 0) {
        // Caso Doble RL
        val newRight = node.right match {
          case n: Node[A] => rotateRight(n)
          case _ => node.right
        }
        rotateLeft(createNode(node.value, node.left, newRight))
      } else {
        // Caso Simple RR
        rotateLeft(node)
      }
    } 
    // Árbol ya balanceado
    else {
      node
    }
  }

  // Inserción inmutable que retorna un nuevo árbol balanceado
  def insert[A](tree: AVLTree[A], value: A)(implicit ord: Ordering[A]): Node[A] = tree match {
    case Empty => createNode(value, Empty, Empty)
    case node @ Node(v, _, left, right) =>
      if (ord.lt(value, v)) {
        balance(createNode(v, insert(left, value), right))
      } else if (ord.gt(value, v)) {
        balance(createNode(v, left, insert(right, value)))
      } else {
        // Manejo apropiado de casos borde: Si es duplicado, ignoramos la inserción y devolvemos el nodo actual
        node.asInstanceOf[Node[A]]
      }
  }

  // Búsqueda logarítmica O(log n)
  def search[A](tree: AVLTree[A], value: A)(implicit ord: Ordering[A]): Boolean = tree match {
    case Empty => false
    case Node(v, _, left, right) =>
      if (ord.equiv(value, v)) true
      else if (ord.lt(value, v)) search(left, value)
      else search(right, value)
  }
}

// =========================================================
// Ejecución y Casos de Prueba
// =========================================================
object Main extends App {
  println("--- Iniciando Pruebas de Implementacion Arbol AVL ---")
  
  // Caso 1: Inserción secuencial (Caso que degeneraría a O(n) en un BST normal)
  println("\n[Caso de Prueba 1] Insercion secuencial (1 al 5) para forzar rotaciones de balanceo:")
  var tree: AVLTree[Int] = AVLTree.empty
  for (i <- 1 to 5) {
    tree = AVLTree.insert(tree, i)
    println(s"-> Se inserto $i | Altura actual del arbol: ${AVLTree.height(tree)}")
  }
  
  // Caso 2: Búsqueda de elementos existentes e inexistentes
  println("\n[Caso de Prueba 2] Busquedas de valores en el arbol balanceado:")
  println(s" Existe el numero 3 en la estructura? ${AVLTree.search(tree, 3)}")
  println(s" Existe el numero 10 en la estructura? ${AVLTree.search(tree, 10)}")
  
  // Caso 3: Manejo de casos borde (inserción de un duplicado)
  println("\n[Caso de Prueba 3] Manejo de duplicados:")
  val treeDup = AVLTree.insert(tree, 3)
  println(s"-> Se intento insertar un duplicado (3).")
  println(s"Altura original: ${AVLTree.height(tree)} | Altura tras intentar duplicado: ${AVLTree.height(treeDup)}")
  println(s"Siguen siendo la misma instancia de altura en memoria? ${AVLTree.height(tree) == AVLTree.height(treeDup)}")
}