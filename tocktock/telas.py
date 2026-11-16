from tocktock import extensoes as ext


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

    def tarefas_visiveis(self):
        tarefas = self.app.gerenciador.listar()
        for visao in ext.VISOES:
            tarefas = visao(tarefas, self.app)
        return tarefas

    def linhas(self):
        extras = [linha for fonte in ext.LINHAS_INICIO for linha in fonte(self.app)]
        tarefas = [
            f"{numero}. {formatar_tarefa(tarefa)}"
            for numero, tarefa in enumerate(self.tarefas_visiveis(), start=1)
        ]
        return extras + tarefas

    def opcoes(self):
        return ext.juntar_opcoes(ext.OPCOES_INICIO, self)

    def receber(self, entrada):
        tarefa = self.tarefa_pelo_numero(entrada)
        if tarefa:
            self.app.ir_para(TelaTarefa(self.app, tarefa))
        else:
            super().receber(entrada)

    def tarefa_pelo_numero(self, entrada):
        tarefas = self.tarefas_visiveis()
        if entrada.isdecimal() and 1 <= int(entrada) <= len(tarefas):
            return tarefas[int(entrada) - 1]
        return None


def formatar_tarefa(tarefa):
    colunas = [tarefa.data] + [c for c in (coluna(tarefa) for coluna in ext.COLUNAS) if c]
    return " | ".join([tarefa.nome] + colunas)


class TelaTarefa(Tela):
    titulo = "Tarefa"

    def __init__(self, app, tarefa):
        super().__init__(app)
        self.tarefa = tarefa

    def linhas(self):
        return [linha for fonte in ext.LINHAS_TAREFA for linha in fonte(self.tarefa)]

    def opcoes(self):
        return ext.juntar_opcoes(ext.OPCOES_TAREFA, self)


class TelaFormulario(Tela):
    """Formulário de tarefa: nome, data e os campos extras registrados pelas funcionalidades.

    ``ao_salvar`` recebe um dicionário com todos os valores. ``tarefa`` é a tarefa
    sendo editada (ou None ao criar) e é repassada às validações.
    """

    def __init__(self, app, titulo, ao_salvar, tarefa=None):
        super().__init__(app)
        self.titulo = titulo
        self.ao_salvar = ao_salvar
        self.tarefa = tarefa
        self.erro = None
        if tarefa is None:
            self.valores = {"nome": "", "data": ""}
            self.valores.update({c.chave: c.padrao for c in ext.CAMPOS})
        else:
            self.valores = {"nome": tarefa.nome, "data": tarefa.data}
            self.valores.update({c.chave: tarefa.campos.get(c.chave, c.padrao) for c in ext.CAMPOS})

    def linhas(self):
        return [f"Erro: {self.erro}"] if self.erro else []

    def opcoes(self):
        opcoes = {
            "1": ("Definir nome", lambda: self.perguntar("nome", "Digite o nome da tarefa:")),
            "2": ("Definir data", lambda: self.perguntar("data", "Digite a data da tarefa:")),
        }
        for numero, campo in enumerate(ext.CAMPOS, start=3):
            opcoes[str(numero)] = (
                campo.rotulo, lambda campo=campo: self.perguntar(campo.chave, campo.pergunta))
        opcoes["S"] = ("Salvar", self.salvar)
        return opcoes

    def perguntar(self, campo, pergunta):
        self.app.ir_para(TelaPergunta(self, campo, pergunta))

    def salvar(self):
        try:
            for validar in ext.VALIDACOES:
                validar(self.valores, self.tarefa)
        except ValueError as erro:
            self.erro = str(erro)
            return
        tarefa = self.ao_salvar(dict(self.valores))
        for depois in ext.APOS_SALVAR:
            depois(tarefa, self.app)
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
        self.formulario.erro = None
        self.app.ir_para(self.formulario)
