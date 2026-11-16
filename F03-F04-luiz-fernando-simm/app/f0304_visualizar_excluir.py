"""F03 - Visualizar tarefa e F04 - Excluir tarefa."""
from tocktock import extensoes as ext


@ext.opcao_tarefa
def visualizar_tarefa(tela):
    tarefa = tela.tarefa
    return {"V": (f"Dados da tarefa: {tarefa.nome} | {tarefa.data}", lambda: None)}


@ext.opcao_tarefa
def excluir_tarefa(tela):
    def excluir():
        tela.app.gerenciador.remover(tela.tarefa)
        tela.app.voltar_ao_inicio()

    return {"X": ("Excluir tarefa", excluir)}
