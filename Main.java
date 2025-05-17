import java.util.Scanner;
// Clase que sirve para crear los nodos del árbol AVL
class Node {
    int valor;           // Aquí guardamos el número que se va a insertar
    int altura;          // Altura del nodo (sirve para saber si el árbol está balanceado)
    Node izquierda;      // Referencia al hijo izquierdo
    Node derecha;        // Referencia al hijo derecho

    // Constructor para crear un nodo nuevo con su valor
    public Node(int valor) {
        this.valor = valor;
        this.altura = 1; // Cuando el nodo se crea, su altura es 1 (porque no tiene hijos todavía)
        this.izquierda = null;
        this.derecha = null;
    }
}

// Clase que es donde vamos a trabajar el árbol AVL completo
class AVLTree {
    Node raiz; // Aquí guardamos la raíz del árbol

    // Constructor: al inicio el árbol está vacío
    public AVLTree() {
        this.raiz = null;
    }

    // Este método se encarga de insertar un número en el árbol y mantenerlo balanceado
    public Node insertar(Node nodo, int valor) {
        // Paso 1: inserción normal de un BST (árbol binario de búsqueda)
        if (nodo == null) {
            return new Node(valor); // Si el nodo está vacío, lo insertamos aquí
        }

        if (valor < nodo.valor) {
            nodo.izquierda = insertar(nodo.izquierda, valor); // Insertamos en la izquierda si es menor
        } else if (valor > nodo.valor) {
            nodo.derecha = insertar(nodo.derecha, valor); // Insertamos en la derecha si es mayor
        } else {
            return nodo; // Si el valor ya existe, no lo insertamos de nuevo
        }

        // Paso 2: actualizar la altura del nodo actual
        nodo.altura = 1 + Math.max(getAltura(nodo.izquierda), getAltura(nodo.derecha));

        // Paso 3: calcular el factor de balance para ver si hay que rotar
        int balance = getFactorBalance(nodo);

        // Paso 4: aplicar rotaciones si el árbol se desbalanceó

        // Rotación simple a la derecha (caso izquierda-izquierda)
        if (balance > 1 && valor < nodo.izquierda.valor) {
            return rotarDerecha(nodo);
        }

        // Rotación simple a la izquierda (caso derecha - derecha)
        if (balance < -1 && valor > nodo.derecha.valor) {
            return rotarIzquierda(nodo);
        }

        // Rotación doble izquierda-derecha (caso izquierda-derecha)
        if (balance > 1 && valor > nodo.izquierda.valor) {
            nodo.izquierda = rotarIzquierda(nodo.izquierda);
            return rotarDerecha(nodo);
        }

        // Rotación doble derecha-izquierda (caso derecha - izquierda)
        if (balance < -1 && valor < nodo.derecha.valor) {
            nodo.derecha = rotarDerecha(nodo.derecha);
            return rotarIzquierda(nodo);
        }
        // Si no hubo desbalance, simplemente devolvemos el nodo
        return nodo;
    }

    // Este método obtiene la altura de un nodo (si está vacío, es 0)
    public int getAltura(Node nodo) {
        if (nodo == null) return 0;
        return nodo.altura;
    }

    // Este método calcula el factor de balance de un nodo (altura izquierda - altura derecha)
    public int getFactorBalance(Node nodo) {
        if (nodo == null) return 0;
        return getAltura(nodo.izquierda) - getAltura(nodo.derecha);
    }

    // Rotación simple a la derecha
    public Node rotarDerecha(Node y) {
        Node x = y.izquierda;
        Node T2 = x.derecha;
        // Hacemos la rotación
        x.derecha = y;
        y.izquierda = T2;
        // Actualizamos las alturas
        y.altura = 1 + Math.max(getAltura(y.izquierda), getAltura(y.derecha));
        x.altura = 1 + Math.max(getAltura(x.izquierda), getAltura(x.derecha));
        // Retornamos la nueva raíz después de rotar
        return x;
    }

    // Rotación simple a la izquierda
    public Node rotarIzquierda(Node x) {
        Node y = x.derecha;
        Node T2 = y.izquierda;

        // Hacemos la rotación
        y.izquierda = x;
        x.derecha = T2;

        // Actualizamos las alturas
        x.altura = 1 + Math.max(getAltura(x.izquierda), getAltura(x.derecha));
        y.altura = 1 + Math.max(getAltura(y.izquierda), getAltura(y.derecha));

        // Retornamos la nueva raíz después de rotar
        return y;
    }

    // Esta función sirve para imprimir el árbol visualmente con espacios según el nivel
    public void printTree() {
        printTreeRec(raiz, 0);
    }

    // Esta función recorre el árbol y lo imprime de forma jerárquica con sangría
    private void printTreeRec(Node nodo, int espacio) {
        int GAP = 6;

        if (nodo == null)
            return;
        espacio += GAP;

        // Primero imprimimos el hijo derecho para que se vea arriba
        printTreeRec(nodo.derecha, espacio);

        // Ahora imprimimos este nodo con espacios
        System.out.println();
        for (int i = GAP; i < espacio; i++)
            System.out.print(" ");
        System.out.println(nodo.valor);

        // Luego imprimimos el hijo izquierdo para que se vea abajo
        printTreeRec(nodo.izquierda, espacio);
    }

    // Esta función la usamos para llamar desde Main y mostrar el árbol
    public void mostrarArbol() {
        System.out.println("\nÁrbol actual:");
        printTree();
    }
}

// Esta es la clase principal donde ejecutamos todo
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Para leer datos desde consola
        AVLTree arbol = new AVLTree();       // Creamos el árbol AVL

        System.out.println("Bienvenido. Escribe números para insertarlos al árbol AVL.");
        System.out.println("Escribe 'exit' o -1 para terminar.");

        while (true) {
            System.out.print("Ingresa un número: ");
            String entrada = sc.nextLine();

            // Si el usuario quiere salir
            if (entrada.equalsIgnoreCase("exit") || entrada.equals("-1")) {
                System.out.println("Programa finalizado.");
                break;
            }

            try {
                int numero = Integer.parseInt(entrada); // Convertimos la entrada a número
                arbol.raiz = arbol.insertar(arbol.raiz, numero); // Insertamos en el árbol y actualizamos la raíz
                arbol.mostrarArbol(); // Mostramos el árbol actualizado
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingresa un número válido.");
            }
        }

        sc.close();
    }
}