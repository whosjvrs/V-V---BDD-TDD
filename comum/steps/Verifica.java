package steps;

final class Verifica {
    private Verifica() {
    }

    static void garantir(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }
}
