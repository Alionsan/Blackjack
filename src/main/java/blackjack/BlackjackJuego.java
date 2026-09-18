package blackjack;

import DeckOfCards.CartaInglesa;
import DeckOfCards.Mazo;
import pila.Pila;

public class BlackjackJuego {

    public enum Estado{
        EN_CURSO,
        JUGADOR_GANA,
        JUGADOR_GANA_BLACKJACK,
        CRUPIER_GANA,
        EMPATE
    }

    private Mazo mazo;
    private Estado estado;
    private Mano manoJugador;
    private Mano manoCrupier;
    private Pila<String> historialMovimientos;

    public void nuevaRonda(){
        mazo = new Mazo();
        manoJugador = new Mano();
        manoCrupier = new Mano();
        historialMovimientos = new Pila<>(20);
        estado = Estado.EN_CURSO;

        repartirAlJugador();
        repartirAlCrupiar(false);
        repartirAlJugador();
        repartirAlCrupiar(true);

        verifciarBlackjackInicial();
    }

    private void repartirAlJugador(){
        CartaInglesa carta = mazo.obtenerUnaCarta();
        carta.makeFaceUp();
        manoJugador.agregarCarta(carta);
    }

    private void repartirAlCrupiar(boolean bocaArriba){
        CartaInglesa carta = mazo.obtenerUnaCarta();
        if(bocaArriba){
            carta.makeFaceUp();
        }
        manoCrupier.agregarCarta(carta);
    }

    private void verifciarBlackjackInicial(){
        boolean jugadorBlackjack = manoJugador.esBlackjack();
        boolean crupierBlackjack = manoCrupier.esBlackjack();

        if(jugadorBlackjack || crupierBlackjack){
            manoCrupier.voltearTodas();
            if(jugadorBlackjack && crupierBlackjack){
                estado = Estado.EMPATE;
            } else if (jugadorBlackjack) {
                estado = Estado.JUGADOR_GANA_BLACKJACK;
            }else {
                estado = Estado.CRUPIER_GANA;
            }
        }
    }

    public void pedirCarta(){
        if(estado != Estado.EN_CURSO || manoJugador.calcularValor() >= 21){
            return;
        }

        historialMovimientos.push("ROBO");

        CartaInglesa carta = mazo.obtenerUnaCarta();
        carta.makeFaceUp();
        manoJugador.agregarCarta(carta);

        if(manoJugador.sePaso()){
            manoCrupier.voltearTodas();
            estado = Estado.CRUPIER_GANA;
        }
    }

    public void deshacerUltimoMovimiento(){
        if(historialMovimientos.pilaVacia()){
            return;
        }

        String ultimoMovimiento = historialMovimientos.pop();

        if(ultimoMovimiento.equals("ROBO")){
            int ultimaPosicion = manoJugador.getCartas().size()-1;
            CartaInglesa cartaDevuelva = manoJugador.getCartas().remove(ultimaPosicion);

            mazo.devolverCarta(cartaDevuelva);

            if(estado == Estado.CRUPIER_GANA){
                estado = Estado.EN_CURSO;
            }
        }
    }

    public void quedarse(){
        if (estado != Estado.EN_CURSO){
            return;
        }
        manoCrupier.voltearTodas();

        if (manoCrupier.calcularValor() < 17){
            CartaInglesa carta = mazo.obtenerUnaCarta();
            carta.makeFaceUp();
            manoCrupier.agregarCarta(carta);
        } else {
            determinarGanador();
        }
    }

    private void determinarGanador(){
        int valorJugador = manoJugador.calcularValor();
        int valorCrupier = manoCrupier.calcularValor();

        if (manoCrupier.sePaso()){
            estado = Estado.JUGADOR_GANA;
        } else if (valorJugador > valorCrupier) {
            estado = Estado.JUGADOR_GANA;
        } else if (valorJugador < valorCrupier) {
            estado = Estado.CRUPIER_GANA;
        } else{
            estado = Estado.EMPATE;
        }
    }

    public boolean puedeJugar(){
        return estado == Estado.EN_CURSO;
    }

    public Mano getManoJugador() {
        return manoJugador;
    }

    public  Mano getManoCrupier() {
        return  manoCrupier;
    }

    public  Estado getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        String texto = "Crupier: " + manoCrupier + "\n";
        texto += "Jugador: " + manoJugador + "\n";
        texto += "Estado: " + estado;
        return texto;
    }
}
