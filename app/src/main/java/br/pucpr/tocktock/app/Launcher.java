package br.pucpr.tocktock.app;

import javafx.application.Application;

/**
 * Ponto de entrada para rodar pelo IntelliJ (botão ▶ ao lado do main).
 *
 * Existe só para contornar o erro "JavaFX runtime components are missing",
 * que acontece quando a classe com main estende Application diretamente.
 */
public final class Launcher {

    private Launcher() {
    }

    public static void main(String[] args) {
        Application.launch(TockTockApp.class, args);
    }
}
