"""F08 - Tarefa recorrente (João Victor dos Reis)."""
import calendar
from datetime import datetime, timedelta

from tocktock import extensoes as ext

ext.campo("recorrência", "Definir recorrência",
          "Digite a recorrência (Nenhuma, Diária, Semanal, Mensal):", "Nenhuma")


@ext.validacao
def validar_recorrencia(valores, tarefa):
    if valores.get("recorrência") not in ["Nenhuma", "Diária", "Semanal", "Mensal"]:
        raise ValueError("Recorrência inválida. Use Nenhuma, Diária, Semanal ou Mensal.")
    if valores.get("recorrência") != "Nenhuma" and not valores.get("data"):
        raise ValueError("Tarefa recorrente precisa de data de vencimento.")


@ext.coluna
def mostrar_proxima(tarefa):
    recorrencia = tarefa.campos.get("recorrência", "Nenhuma")
    if recorrencia == "Nenhuma":
        return None
    data = datetime.strptime(tarefa.data, "%d/%m/%Y")
    if recorrencia == "Diária":
        proxima = data + timedelta(days=1)
    elif recorrencia == "Semanal":
        proxima = data + timedelta(days=7)
    else:
        ano = data.year + (1 if data.month == 12 else 0)
        mes = 1 if data.month == 12 else data.month + 1
        dia = min(data.day, calendar.monthrange(ano, mes)[1])
        proxima = data.replace(year=ano, month=mes, day=dia)
    return "Próxima: " + proxima.strftime("%d/%m/%Y")
