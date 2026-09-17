package pila;

public class Pila<T> {
    private T[] pila;
    private int tope;

    public Pila(int tamaño) {
        this.pila = (T[]) new Object[tamaño];
        this.tope = -1;
    }

    public boolean pilaLlena() {
        return tope == pila.length - 1;
    }

    public boolean pilaVacia() {
        return tope == -1;
    }

    public void push(T elemento) {
        if (pilaLlena()) {
            System.out.println("Pila llena");
        } else {
            tope++;
            pila[tope] = elemento;
        }
    }

    public T peek() {
        if (pilaVacia()) {
            System.out.println("Pila vacia");
            return null;
        }
        return pila[tope];
    }

    public T pop() {
        if (pilaVacia()) {
            System.out.println("Pila vacia");
            return null;
        }
        T elementoSacado = pila[tope];
        pila[tope] = null;
        tope--;
        return elementoSacado;
    }

    @Override
    public String toString() {
        if (pilaVacia()) {
            return "Pila vacia";
        }

        String resultado = "[";

        for (int i = tope; i >= 0; i--) {
            resultado += pila[i];

            if (i > 0) {
                resultado += ", ";
            }
        }

        resultado += "]";

        return resultado;
    }

    public int getTope() {
        return tope;
    }

    public void setTope(int tope) {
        this.tope = tope;
    }

    public int getCapacidad() {
        return pila.length;
    }
}