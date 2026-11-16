from tocktock.app import App
from tocktock.gerenciador import GerenciadorTarefas


def main():
    app = App(GerenciadorTarefas())
    while True:
        print(app.tela)
        try:
            entrada = input("> ")
        except (EOFError, KeyboardInterrupt):
            print()
            return
        app.receber(entrada)


if __name__ == "__main__":
    main()
