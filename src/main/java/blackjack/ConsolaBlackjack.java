package blackjack;

import java.util.Scanner;

public class ConsolaBlackjack {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        BlackjackJuego juego = new BlackjackJuego();

        System.out.println("Blackjack");
        mostrarEstado(juego);

        boolean salir = false;
        while (!salir) {
            System.out.println("\n1) Pedir carta   2) Quedarse   3) Nueva ronda   4) Salir");
            System.out.print("Opción: ");
            String opcion = teclado.nextLine();

            switch (opcion) {
                case "1" -> juego.pedirCarta();
                case "2" -> juego.quedarse();
                case "3" -> juego.nuevaRonda();
                case "4" -> salir = true;
                default -> System.out.println("Opción inválida");
            }

            if (!salir) {
                mostrarEstado(juego);
            }
        }
        System.out.println("A");
    }

    private static void mostrarEstado(BlackjackJuego juego) {
        System.out.println();
        System.out.println("Crupier: " + juego.getManoCrupier());
        System.out.println("Jugador: " + juego.getManoJugador());

        if (juego.puedeJugar()) {
            System.out.println("Tu total: " + juego.getManoJugador().calcularValor());
        } else {
            System.out.println("Total jugador: " + juego.getManoJugador().calcularValor()
                    + "   |   Total crupier: " + juego.getManoCrupier().calcularValor());
            System.out.println(">>> " + juego.getEstado());
        }
    }
}