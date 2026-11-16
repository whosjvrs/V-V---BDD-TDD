"""F05 - Status de conclusão e F06 - Status de execução (Tabatha Martins)."""
from datetime import date

from tocktock import extensoes as ext


@ext.opcao_tarefa
def alternar_conclusao(tela):
    app, tarefa = tela.app, tela.tarefa

    if tarefa.campos.get("concluida", False):
        def desmarcar():
            tarefa.campos["concluida"] = False
            tarefa.campos["data_conclusao"] = None
            app.voltar_ao_inicio()

        return {"C": ("Marcar como não concluída", desmarcar)}

    def concluir():
        if not tarefa.campos.get("farei", True):
            app.estado["f05_alerta"] = "Não é possível concluir uma tarefa marcada como Não farei"
        else:
            tarefa.campos["concluida"] = True
            tarefa.campos["data_conclusao"] = date.today().strftime("%d/%m/%Y")
        app.voltar_ao_inicio()

    return {"C": ("Marcar como concluída", concluir)}


@ext.opcao_tarefa
def alternar_farei(tela):
    app, tarefa = tela.app, tela.tarefa

    if tarefa.campos.get("farei", True):
        def marcar_nao_farei():
            tarefa.campos["farei"] = False
            app.voltar_ao_inicio()

        return {"F": ("Marcar como não farei", marcar_nao_farei)}

    def marcar_farei():
        tarefa.campos["farei"] = True
        app.voltar_ao_inicio()

    return {"F": ("Marcar como farei", marcar_farei)}


@ext.coluna
def coluna_conclusao(tarefa):
    if tarefa.campos.get("concluida", False):
        return "Concluída em " + tarefa.campos["data_conclusao"]
    return "Não concluída"


@ext.coluna
def coluna_farei(tarefa):
    if tarefa.campos.get("farei", True):
        return "Farei"
    return "Não farei"


@ext.linhas_inicio
def alerta(app):
    mensagem = app.estado.pop("f05_alerta", None)
    if mensagem:
        return ["Alerta: " + mensagem]
    return []
