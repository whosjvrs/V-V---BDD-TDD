"""F03 - Visualizar tarefa e F04 - Excluir tarefa."""
from tocktock import extensoes as ext


@ext.linhas_tarefa
def visualizar_tarefa(tarefa):
    return [f"Nome: {tarefa.nome}", f"Data: {tarefa.data}"]


@ext.opcao_tarefa
def excluir_tarefa(tela):
    def excluir():
        tela.app.gerenciador.remover(tela.tarefa)
        tela.app.voltar_ao_inicio()

    return {"X": ("Excluir tarefa", excluir)}
