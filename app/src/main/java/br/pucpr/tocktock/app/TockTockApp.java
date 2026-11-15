package br.pucpr.tocktock.app;

import br.pucpr.tocktock.app.abas.AbaF01F02;
import br.pucpr.tocktock.app.abas.AbaF03F04;
import br.pucpr.tocktock.app.abas.AbaF05F06;
import br.pucpr.tocktock.app.abas.AbaF07F08;
import br.pucpr.tocktock.app.abas.AbaF09F10;
import br.pucpr.tocktock.app.abas.AbaF11F12;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * Janela principal do TockTock: uma aba "Início" + uma aba por integrante.
 *
 * Não é preciso alterar esta classe para ligar a sua funcionalidade:
 * altere apenas a SUA classe em br.pucpr.tocktock.app.abas.
 */
public class TockTockApp extends Application {

    private static final List<Aba> ABAS = List.of(
            new AbaF01F02(),
            new AbaF03F04(),
            new AbaF05F06(),
            new AbaF07F08(),
            new AbaF09F10(),
            new AbaF11F12()
    );

    @Override
    public void start(Stage palco) {
        TabPane abas = new TabPane();
        abas.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        abas.getTabs().add(new Tab("Início", criarInicio()));
        for (Aba aba : ABAS) {
            abas.getTabs().add(new Tab(aba.titulo(), aba.criarConteudo()));
        }

        BorderPane raiz = new BorderPane();
        raiz.setTop(criarCabecalho());
        raiz.setCenter(abas);

        Scene cena = new Scene(raiz, 1024, 680);
        cena.getStylesheets().add(
                TockTockApp.class.getResource("styles.css").toExternalForm());

        palco.setTitle("TockTock");
        palco.setMinWidth(720);
        palco.setMinHeight(480);
        palco.setScene(cena);
        palco.show();
    }

    private Node criarCabecalho() {
        Label marca = new Label("TockTock");
        marca.getStyleClass().add("marca");
        Label subtitulo = new Label("Gerenciador de tarefas com calendário");
        subtitulo.getStyleClass().add("subtitulo");

        VBox cabecalho = new VBox(2, marca, subtitulo);
        cabecalho.setPadding(new Insets(18, 24, 14, 24));
        cabecalho.getStyleClass().add("cabecalho");
        return cabecalho;
    }

    private Node criarInicio() {
        Label titulo = new Label("Funcionalidades");
        titulo.getStyleClass().add("titulo-aba");

        FlowPane cartoes = new FlowPane(16, 16);
        for (Aba aba : ABAS) {
            Label nome = new Label(aba.responsavel());
            nome.getStyleClass().add("cartao-titulo");
            VBox cartao = new VBox(6, nome);
            for (String funcionalidade : aba.funcionalidades()) {
                Label f = new Label(funcionalidade);
                f.getStyleClass().add("texto-secundario");
                cartao.getChildren().add(f);
            }
            cartao.setPrefWidth(300);
            cartao.getStyleClass().add("cartao");
            cartoes.getChildren().add(cartao);
        }

        VBox inicio = new VBox(16, titulo, cartoes);
        inicio.getStyleClass().add("conteudo");

        ScrollPane rolagem = new ScrollPane(inicio);
        rolagem.setFitToWidth(true);
        return rolagem;
    }
}
