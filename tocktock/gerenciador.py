from tocktock.tarefa import Tarefa


class GerenciadorTarefas:
    def __init__(self):
        self._tarefas = []

    def criar(self, nome, data):
        tarefa = Tarefa(nome, data)
        self._tarefas.append(tarefa)
        return tarefa

    def listar(self):
        return list(self._tarefas)

    def editar(self, tarefa, nome, data):
        tarefa.nome = nome
        tarefa.data = data
        return tarefa
