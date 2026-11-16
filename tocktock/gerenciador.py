from tocktock.tarefa import Tarefa


class GerenciadorTarefas:
    def __init__(self):
        self._tarefas = []

    def criar(self, nome, data, **campos):
        tarefa = Tarefa(nome, data, dict(campos))
        self._tarefas.append(tarefa)
        return tarefa

    def listar(self):
        return list(self._tarefas)

    def editar(self, tarefa, nome, data, **campos):
        tarefa.nome = nome
        tarefa.data = data
        tarefa.campos.update(campos)
        return tarefa

    def remover(self, tarefa):
        self._tarefas.remove(tarefa)
