package f0506;

/**
 * Guarda o nome da tarefa usada no cenário, para os passos "Então"
 * (que não repetem o nome) saberem qual linha da lista conferir.
 * O Cucumber cria uma instância nova a cada cenário.
 */
public class TarefaAtual {
    public String nome;
}
