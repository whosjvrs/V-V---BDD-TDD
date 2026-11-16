package comum;

public final class Verifica {
    private Verifica() {
    }

    public static void garantir(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }
}
