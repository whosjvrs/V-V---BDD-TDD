package br.pucpr.tocktock.app.abas;

import br.pucpr.tocktock.app.Aba;
import br.pucpr.tocktock.app.PainelEmConstrucao;
import javafx.scene.Node;

/**
 * Aba de Luiz Dias.
 *
 * Só Luiz altera este arquivo. Quando a lógica de F01 e F02 estiver
 * passando nos testes, troque o conteúdo de criarConteudo() pela tela real,
 * chamando as classes do pacote br.pucpr.tocktock.f0102.
 */
public class AbaF01F02 implements Aba {

    @Override
    public String titulo() {
        return "F01 · F02";
    }

    @Override
    public String responsavel() {
        return "Luiz Dias";
    }

    @Override
    public String[] funcionalidades() {
        return new String[]{"F01 – Criar tarefa", "F02 – Editar tarefa"};
    }

    @Override
    public Node criarConteudo() {
        return PainelEmConstrucao.para(this);
    }
}
