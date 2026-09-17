package blackjack;

import DeckOfCards.CartaInglesa;

import java.util.ArrayList;

public class Mano {
    private ArrayList<CartaInglesa> cartas = new ArrayList<>();

    public void agregarCarta(CartaInglesa carta) {
        cartas.add(carta);
    }

    public ArrayList<CartaInglesa> getCartas() {
        return cartas;
    }

    public int calcularValor() {
        return calcular(false);
    }

    public boolean esBlackjack(){
        return cartas.size() == 2 && calcularValor() == 21;
    }

    public boolean sePaso(){
        return calcularValor() > 21;
    }

    public void voltearTodas(){
        for(CartaInglesa carta : cartas){
            carta.makeFaceUp();
        }
    }

    public void limpiar() {
        cartas.clear();
    }



    private int calcular(boolean soloVisibles) {
        int total = 0;
        int ases = 0;
        for (CartaInglesa carta : cartas) {
            if (soloVisibles && !carta.isFaceup()) {
                continue;
            }
            int valor = carta.getValor();
            if (valor == 14) {
                ases++;
                total += 11;
            } else if (valor >= 11 && valor <= 13) {

                total += 10;
            } else {
                total += valor;
            }
        }
        while (total > 21 && ases > 0) {
            total -= 10;
            ases--;
        }
        return total;
    }

    @Override
    public String toString() {
        String texto = "";
        for (CartaInglesa carta : cartas) {
            texto += carta.toString() + " ";
        }
        return texto.trim();
    }

}
