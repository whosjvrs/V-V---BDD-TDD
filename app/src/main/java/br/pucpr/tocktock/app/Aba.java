package br.pucpr.tocktock.app;

import javafx.scene.Node;

/**
 * Uma aba da janela principal. Cada integrante tem a sua, em br.pucpr.tocktock.app.abas.
 */
public interface Aba {

    /** Texto que aparece na aba, ex.: "F07 · F08". */
    String titulo();

    /** Nome de quem é responsável pela aba. */
    String responsavel();

    /** As duas funcionalidades da aba, ex.: "F07 – Prioridade da tarefa". */
    String[] funcionalidades();

    /**
     * Conteúdo da aba. Enquanto a funcionalidade não estiver pronta,
     * devolve o painel "em construção". Quando a lógica passar nos testes,
     * troque pelo seu formulário/lista chamando o serviço do seu módulo.
     */
    Node criarConteudo();
}
