package br.pucpr.tocktock.app.abas;

import br.pucpr.tocktock.app.Aba;
import br.pucpr.tocktock.app.PainelEmConstrucao;
import javafx.scene.Node;

/**
 * Aba de Rafael Sato.
 *
 * Só Rafael altera este arquivo. Quando a lógica de F11 e F12 estiver
 * passando nos testes, troque o conteúdo de criarConteudo() pela tela real,
 * chamando as classes do pacote br.pucpr.tocktock.f1112.
 */
public class AbaF11F12 implements Aba {

    @Override
    public String titulo() {
        return "F11 · F12";
    }

    @Override
    public String responsavel() {
        return "Rafael Sato";
    }

    @Override
    public String[] funcionalidades() {
        return new String[]{"F11 – Criar lista de tarefas", "F12 – Entrar em lista de tarefas"};
    }

    @Override
    public Node criarConteudo() {
        return PainelEmConstrucao.para(this);
    }
}
