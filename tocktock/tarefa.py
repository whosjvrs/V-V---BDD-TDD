from dataclasses import dataclass, field


@dataclass
class Tarefa:
    nome: str
    data: str
    # Dados extras de cada funcionalidade, ex.: {"prioridade": "Alta", "concluida": True}.
    campos: dict = field(default_factory=dict)
