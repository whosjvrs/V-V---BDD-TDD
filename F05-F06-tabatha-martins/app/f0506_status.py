"""F05 - Status de conclusão e F06 - Status de execução (Tabatha Martins).

Campos usados em ``tarefa.campos``:
- ``concluida`` (True/False): status de conclusão, como combinado no README (F07 e F08 leem).
- ``data_conclusao`` (dd/mm/aaaa ou None): data em que a tarefa foi concluída.
- ``farei`` (True/False): status de execução. Sem o campo, a tarefa é "Farei".

Regra: uma tarefa marcada como "Não farei" não pode ser concluída.
"""
from datetime import date

from tocktock import extensoes as ext

CONCLUIDA = "concluida"
DATA_CONCLUSAO = "data_conclusao"
FAREI = "farei"
CHAVE_ALERTA = "f05_alerta"
FORMATO_DATA = "%d/%m/%Y"
MSG_NAO_FAREI = "Não é possível concluir uma tarefa marcada como Não farei"


# ---------- F05: Concluída / Não concluída ----------

def esta_concluida(tarefa):
    return tarefa.campos.get(CONCLUIDA, False)


def concluir(tarefa):
    """Marca como concluída e registra a data de hoje. Recusa tarefas "Não farei"."""
    if not vai_fazer(tarefa):
        raise ValueError(MSG_NAO_FAREI)
    tarefa.campos[CONCLUIDA] = True
    tarefa.campos[DATA_CONCLUSAO] = date.today().strftime(FORMATO_DATA)


def desmarcar(tarefa):
    """Volta para "Não concluída" e apaga a data de conclusão."""
    tarefa.campos[CONCLUIDA] = False
    tarefa.campos[DATA_CONCLUSAO] = None


def status_conclusao(tarefa):
    if esta_concluida(tarefa):
        return f"Concluída em {tarefa.campos[DATA_CONCLUSAO]}"
    return "Não concluída"


# ---------- F06: Farei / Não farei ----------

def vai_fazer(tarefa):
    return tarefa.campos.get(FAREI, True)


def alternar_execucao(tarefa):
    tarefa.campos[FAREI] = not vai_fazer(tarefa)


def status_execucao(tarefa):
    return "Farei" if vai_fazer(tarefa) else "Não farei"


# ---------- Ligação com as telas do app ----------

def opcao(app, tarefa, rotulo, acao):
    """Monta uma opção que executa a ação, mostra o erro como alerta e volta ao início."""
    def executar():
        try:
            acao(tarefa)
        except ValueError as erro:
            app.estado[CHAVE_ALERTA] = str(erro)
        app.voltar_ao_inicio()

    return rotulo, executar


@ext.opcao_tarefa
def opcao_conclusao(tela):
    if esta_concluida(tela.tarefa):
        return {"C": opcao(tela.app, tela.tarefa, "Marcar como não concluída", desmarcar)}
    return {"C": opcao(tela.app, tela.tarefa, "Marcar como concluída", concluir)}


@ext.opcao_tarefa
def opcao_execucao(tela):
    rotulo = "Marcar como não farei" if vai_fazer(tela.tarefa) else "Marcar como farei"
    return {"F": opcao(tela.app, tela.tarefa, rotulo, alternar_execucao)}


@ext.coluna
def coluna_conclusao(tarefa):
    return status_conclusao(tarefa)


@ext.coluna
def coluna_execucao(tarefa):
    return status_execucao(tarefa)


@ext.linhas_inicio
def alerta(app):
    """Mostra o alerta uma única vez, na próxima tela inicial."""
    mensagem = app.estado.pop(CHAVE_ALERTA, None)
    return [f"Alerta: {mensagem}"] if mensagem else []
