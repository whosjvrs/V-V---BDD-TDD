from tocktock import extensoes as ext
from tocktock.telas import Tela, TelaFormulario


def listas(app):
    return app.estado.setdefault("f11_listas", {})


class TelaNomeDaLista(Tela):
    def __init__(self, app, ao_responder):
        super().__init__(app)
        self.ao_responder = ao_responder

    def receber(self, entrada):
        self.ao_responder(entrada)


class TelaLista(Tela):
    def __init__(self, app, nome):
        super().__init__(app)
        self.nome = nome
        self.titulo = f"Lista {nome}"

    def linhas(self):
        tarefas = listas(self.app)[self.nome]
        return [f"{i}. {t.nome}" for i, t in enumerate(tarefas, start=1)] or ["This list has no tasks."]

    def opcoes(self):
        return {"N": ("Criar nova tarefa", self.criar_tarefa)}

    def criar_tarefa(self):
        def salvar(valores):
            tarefa = self.app.gerenciador.criar(**valores)
            listas(self.app)[self.nome].append(tarefa)
            return tarefa

        self.app.ir_para(TelaFormulario(self.app, "Nova tarefa", salvar))


@ext.linhas_inicio
def mostrar_listas(app):
    linhas = [f"Lista: {nome}" for nome in listas(app)]
    if "f11_erro" in app.estado:
        linhas.append("Erro: " + app.estado.pop("f11_erro"))
    return linhas


@ext.opcao_inicio
def opcoes_de_lista(tela):
    app = tela.app

    def criar(nome):
        if not nome.strip():
            app.estado["f11_erro"] = "The list name is required."
        elif nome in listas(app):
            app.estado["f11_erro"] = "A list with this name already exists."
        else:
            listas(app)[nome] = []
        app.voltar_ao_inicio()

    def entrar(nome):
        app.ir_para(TelaLista(app, nome))

    return {
        "L": ("Criar lista", lambda: app.ir_para(TelaNomeDaLista(app, criar))),
        "E": ("Entrar em lista", lambda: app.ir_para(TelaNomeDaLista(app, entrar))),
    }
