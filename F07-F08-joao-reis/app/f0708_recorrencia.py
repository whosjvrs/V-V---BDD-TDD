"""F08 - Tarefa recorrente (João Victor dos Reis)."""
import calendar
from datetime import datetime, timedelta

from tocktock import extensoes as ext

FORMATO_DATA = "%d/%m/%Y"
NENHUMA = "Nenhuma"
ERRO_RECORRENCIA = "Recorrência inválida. Use Nenhuma, Diária, Semanal ou Mensal."
ERRO_SEM_DATA = "Tarefa recorrente precisa de data de vencimento."


def somar_um_mes(data):
    """Mesmo dia do mês seguinte; se o dia não existir, usa o último dia do mês."""
    ano, mes = (data.year + 1, 1) if data.month == 12 else (data.year, data.month + 1)
    ultimo_dia = calendar.monthrange(ano, mes)[1]
    return data.replace(year=ano, month=mes, day=min(data.day, ultimo_dia))


# Cada recorrência sabe calcular a próxima data a partir da atual.
PROXIMA_DATA = {
    "Diária": lambda data: data + timedelta(days=1),
    "Semanal": lambda data: data + timedelta(days=7),
    "Mensal": somar_um_mes,
}
RECORRENCIAS = [NENHUMA, *PROXIMA_DATA]

ext.campo("recorrência", "Definir recorrência",
          f"Digite a recorrência ({', '.join(RECORRENCIAS)}):", NENHUMA)


def proxima_ocorrencia(vencimento, recorrencia):
    """Data (texto dd/mm/aaaa) da próxima ocorrência, ou None se a tarefa não se repete."""
    calcular = PROXIMA_DATA.get(recorrencia)
    if calcular is None:
        return None
    return calcular(datetime.strptime(vencimento, FORMATO_DATA)).strftime(FORMATO_DATA)


@ext.validacao
def validar_recorrencia(valores, tarefa):
    recorrencia = valores.get("recorrência")
    if recorrencia not in RECORRENCIAS:
        raise ValueError(ERRO_RECORRENCIA)
    if recorrencia != NENHUMA and not valores.get("data"):
        raise ValueError(ERRO_SEM_DATA)


@ext.coluna
def mostrar_proxima(tarefa):
    proxima = proxima_ocorrencia(tarefa.data, tarefa.campos.get("recorrência", NENHUMA))
    return f"Próxima: {proxima}" if proxima else None
