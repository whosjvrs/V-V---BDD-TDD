"""F07 - Prioridade da tarefa (João Victor dos Reis)."""
from tocktock import extensoes as ext

ext.campo("prioridade", "Definir prioridade",
          "Digite a prioridade da tarefa (Baixa, Média, Alta):", "Média")


@ext.validacao
def validar_prioridade(valores, tarefa):
    if valores.get("prioridade") not in ["Baixa", "Média", "Alta"]:
        raise ValueError("Prioridade inválida. Use Baixa, Média ou Alta.")


@ext.coluna
def mostrar_prioridade(tarefa):
    if "prioridade" in tarefa.campos:
        return "Prioridade: " + tarefa.campos["prioridade"]
    return None


@ext.visao
def ordenar(tarefas, app):
    if app.estado.get("f07_ordenar"):
        tarefas = sorted(tarefas, key=lambda t: {"Alta": 0, "Média": 1, "Baixa": 2}.get(t.campos.get("prioridade"), 3))
    return tarefas


@ext.opcao_inicio
def opcao_ordenar(tela):
    def ordenar_por_prioridade():
        tela.app.estado["f07_ordenar"] = True

    return {"O": ("Ordenar por prioridade", ordenar_por_prioridade)}
