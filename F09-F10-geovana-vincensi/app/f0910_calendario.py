"""F09 - Calendário e F10 - Filtro por período."""
from datetime import datetime

from tocktock import extensoes as ext
from tocktock.telas import Tela, TelaPergunta, formatar_tarefa


@ext.opcao_inicio
def calendario(tela):
    return {"C": ("Calendário", lambda: tela.app.ir_para(TelaCalendario(tela.app)))}


class TelaCalendario(Tela):
    titulo = "Calendário"

    def __init__(self, app):
        super().__init__(app)
        self.valores = {"dia": "", "mês": "", "data inicial": "", "data final": ""}
        self.erro = None
        self.consulta = None

    def opcoes(self):
        return {
            "1": ("Definir dia", lambda: self.perguntar("dia")),
            "2": ("Definir mês", lambda: self.perguntar("mês")),
            "3": ("Definir data inicial", lambda: self.perguntar("data inicial")),
            "4": ("Definir data final", lambda: self.perguntar("data final")),
            "F": ("Filtrar", self.filtrar),
        }

    def perguntar(self, campo):
        if campo in ("dia", "mês"):
            self.consulta = campo
        self.app.ir_para(TelaPergunta(self, campo, f"Digite {campo}:"))

    def filtrar(self):
        self.consulta = "período"

    def linhas(self):
        tarefas = [t for t in self.app.gerenciador.listar() if t.data]
        if self.consulta == "dia":
            dia = datetime.strptime(self.valores["dia"], "%d/%m/%Y").date()
            tarefas = [t for t in tarefas
                       if datetime.strptime(t.data, "%d/%m/%Y").date() == dia]
        elif self.consulta == "mês":
            mes = datetime.strptime(self.valores["mês"], "%m/%Y")
            tarefas = [t for t in tarefas
                       if datetime.strptime(t.data, "%d/%m/%Y").strftime("%m/%Y")
                       == mes.strftime("%m/%Y")]
        elif self.consulta == "período":
            inicio = datetime.strptime(self.valores["data inicial"], "%d/%m/%Y").date()
            fim = datetime.strptime(self.valores["data final"], "%d/%m/%Y").date()
            tarefas = [t for t in tarefas
                       if inicio <= datetime.strptime(t.data, "%d/%m/%Y").date() <= fim]
            if not tarefas:
                return ["Nenhuma tarefa nesse intervalo"]
        return [f"{numero}. {formatar_tarefa(tarefa)}"
                for numero, tarefa in enumerate(tarefas, start=1)]
