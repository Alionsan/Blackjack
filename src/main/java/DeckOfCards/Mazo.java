package DeckOfCards;

import java.util.Random;

import pila.Pila;

public class Mazo {
    private Pila<CartaInglesa> cartas;

    public Mazo() {
        cartas = new Pila<>(52);
        llenarYMezclar();
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

    public Pila<CartaInglesa> getCartas() {
        return cartas;
    }



    public void devolverCarta(CartaInglesa carta) {
        carta.makeFaceDown();

        int cartasRestantes = cartas.getTope() + 1;

        CartaInglesa[] arregloTemporal = new CartaInglesa[cartasRestantes + 1];

        for(int i = 0; i < cartasRestantes; i++){
            arregloTemporal[i] = cartas.pop();
        }

        arregloTemporal[cartasRestantes] = carta;

        Random rand = new Random();
        for (int i = arregloTemporal.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            CartaInglesa temp = arregloTemporal[i];
            arregloTemporal[i] = arregloTemporal[j];
            arregloTemporal[j] = temp;
        }

        for(CartaInglesa c : arregloTemporal){
            cartas.push(c);
        }
    }

    @Override
    public String toString() {
        return cartas.toString();
    }
}
