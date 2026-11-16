"""Pontos de extensão do TockTock.

O núcleo (este pacote) não conhece nenhuma funcionalidade. Cada integrante
registra a sua a partir de um arquivo na própria pasta, por exemplo
``F07-F08-joao-reis/app/f0708_prioridade.py``, usando os decoradores abaixo.
Assim ninguém precisa editar os arquivos do núcleo e não há conflitos de merge.
"""
from dataclasses import dataclass

# Funções (tela) -> {tecla: (rótulo, ação)} com opções extras de cada tela.
OPCOES_INICIO = []
OPCOES_TAREFA = []

# Funções (app) -> list[str] com linhas extras no topo da tela inicial (ex.: "Filtro: Alta").
LINHAS_INICIO = []

# Funções (tarefas, app) -> tarefas. Ordenam ou filtram a lista da tela inicial.
VISOES = []

# Funções (tarefa) -> str | None. Colunas extras de cada linha da lista.
COLUNAS = []

# Funções (valores, tarefa) -> None. Lançam ValueError("mensagem") para recusar o salvamento.
# ``tarefa`` é None ao criar e a tarefa original ao editar.
VALIDACOES = []

# Funções (tarefa, app) -> None. Chamadas depois que uma tarefa é salva (criada ou editada).
APOS_SALVAR = []


@dataclass
class Campo:
    chave: str
    rotulo: str
    pergunta: str
    padrao: str = ""


# Campos extras do formulário de tarefa (além de nome e data).
CAMPOS = []


def opcao_inicio(funcao):
    OPCOES_INICIO.append(funcao)
    return funcao


def opcao_tarefa(funcao):
    OPCOES_TAREFA.append(funcao)
    return funcao


def linhas_inicio(funcao):
    LINHAS_INICIO.append(funcao)
    return funcao


def visao(funcao):
    VISOES.append(funcao)
    return funcao


def coluna(funcao):
    COLUNAS.append(funcao)
    return funcao


def validacao(funcao):
    VALIDACOES.append(funcao)
    return funcao


def apos_salvar(funcao):
    APOS_SALVAR.append(funcao)
    return funcao


def campo(chave, rotulo, pergunta, padrao=""):
    CAMPOS.append(Campo(chave, rotulo, pergunta, padrao))


def juntar_opcoes(fontes, tela):
    """Junta as opções registradas, acusando teclas repetidas entre funcionalidades."""
    opcoes = {}
    for fonte in fontes:
        for tecla, opcao in fonte(tela).items():
            if tecla in opcoes:
                raise RuntimeError(
                    f"tecla [{tecla}] usada duas vezes: '{opcoes[tecla][0]}' e '{opcao[0]}'")
            opcoes[tecla] = opcao
    return opcoes
