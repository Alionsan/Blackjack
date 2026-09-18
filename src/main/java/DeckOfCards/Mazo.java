package DeckOfCards;
/**
 * Write a description of class Mazo here.
 *
 * @author (Cecilia Curlango Rosas)
 * @version (2025-2)
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import pila.Pila;

public class Mazo {
    private Pila<CartaInglesa> cartas;

    public Mazo() {
        cartas = new Pila<>(52);

    }

    public CartaInglesa obtenerUnaCarta(){
        if (!cartas.pilaVacia()){
            return cartas.pop();
        }
        return null;
    }

    private void llenarYMezclar(){
        CartaInglesa[] arregloTemporal = new CartaInglesa[52];
        int indice = 0;

        for(int i = 2; i<= 14; i++){
            for(Palo palo : Palo.values()){
                arregloTemporal[indice] = new CartaInglesa(i, palo, palo.getColor());
                indice++;
            }
        }

        Random rand = new Random();
        for(int i = arregloTemporal.length - 1; i > 0; i--){
            int j = rand.nextInt(i+1);
            CartaInglesa temp = arregloTemporal[i];
            arregloTemporal[i] = arregloTemporal[j];
            arregloTemporal[j] = temp;
        }

        for(CartaInglesa c : arregloTemporal){
            cartas.push(c);
        }
    }

    /**
     * Obtiene todas las cartas del mazo.
     * @return
     */
    public ArrayList<CartaInglesa> getCartas() {
        return cartas;
    }

    private void mezclar() {
        Collections.shuffle(cartas);
    }

    private void llenar() {
        for (int i = 2; i <=14 ; i++) {
            for (Palo palo : Palo.values()) {
                CartaInglesa c = new CartaInglesa(i,palo, palo.getColor());
                cartas.add(c);
            }
        }
    }

    public void ordenar() {
        Collections.sort(cartas);
    }

    @Override
    public String toString() {
        return cartas.toString();
    }
}
