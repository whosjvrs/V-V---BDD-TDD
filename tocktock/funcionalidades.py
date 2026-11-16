"""Carrega automaticamente as funcionalidades de cada integrante.

Procura arquivos ``*.py`` em ``F*-F*-*/app/`` na raiz do repositório (exceto
``test_*.py`` e arquivos que começam com ``_``) e os importa. Cada arquivo se
registra em ``tocktock.extensoes``.
"""
import importlib.util
import sys
from pathlib import Path

RAIZ = Path(__file__).resolve().parent.parent
_carregado = False


def arquivos():
    for arquivo in sorted(RAIZ.glob("F*-F*-*/app/*.py")):
        if arquivo.name.startswith(("_", "test_")):
            continue
        yield arquivo


def carregar():
    global _carregado
    if _carregado:
        return
    _carregado = True
    for arquivo in arquivos():
        nome = f"tocktock_{arquivo.parent.parent.name.replace('-', '_')}_{arquivo.stem}"
        spec = importlib.util.spec_from_file_location(nome, arquivo)
        modulo = importlib.util.module_from_spec(spec)
        sys.modules[nome] = modulo
        spec.loader.exec_module(modulo)
