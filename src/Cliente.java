import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Cliente {
    public static void main(String[] args) throws IOException {
    final int MAX_TENTATIVAS = 30;
    final int INTERVALO_ESPERA = 2; // segundos

    Socket clienteSocket = null;
    BufferedReader entrada = null;
    PrintWriter saida = null;

    // Loop de tentativas de conexão
    for (int tentativa = 1; tentativa <= MAX_TENTATIVAS; tentativa++) {
        try {
            clienteSocket = new Socket("localhost", 12345);
            entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            saida = new PrintWriter(clienteSocket.getOutputStream(), true);

            String mensagemInicial = entrada.readLine();

            if (mensagemInicial != null && mensagemInicial.startsWith("OK")) {
                System.out.println("CONECTADO AO SERVIDOR");
                break;
            }

            // Servidor cheio — aguarda e tenta de novo
            System.out.println("[Tentativa " + tentativa + "/" + MAX_TENTATIVAS + "] Servidor cheio. Aguardando " + INTERVALO_ESPERA + "s...");
            clienteSocket.close();
            clienteSocket = null;

        } catch (IOException e) {
            System.out.println("[Tentativa " + tentativa + "/" + MAX_TENTATIVAS + "] Servidor indisponível. Aguardando " + INTERVALO_ESPERA + "s...");
        }

        // Se esgotou as tentativas, não dorme — sai do loop e encerra
        if (tentativa == MAX_TENTATIVAS) break;

        try {
            Thread.sleep(INTERVALO_ESPERA * 1000L);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            if (clienteSocket != null) clienteSocket.close();
            return;
        }
    }

    if (clienteSocket == null) {
        System.out.println("Não foi possível conectar após várias tentativas. Encerrando.");
        return;
    }

        // 6. Scanner para ler o teclado
        Scanner teclado = new Scanner(System.in);
        // 5. Variável de controle do loop do menu
        boolean executa = true;

        // 6. Loop principal do menu
        while (executa) {
            System.out.println("\nMENU: ");
            System.out.println("1 - Somar");
            System.out.println("2 - Subtrair");
            System.out.println("3 - Multiplicar");
            System.out.println("4 - Enviar Imagem");
            System.out.println("0 - Encerrar conexão");
            System.out.print("ESCOLHA UMA OPÇÃO: ");

            // 7. Lê a opção digitada
            String opcao = teclado.nextLine();

            // 8. Estrutura de decisão (o equivalente ao "match" do Python)
            switch (opcao) {
                case "1":
                    System.out.println("Somar selecionado.");
                    System.out.print("Digite o primeiro número: ");
                    String numero1Soma = teclado.nextLine();
                    System.out.print("Digite o segundo número: ");
                    String numero2Soma = teclado.nextLine();
                    // Formato: CODIGO|NUM1,NUM2
                    String mensagemSoma = "1|" + numero1Soma + "," + numero2Soma;
                    // Envia para o servidor
                    saida.println(mensagemSoma);
                    // Recebe a resposta
                    String respostaSoma = entrada.readLine();
                    System.out.println("Servidor: " + respostaSoma);
                    break;
                case "2":
                    System.out.println("Subtração selecionada.");
                    System.out.print("Digite o primeiro número: ");
                    String numero1Subtracao = teclado.nextLine();
                    System.out.print("Digite o segundo número: ");
                    String numero2Subtracao = teclado.nextLine();
                    // Formato: CODIGO|NUM1,NUM2
                    String mensagemSubtracao = "2|" + numero1Subtracao + "," + numero2Subtracao;
                    // Envia para o servidor
                    saida.println(mensagemSubtracao);
                    // Recebe a resposta
                    String respostaSubtracao = entrada.readLine();
                    System.out.println("Servidor: " + respostaSubtracao);
                    break;

                case "3":
                    System.out.println("Multiplicação selecionada.");
                    System.out.print("Digite o primeiro número: ");
                    String numero1Multiplicacao = teclado.nextLine();
                    System.out.print("Digite o segundo número: ");
                    String numero2Multiplicacao = teclado.nextLine();
                    // Formato: CODIGO|NUM1,NUM2
                    String mensagemMultiplicacao = "3|" + numero1Multiplicacao + "," + numero2Multiplicacao;
                    // Envia para o servidor
                    saida.println(mensagemMultiplicacao);
                    // Recebe a resposta
                    String respostaMultiplicacao = entrada.readLine();
                    System.out.println("Servidor: " + respostaMultiplicacao);
                    break;

                case "4":
                    System.out.println("Envio de imagem selecionado.");
                    saida.println("4|");
                    String respostaImagem = entrada.readLine();
                    System.out.println("Servidor: " + respostaImagem);
                    break;

                case "0":
                    System.out.println("Encerrando conexão...");
                    // Envia o código de encerramento para o servidor
                    saida.println("0");
                    executa = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        // 9. Fechamento de recursos
        teclado.close();
        entrada.close();
        saida.close();
        clienteSocket.close();
        System.out.println("Conexão encerrada.");
    }
}