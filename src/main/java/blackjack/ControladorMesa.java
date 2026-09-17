package blackjack;

import DeckOfCards.CartaInglesa;
import DeckOfCards.Mazo;

public class ControladorMesa {
    private BlackjackJuego juegoPrincipal;

    private Mazo mazoBots;
    private Mano manoJ2;
    private Mano manoJ3;
    private Mano manoJ4;

    private int turnoActual = 0;

    public ControladorMesa() {
        juegoPrincipal = new BlackjackJuego();
        manoJ2 = new Mano();
        manoJ3 = new Mano();
        manoJ4 = new Mano();
        iniciarNuevaRonda();
    }

    public void iniciarNuevaRonda() {
        juegoPrincipal.nuevaRonda();
        juegoPrincipal.getManoCrupier().voltearTodas();

        mazoBots = new Mazo();
        manoJ2.limpiar();
        manoJ3.limpiar();
        manoJ4.limpiar();
        turnoActual = 0;

        repartirInicial(manoJ2);
        repartirInicial(manoJ3);
        repartirInicial(manoJ4);

        if (!juegoPrincipal.puedeJugar()) {
            if (juegoPrincipal.getManoCrupier().esBlackjack()) {
                turnoActual = 5;
            } else {
                turnoActual = 1;
            }
        }
    }

    private void repartirInicial(Mano mano) {
        CartaInglesa c1 = mazoBots.obtenerUnaCarta();
        CartaInglesa c2 = mazoBots.obtenerUnaCarta();
        if (c1 != null) mano.agregarCarta(c1);
        if (c2 != null) mano.agregarCarta(c2);
        mano.voltearTodas();
    }

    public void jugadorRoba() {
        if (turnoActual == 0 && juegoPrincipal.puedeJugar()) {
            juegoPrincipal.pedirCarta();

            if (!juegoPrincipal.puedeJugar()) {
                turnoActual = 1;
            }
        }
    }

    public void jugadorSeQueda() {
        if (turnoActual == 0 && juegoPrincipal.puedeJugar()) {
            turnoActual = 1;
        }
    }

    public boolean esTurnoDeBots() {
        return turnoActual >= 1 && turnoActual <= 4;
    }

    public boolean esFinDeRonda() {
        return turnoActual == 5;
    }

    public void jugarSiguienteTurno() {
        switch (turnoActual) {
            case 1:
                if (manoJ2.calcularValor() < 17) robarBot(manoJ2);
                else turnoActual = 2;
                break;
            case 2:
                if (manoJ3.calcularValor() < 17) robarBot(manoJ3);
                else turnoActual = 3;
                break;
            case 3:
                if (manoJ4.calcularValor() < 17) robarBot(manoJ4);
                else turnoActual = 4;
                break;
            case 4:
                if (juegoPrincipal.puedeJugar()) {
                    juegoPrincipal.quedarse();
                } else {
                    Mano crupier = juegoPrincipal.getManoCrupier();
                    while (crupier.calcularValor() < 17) {
                        robarBot(crupier);
                    }
                }
                turnoActual = 5;
                break;
        }
    }

    private void robarBot(Mano mano) {
        CartaInglesa carta = mazoBots.obtenerUnaCarta();
        if (carta != null) {
            carta.makeFaceUp();
            mano.agregarCarta(carta);
        }
    }

    public boolean juegoEnCurso() { return juegoPrincipal.puedeJugar(); }
    public Mano getManoCrupier() { return juegoPrincipal.getManoCrupier(); }
    public Mano getManoJ1() { return juegoPrincipal.getManoJugador(); }
    public Mano getManoJ2() { return manoJ2; }
    public Mano getManoJ3() { return manoJ3; }
    public Mano getManoJ4() { return manoJ4; }

    public int getPtsCrupier() { return juegoPrincipal.getManoCrupier().calcularValor(); }
    public int getPtsJ1() { return juegoPrincipal.getManoJugador().calcularValor(); }
    public int getPtsJ2() { return manoJ2.calcularValor(); }
    public int getPtsJ3() { return manoJ3.calcularValor(); }
    public int getPtsJ4() { return manoJ4.calcularValor(); }

    public String obtenerMensajeGanador() {
        Mano[] manos = {getManoJ1(), manoJ2, manoJ3, manoJ4, getManoCrupier()};
        String[] nombres = {"Jugador 1 (Tú)", "Jugador 2", "Jugador 3", "Jugador 4", "Crupier"};

        int maxPuntos = -1;
        int minCartas = 999;
        boolean hayBlackjack = false;

        for (Mano m : manos) {
            int pts = m.calcularValor();
            if (pts <= 21) {
                if (m.esBlackjack()) {
                    hayBlackjack = true;
                    maxPuntos = 21;
                    minCartas = 2;
                } else if (!hayBlackjack) {
                    if (pts > maxPuntos) {
                        maxPuntos = pts;
                        minCartas = m.getCartas().size();
                    } else if (pts == maxPuntos) {
                        if (m.getCartas().size() < minCartas) {
                            minCartas = m.getCartas().size();
                        }
                    }
                }
            }
        }

        if (maxPuntos == -1) {
            return "Todos pierden\nSe pasaron de 21";
        }

        int cantidadGanadores = 0;
        String nombreGanador = "";

        for (int i = 0; i < manos.length; i++) {
            int pts = manos[i].calcularValor();
            int numCartas = manos[i].getCartas().size();

            if (pts <= 21) {
                if (hayBlackjack && manos[i].esBlackjack()) {
                    cantidadGanadores++;
                    nombreGanador = nombres[i];
                } else if (!hayBlackjack && pts == maxPuntos && numCartas == minCartas) {
                    cantidadGanadores++;
                    nombreGanador = nombres[i];
                }
            }
        }

        if (cantidadGanadores > 1) {
            return "Empate";
        } else if (nombreGanador.equals("Jugador 1 (Tú)")) {
            return "Ganaste!\nTienes " + maxPuntos + " pts en " + minCartas + " cartas";
        } else {
            return "Ganó " + nombreGanador.toUpperCase() + "!\nCon " + maxPuntos + " pts en " + minCartas + " cartas";
        }
    }

    public String obtenerTipoGanador() {
        String mensaje = obtenerMensajeGanador();
        if (mensaje.contains("Ganaste")) return "VICTORIA";
        if (mensaje.contains("Empate")) return "EMPATE";
        if (mensaje.contains("Todos pierden")) return "DERROTA";
        return "OTRO";
    }
}