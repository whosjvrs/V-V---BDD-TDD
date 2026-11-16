"""F01 - Criar tarefa e F02 - Editar tarefa (Luiz Dias)."""
from tocktock import extensoes as ext
from tocktock.telas import TelaFormulario


@ext.opcao_inicio
def criar_nova_tarefa(tela):
    app = tela.app

    def criar():
        app.ir_para(TelaFormulario(app, "Nova tarefa", lambda valores: app.gerenciador.criar(**valores)))

    return {"N": ("Criar nova tarefa", criar)}


@ext.opcao_tarefa
def editar_tarefa(tela):
    app, tarefa = tela.app, tela.tarefa

    def editar():
        app.ir_para(TelaFormulario(
            app, "Editar tarefa",
            lambda valores: app.gerenciador.editar(tarefa, **valores),
            tarefa=tarefa))

    return {"E": ("Editar", editar)}
