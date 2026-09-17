package Vista;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import blackjack.ControladorMesa;
import blackjack.BlackjackJuego.Estado;
import blackjack.Mano;
import DeckOfCards.CartaInglesa;
import javafx.application.Platform;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Vista extends StackPane {

    private ControladorMesa controlador;

    private HBox cajaCrupier;
    private HBox cajaJugador1;
    private HBox cajaJugador2;
    private HBox cajaJugador3;
    private HBox cajaJugador4;

    private Label lblPtsCrupier;
    private Label lblPtsJ1;
    private Label lblPtsJ2;
    private Label lblPtsJ3;
    private Label lblPtsJ4;

    private StackPane drawPile;
    private Button btnRobar;
    private Button btnQuedarse;

    private VBox overlayFinJuego;
    private Label lblMensajeFin;
    private Button btnReintentar;
    private VBox menuInicial;

    public Vista() {
        controlador = new ControladorMesa();
        inicializarUI();
        actualizarTablero();
    }

    private void iniciarJuegoDesdeMenu() {
        menuInicial.setVisible(false);
        verificarAutomatizacion();
    }

    private void inicializarUI() {
        this.setStyle("-fx-background-color: #2E7D32;");

        BorderPane mesa = new BorderPane();
        mesa.setPadding(new Insets(30));

        lblPtsCrupier = crearEtiquetaBlanca("Puntos: 0");
        lblPtsJ1 = crearEtiquetaBlanca("Puntos: 0");
        lblPtsJ2 = crearEtiquetaBlanca("Puntos: 0");
        lblPtsJ3 = crearEtiquetaBlanca("Puntos: 0");
        lblPtsJ4 = crearEtiquetaBlanca("Puntos: 0");

        cajaCrupier = new HBox(10);
        cajaCrupier.setAlignment(Pos.CENTER);
        mesa.setTop(crearPanelJugador("Crupier", cajaCrupier, lblPtsCrupier));

        cajaJugador1 = new HBox(15);
        cajaJugador1.setAlignment(Pos.CENTER);
        cajaJugador1.setPadding(new Insets(20, 0, 10, 0));

        btnRobar = new Button("Robar");
        btnQuedarse = new Button("Quedarse");
        HBox cajaBotones = new HBox(15, btnRobar, btnQuedarse);
        cajaBotones.setAlignment(Pos.CENTER);

        VBox panelJ1 = new VBox(10, crearEtiquetaBlanca("Jugador 1 (Tú)"), cajaJugador1, lblPtsJ1, cajaBotones);
        panelJ1.setAlignment(Pos.CENTER);
        mesa.setBottom(panelJ1);

        cajaJugador2 = new HBox(5);
        cajaJugador2.setAlignment(Pos.CENTER);
        mesa.setLeft(crearPanelJugador("Jugador 2", cajaJugador2, lblPtsJ2));

        cajaJugador3 = new HBox(5);
        cajaJugador3.setAlignment(Pos.CENTER);
        mesa.setRight(crearPanelJugador("Jugador 3", cajaJugador3, lblPtsJ3));

        cajaJugador4 = new HBox(5);
        cajaJugador4.setAlignment(Pos.CENTER);
        VBox panelJ4 = crearPanelJugador("Jugador 4", cajaJugador4, lblPtsJ4);
        mesa.setCenter(panelJ4);

        drawPile = new StackPane();
        CardView backCard = new CardView(null);
        drawPile.getChildren().add(backCard);

        drawPile.setOnMouseClicked(e -> accionRobar());
        btnRobar.setOnAction(e -> accionRobar());
        btnQuedarse.setOnAction(e -> accionQuedarse());

        VBox cajaDrawPile = new VBox(5, drawPile, crearEtiquetaBlanca("Pila de robo"));
        cajaDrawPile.setAlignment(Pos.CENTER);
        cajaDrawPile.setMaxSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);
        cajaDrawPile.setPadding(new Insets(40));
        StackPane.setAlignment(cajaDrawPile, Pos.BOTTOM_LEFT);

        overlayFinJuego = new VBox(20);
        overlayFinJuego.setAlignment(Pos.CENTER);
        overlayFinJuego.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);");
        overlayFinJuego.setVisible(false);

        lblMensajeFin = new Label("");
        lblMensajeFin.setTextAlignment(TextAlignment.CENTER);
        lblMensajeFin.setFont(Font.font("Arial", FontWeight.BOLD, 45));

        btnReintentar = new Button("Volver a Jugar");
        btnReintentar.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        btnReintentar.setPadding(new Insets(10, 20, 10, 20));
        btnReintentar.setOnAction(e -> reiniciarJuego());

        overlayFinJuego.getChildren().addAll(lblMensajeFin, btnReintentar);

        menuInicial = new VBox(30);
        menuInicial.setAlignment(Pos.CENTER);

        menuInicial.setStyle("-fx-background-color: #1B5E20;");

        Label lblTitulo = new Label("BLACKJACK 21");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 70));
        lblTitulo.setTextFill(Color.WHITE);

        Button btnJugar = new Button("Jugar");
        btnJugar.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        btnJugar.setPadding(new Insets(10, 50, 10, 50));
        btnJugar.setOnAction(e -> iniciarJuegoDesdeMenu());

        Button btnSalir = new Button("Salir");
        btnSalir.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        btnSalir.setPadding(new Insets(10, 50, 10, 50));
        btnSalir.setOnAction(e -> Platform.exit());

        menuInicial.getChildren().addAll(lblTitulo, btnJugar, btnSalir);

        this.getChildren().addAll(mesa, cajaDrawPile, overlayFinJuego, menuInicial);
    }


    private void mostrarPantallaFinal() {
        String mensaje = controlador.obtenerMensajeGanador();
        String tipo = controlador.obtenerTipoGanador();

        switch (tipo) {
            case "VICTORIA":
                lblMensajeFin.setTextFill(Color.web("#008000"));
                break;
            case "EMPATE":
                lblMensajeFin.setTextFill(Color.web("#FFEB3B"));
                break;
            case "DERROTA":
            case "OTRO":
                lblMensajeFin.setTextFill(Color.web("#F44336"));
                break;
        }

        lblMensajeFin.setText(mensaje);
        overlayFinJuego.setVisible(true);
    }

    private void renderizarMano(HBox caja, Mano mano, double escala) {
        caja.getChildren().clear();
        for (CartaInglesa carta : mano.getCartas()) {
            CardView cv = new CardView(carta);
            cv.setScaleX(escala);
            cv.setScaleY(escala);
            caja.getChildren().add(cv);
        }
    }

    private VBox crearPanelJugador(String nombre, HBox cajaCartas, Label lblPuntos) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(crearEtiquetaBlanca(nombre), cajaCartas, lblPuntos);
        return vbox;
    }

    private Label crearEtiquetaBlanca(String texto) {
        Label lbl = new Label(texto);
        lbl.setTextFill(Color.WHITE);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lbl.setAlignment(Pos.CENTER);
        return lbl;
    }

    private void accionRobar() {
        controlador.jugadorRoba();
        actualizarTablero();

        if (controlador.esTurnoDeBots()) {
            procesarTurnosBots();
        }
    }

    private void accionQuedarse() {
        controlador.jugadorSeQueda();
        actualizarTablero();

        if (controlador.esTurnoDeBots()) {
            procesarTurnosBots();
        }
    }

    private void procesarTurnosBots() {
        btnRobar.setDisable(true);
        btnQuedarse.setDisable(true);
        drawPile.setDisable(true);

        PauseTransition pausa = new PauseTransition(Duration.millis(800));
        pausa.setOnFinished(e -> {

            controlador.jugarSiguienteTurno();
            actualizarTablero();

            if (controlador.esTurnoDeBots()) {
                procesarTurnosBots();
            } else if (controlador.esFinDeRonda()) {
                mostrarPantallaFinal();
            }
        });
        pausa.play();
    }

    private void actualizarTablero() {
        renderizarMano(cajaCrupier, controlador.getManoCrupier(), 1.0);
        renderizarMano(cajaJugador1, controlador.getManoJ1(), 1.4);
        renderizarMano(cajaJugador2, controlador.getManoJ2(), 0.8);
        renderizarMano(cajaJugador3, controlador.getManoJ3(), 0.8);
        renderizarMano(cajaJugador4, controlador.getManoJ4(), 0.8);

        lblPtsCrupier.setText("Puntos: " + controlador.getPtsCrupier());
        lblPtsJ1.setText("Puntos: " + controlador.getPtsJ1());
        lblPtsJ2.setText("Puntos: " + controlador.getPtsJ2());
        lblPtsJ3.setText("Puntos: " + controlador.getPtsJ3());
        lblPtsJ4.setText("Puntos: " + controlador.getPtsJ4());

        if (!controlador.juegoEnCurso() || controlador.esTurnoDeBots() || controlador.esFinDeRonda()) {
            btnRobar.setDisable(true);
            btnQuedarse.setDisable(true);
            drawPile.setDisable(true);
        } else {
            btnRobar.setDisable(false);
            btnQuedarse.setDisable(false);
            drawPile.setDisable(false);
        }
    }

    private void reiniciarJuego() {
        controlador.iniciarNuevaRonda();
        overlayFinJuego.setVisible(false);
        actualizarTablero();

        verificarAutomatizacion();
    }

    private void verificarAutomatizacion() {
        if (controlador.esTurnoDeBots() || controlador.esFinDeRonda()) {
            procesarTurnosBots();
        }
    }
}