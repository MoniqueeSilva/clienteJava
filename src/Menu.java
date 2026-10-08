import java.util.Scanner;

public class Menu {
    private static final Scanner teclado = new Scanner(System.in);

    public static String mostrarMenu() {
        System.out.println("\nMENU: ");
        System.out.println("1 - Somar");
        System.out.println("2 - Subtrair");
        System.out.println("3 - Multiplicar");
        System.out.println("4 - Enviar Imagem");
        System.out.println("0 - Encerrar conexão");
        System.out.print("ESCOLHA UMA OPÇÃO: ");
        return teclado.nextLine();
    }

    public static String criarMensagem(String opcao) {
        switch (opcao) {
            case "1":
                System.out.println("Somar selecionado.");
                return criarMensagemNumerica("1");
            case "2":
                System.out.println("Subtração selecionada.");
                return criarMensagemNumerica("2");
            case "3":
                System.out.println("Multiplicação selecionada.");
                return criarMensagemNumerica("3");
            case "4":
                System.out.println("Envio de imagem selecionado.");
                return "4|";
            case "5":
                System.out.println("Envio do objeto");
                Aluno a1 = new Aluno();
                a1.curso = "TSI";
                a1.nome = "ze";
                a1.matricula = "123";
                a1.serializaUmAluno();
                return "5|";
            
            case "0":
                System.out.println("Encerrando conexão...");
                return "0|";
            default:
                System.out.println("Opção inválida. Tente novamente.");
                return null;
        }
    }

    private static String criarMensagemNumerica(String codigo) {
        System.out.print("Digite o primeiro número: ");
        String numero1 = teclado.nextLine();
        System.out.print("Digite o segundo número: ");
        String numero2 = teclado.nextLine();
        return codigo + "|" + numero1 + "," + numero2;
    }
}
