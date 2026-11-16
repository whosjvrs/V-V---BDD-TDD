"""F09 - Calendário e F10 - Filtro por período."""
from calendar import monthrange
from datetime import datetime

from tocktock import extensoes as ext
from tocktock.telas import Tela, TelaPergunta, formatar_tarefa


def converter_data(texto, formato="%d/%m/%Y"):
    return datetime.strptime(texto, formato).date()


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

    def intervalo_consulta(self):
        if self.consulta == "dia":
            dia = converter_data(self.valores["dia"])
            return dia, dia
        if self.consulta == "mês":
            inicio = converter_data(self.valores["mês"], "%m/%Y")
            ultimo_dia = monthrange(inicio.year, inicio.month)[1]
            return inicio, inicio.replace(day=ultimo_dia)
        if self.consulta == "período":
            return (converter_data(self.valores["data inicial"]),
                    converter_data(self.valores["data final"]))
        return None

    def linhas(self):
        tarefas = [t for t in self.app.gerenciador.listar() if t.data]
        intervalo = self.intervalo_consulta()
        if intervalo is not None:
            inicio, fim = intervalo
            tarefas = [t for t in tarefas if inicio <= converter_data(t.data) <= fim]
        if self.consulta == "período" and not tarefas:
            return ["Nenhuma tarefa nesse intervalo"]
        return [f"{numero}. {formatar_tarefa(tarefa)}"
                for numero, tarefa in enumerate(tarefas, start=1)]
