from tocktock.telas import TelaInicio


class App:
    def __init__(self, gerenciador):
        self.gerenciador = gerenciador
        self.tela_atual = TelaInicio(self)

    @property
    def tela(self):
        return self.tela_atual.renderizar()

    def receber(self, entrada):
        self.tela_atual.receber(entrada)

    def ir_para(self, tela):
        self.tela_atual = tela

    def voltar_ao_inicio(self):
        self.ir_para(TelaInicio(self))
