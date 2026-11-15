package br.pucpr.tocktock.app;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Painel provisório mostrado nas abas cujas funcionalidades ainda não foram ligadas à interface.
 * Não precisa ser alterado por ninguém.
 */
public final class PainelEmConstrucao {

    private PainelEmConstrucao() {
    }

    public static Node para(Aba aba) {
        Label selo = new Label("EM CONSTRUÇÃO");
        selo.getStyleClass().add("selo");

        Label titulo = new Label(String.join("   ·   ", aba.funcionalidades()));
        titulo.getStyleClass().add("titulo-aba");
        titulo.setWrapText(true);

        Label responsavel = new Label("Responsável: " + aba.responsavel());
        responsavel.getStyleClass().add("texto-secundario");

        Label dica = new Label(
                "Fluxo: cenário BDD → ciclos TDD (JUnit) → cenários BDD verdes → ligar esta aba ao serviço do módulo.");
        dica.getStyleClass().add("texto-secundario");
        dica.setWrapText(true);

        VBox caixa = new VBox(12, selo, titulo, responsavel, dica);
        caixa.setAlignment(Pos.CENTER);
        caixa.setMaxWidth(560);
        caixa.getStyleClass().add("cartao");

        VBox fundo = new VBox(caixa);
        fundo.setAlignment(Pos.CENTER);
        fundo.getStyleClass().add("conteudo");
        return fundo;
    }
}
