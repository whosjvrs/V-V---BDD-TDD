"""F07 - Prioridade da tarefa (João Victor dos Reis)."""
from tocktock import extensoes as ext

# Da maior para a menor: a posição na lista define a ordenação.
PRIORIDADES = ["Alta", "Média", "Baixa"]
PADRAO = "Média"
ERRO_PRIORIDADE = "Prioridade inválida. Use Baixa, Média ou Alta."
CHAVE_ORDENAR = "f07_ordenar"

ext.campo("prioridade", "Definir prioridade",
          "Digite a prioridade da tarefa (Baixa, Média, Alta):", PADRAO)


def prioridade_de(tarefa):
    return tarefa.campos.get("prioridade")


def peso(tarefa):
    """Posição da prioridade na ordenação; tarefas sem prioridade vão para o fim."""
    prioridade = prioridade_de(tarefa)
    return PRIORIDADES.index(prioridade) if prioridade in PRIORIDADES else len(PRIORIDADES)


@ext.validacao
def validar_prioridade(valores, tarefa):
    if valores.get("prioridade") not in PRIORIDADES:
        raise ValueError(ERRO_PRIORIDADE)


@ext.coluna
def mostrar_prioridade(tarefa):
    prioridade = prioridade_de(tarefa)
    return f"Prioridade: {prioridade}" if prioridade else None


@ext.visao
def ordenar(tarefas, app):
    return sorted(tarefas, key=peso) if app.estado.get(CHAVE_ORDENAR) else tarefas


@ext.opcao_inicio
def opcao_ordenar(tela):
    def ordenar_por_prioridade():
        tela.app.estado[CHAVE_ORDENAR] = True

    return {"O": ("Ordenar por prioridade", ordenar_por_prioridade)}
