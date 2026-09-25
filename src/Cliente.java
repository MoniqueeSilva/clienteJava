import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {
        // 1. Conexão com o servidor (mantida igual)
        Socket clienteSocket = new Socket("localhost", 12345);
        System.out.println("CONECTADO AO SERVIDOR");

        // 2. Scanner para ler a opção do teclado
        Scanner teclado = new Scanner(System.in);
        
        // 3. Variável de controle do loop do menu
        boolean executa = true;

        // 4. Loop principal do menu
        while (executa) {
            System.out.println("\nMENU: ");
            System.out.println("1 - Somar");
            System.out.println("2 - Subtrair");
            System.out.println("3 - Multiplicar");
            System.out.println("4 - Enviar Imagem");
            System.out.println("0 - Encerrar conexão");
            System.out.print("ESCOLHA UMA OPÇÃO: ");

            // 5. Lê a opção digitada
            String opcao = teclado.nextLine();

            // 6. Estrutura de decisão (o equivalente ao "match" do Python)
            switch (opcao) {
                case "1":
                    System.out.println("Somar selecionado.");
                    break;
                case "2":
                    System.out.println("Subtrair selecionado.");
                    break;
                case "3":
                    System.out.println("Multiplicar selecionado.");
                    break;
                case "4":
                    System.out.println("Envio de imagem selecionado.");
                    break;
                case "0":
                    System.out.println("Encerrando conexão...");
                    executa = false; // Muda a flag para sair do loop
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        // 7. Fechamento de recursos
        teclado.close();
        clienteSocket.close();
        System.out.println("Conexão encerrada.");
    }
}