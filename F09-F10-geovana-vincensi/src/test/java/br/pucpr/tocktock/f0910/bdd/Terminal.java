package br.pucpr.tocktock.f0910.bdd;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Path;
import java.nio.file.Files;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Terminal {
    private static final Pattern OPCAO = Pattern.compile("\\[(\\w+)\\] (.+)");
    private static final Pattern TAREFA = Pattern.compile("(\\d+)\\. (.+?)(?: \\| (.*))?");
    private static final String PROMPT = "\n> ";
    private static final long ESPERA_MS = 5000;

    public record LinhaTarefa(String numero, String data) {}

    private final Process processo;
    private final Writer entrada;
    private final StringBuilder saida = new StringBuilder();
    private int lido = 0;
    private String tela = "";

    public Terminal() throws IOException {
        String python = System.getenv().getOrDefault("PYTHON", "python");
        ProcessBuilder builder = new ProcessBuilder(python, "-u", "-m", "tocktock");
        String configurado = System.getenv("TOCKTOCK_DIR");
        if (configurado == null || configurado.isBlank()) {
            throw new IllegalStateException("Infraestrutura: defina TOCKTOCK_DIR com a raiz de ra4-verival");
        }
        Path raiz = Path.of(configurado).toAbsolutePath();
        if (!Files.isRegularFile(raiz.resolve("tocktock/__main__.py"))) {
            throw new IllegalStateException("Infraestrutura: base Python ausente em " + raiz
                    + "; esperado tocktock/__main__.py. Isto não é Red funcional.");
        }
        builder.directory(raiz.toFile());
        builder.redirectErrorStream(true);
        builder.environment().put("PYTHONIOENCODING", "utf-8");
        processo = builder.start();
        entrada = new OutputStreamWriter(processo.getOutputStream(), StandardCharsets.UTF_8);
        Thread leitor = new Thread(this::ler);
        leitor.setDaemon(true);
        leitor.start();
        try {
            esperarTela();
        } catch (RuntimeException | AssertionError erro) {
            fechar();
            throw erro;
        }
    }

    public String tela() {
        return tela;
    }

    public String titulo() {
        return tela.lines().findFirst().orElse("");
    }

    public Map<String, String> opcoes() {
        Map<String, String> opcoes = new LinkedHashMap<>();
        tela.lines()
                .map(linha -> OPCAO.matcher(linha.strip()))
                .filter(Matcher::matches)
                .forEach(achou -> opcoes.put(achou.group(2), achou.group(1)));
        return opcoes;
    }

    public Map<String, LinhaTarefa> tarefas() {
        Map<String, LinhaTarefa> tarefas = new LinkedHashMap<>();
        tela.lines()
                .map(linha -> TAREFA.matcher(linha.strip()))
                .filter(Matcher::matches)
                .forEach(achou -> tarefas.put(
                        achou.group(2),
                        new LinhaTarefa(achou.group(1), achou.group(3) == null ? "" : achou.group(3))));
        return tarefas;
    }

    public void digitar(String texto) {
        try {
            entrada.write(texto + "\n");
            entrada.flush();
        } catch (IOException erro) {
            throw new AssertionError("não consegui digitar no programa: " + erro.getMessage(), erro);
        }
        esperarTela();
    }

    public void escolher(String rotulo) {
        String tecla = opcoes().get(rotulo);
        assertTrue(tecla != null, "a opção \"" + rotulo + "\" não aparece na tela:\n" + tela);
        digitar(tecla);
    }

    public void preencher(String campo, String valor) {
        escolher("Definir " + campo);
        digitar(valor);
    }

    public void abrirTarefa(String nome) {
        LinhaTarefa tarefa = tarefas().get(nome);
        assertTrue(tarefa != null, "a tarefa \"" + nome + "\" não aparece na lista:\n" + tela);
        digitar(tarefa.numero());
    }

    public void fechar() {
        try {
            entrada.close();
            if (!processo.waitFor(2, TimeUnit.SECONDS)) {
                processo.destroyForcibly();
            }
        } catch (IOException erro) {
            processo.destroyForcibly();
        } catch (InterruptedException erro) {
            processo.destroyForcibly();
            Thread.currentThread().interrupt();
        }
    }

    private void ler() {
        try (Reader leitor = new InputStreamReader(processo.getInputStream(), StandardCharsets.UTF_8)) {
            int caractere;
            while ((caractere = leitor.read()) != -1) {
                synchronized (saida) {
                    saida.append((char) caractere);
                    saida.notifyAll();
                }
            }
        } catch (IOException fimDaSaida) {
            synchronized (saida) {
                saida.notifyAll();
            }
        }
    }

    private void esperarTela() {
        long limite = System.currentTimeMillis() + ESPERA_MS;
        synchronized (saida) {
            while (!temTelaNova()) {
                long falta = limite - System.currentTimeMillis();
                assertTrue(falta > 0 && processo.isAlive(),
                        "o programa não mostrou uma tela nova. saída até agora:\n" + saida.substring(lido));
                try {
                    saida.wait(falta);
                } catch (InterruptedException erro) {
                    Thread.currentThread().interrupt();
                    throw new AssertionError("interrompido esperando a tela do programa", erro);
                }
            }
            tela = saida.substring(lido, saida.length() - PROMPT.length()).strip();
            lido = saida.length();
        }
    }

    private boolean temTelaNova() {
        return saida.length() > lido && saida.toString().endsWith(PROMPT);
    }
}
