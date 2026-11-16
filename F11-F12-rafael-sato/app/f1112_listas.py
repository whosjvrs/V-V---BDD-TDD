from tocktock import extensoes as ext
from tocktock.telas import Tela, TelaFormulario, TelaTarefa, formatar_tarefa

CHAVE_LISTAS = "f11_listas"
CHAVE_ERRO = "f11_erro"
CAMPO_LISTA = "lista"

ERRO_NOME_OBRIGATORIO = "The list name is required."
ERRO_NOME_REPETIDO = "A list with this name already exists."
ERRO_LISTA_INEXISTENTE = "There is no list with this name."
LISTA_VAZIA = "This list has no tasks."

def listas(app):
    return app.estado.setdefault(CHAVE_LISTAS, [])


def procurar_lista(app, nome):
    nome = nome.strip().casefold()
    return next((lista for lista in listas(app) if lista.casefold() == nome), None)


def tarefas_da_lista(app, lista):
    return [t for t in app.gerenciador.listar() if t.campos.get(CAMPO_LISTA) == lista]


def validar_nome(app, nome):
    if not nome.strip():
        raise ValueError(ERRO_NOME_OBRIGATORIO)
    if procurar_lista(app, nome):
        raise ValueError(ERRO_NOME_REPETIDO)

def mostrar_erro(app, mensagem):
    app.estado[CHAVE_ERRO] = mensagem
    app.voltar_ao_inicio()


def criar_lista(app, nome):
    try:
        validar_nome(app, nome)
    except ValueError as erro:
        return mostrar_erro(app, str(erro))
    listas(app).append(nome.strip())
    app.voltar_ao_inicio()


def entrar_na_lista(app, nome):
    lista = procurar_lista(app, nome)
    if lista is None:
        return mostrar_erro(app, ERRO_LISTA_INEXISTENTE)
    app.ir_para(TelaLista(app, lista))

class TelaNomeDaLista(Tela):

    def __init__(self, app, pergunta, ao_responder):
        super().__init__(app)
        self.pergunta = pergunta
        self.ao_responder = ao_responder

    def linhas(self):
        return [self.pergunta]

    def receber(self, entrada):
        self.ao_responder(self.app, entrada)


class TelaLista(Tela):
    def __init__(self, app, nome):
        super().__init__(app)
        self.nome = nome
        self.titulo = f"Lista {nome}"

    def tarefas(self):
        return tarefas_da_lista(self.app, self.nome)

    def linhas(self):
        tarefas = self.tarefas()
        if not tarefas:
            return [LISTA_VAZIA]
        total = "1 tarefa" if len(tarefas) == 1 else f"{len(tarefas)} tarefas"
        return [total, ""] + [
            f"{numero}. {formatar_tarefa(tarefa)}"
            for numero, tarefa in enumerate(tarefas, start=1)
        ]

    def opcoes(self):
        return {
            "N": ("Criar nova tarefa", self.criar_tarefa),
            "V": ("Voltar", self.app.voltar_ao_inicio),
        }

    def criar_tarefa(self):
        def salvar(valores):
            return self.app.gerenciador.criar(**valores, **{CAMPO_LISTA: self.nome})

        self.app.ir_para(TelaFormulario(self.app, f"Nova tarefa em {self.nome}", salvar))

    def receber(self, entrada):
        tarefas = self.tarefas()
        if entrada.isdecimal() and 1 <= int(entrada) <= len(tarefas):
            self.app.ir_para(TelaTarefa(self.app, tarefas[int(entrada) - 1]))
        else:
            super().receber(entrada)

@ext.linhas_inicio
def barra_lateral(app):
    linhas = [f"Lista: {nome}" for nome in listas(app)]
    erro = app.estado.pop(CHAVE_ERRO, None)
    if erro:
        linhas.append(f"Erro: {erro}")
    return linhas


@ext.opcao_inicio
def opcoes_de_lista(tela):
    app = tela.app

    def perguntar(pergunta, ao_responder):
        return lambda: app.ir_para(TelaNomeDaLista(app, pergunta, ao_responder))

    opcoes = {"L": ("Criar lista", perguntar("Digite o nome da nova lista:", criar_lista))}
    if listas(app):
        opcoes["E"] = ("Entrar em lista", perguntar("Digite o nome da lista:", entrar_na_lista))
    return opcoes
