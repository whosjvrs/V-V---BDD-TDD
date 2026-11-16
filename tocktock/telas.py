class Tela:
    titulo = ""

    def __init__(self, app):
        self.app = app

    def linhas(self):
        return []

    def opcoes(self):
        return {}

    def renderizar(self):
        cabecalho = [f"TockTock - {self.titulo}"] if self.titulo else []
        rodape = [f"[{tecla}] {rotulo}" for tecla, (rotulo, _) in self.opcoes().items()]
        blocos = [cabecalho, self.linhas(), rodape]
        return "\n\n".join("\n".join(bloco) for bloco in blocos if bloco)

    def receber(self, entrada):
        opcao = self.opcoes().get(entrada)
        if opcao:
            _, acao = opcao
            acao()


class TelaInicio(Tela):
    titulo = "Início"

    def linhas(self):
        return [
            f"{numero}. {tarefa.nome} | {tarefa.data}"
            for numero, tarefa in enumerate(self.app.gerenciador.listar(), start=1)
        ]

    def opcoes(self):
        return {"N": ("Criar nova tarefa", self.criar_tarefa)}

    def receber(self, entrada):
        tarefa = self.tarefa_pelo_numero(entrada)
        if tarefa:
            self.app.ir_para(TelaTarefa(self.app, tarefa))
        else:
            super().receber(entrada)

    def tarefa_pelo_numero(self, entrada):
        tarefas = self.app.gerenciador.listar()
        if entrada.isdecimal() and 1 <= int(entrada) <= len(tarefas):
            return tarefas[int(entrada) - 1]
        return None

    def criar_tarefa(self):
        self.app.ir_para(TelaFormulario(self.app, "Nova tarefa", self.app.gerenciador.criar))


class TelaTarefa(Tela):
    titulo = "Tarefa"

    def __init__(self, app, tarefa):
        super().__init__(app)
        self.tarefa = tarefa

    def opcoes(self):
        return {"E": ("Editar", self.editar)}

    def editar(self):
        formulario = TelaFormulario(
            self.app, "Editar tarefa", self.salvar_edicao, self.tarefa.nome, self.tarefa.data
        )
        self.app.ir_para(formulario)

    def salvar_edicao(self, nome, data):
        self.app.gerenciador.editar(self.tarefa, nome, data)


class TelaFormulario(Tela):
    def __init__(self, app, titulo, ao_salvar, nome="", data=""):
        super().__init__(app)
        self.titulo = titulo
        self.ao_salvar = ao_salvar
        self.valores = {"nome": nome, "data": data}

    def opcoes(self):
        return {
            "1": ("Definir nome", lambda: self.perguntar("nome", "Digite o nome da tarefa:")),
            "2": ("Definir data", lambda: self.perguntar("data", "Digite a data da tarefa:")),
            "S": ("Salvar", self.salvar),
        }

    def perguntar(self, campo, pergunta):
        self.app.ir_para(TelaPergunta(self, campo, pergunta))

    def salvar(self):
        self.ao_salvar(**self.valores)
        self.app.voltar_ao_inicio()


class TelaPergunta(Tela):
    def __init__(self, formulario, campo, pergunta):
        super().__init__(formulario.app)
        self.formulario = formulario
        self.campo = campo
        self.pergunta = pergunta

    def linhas(self):
        return [self.pergunta]

    def receber(self, entrada):
        self.formulario.valores[self.campo] = entrada
        self.app.ir_para(self.formulario)
