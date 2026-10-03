import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TesteEstresse {
    private static final String HOST = "localhost";
    private static final int PORTA = 12345;

    // Quantidade de clientes que serão simulados
    private static final int QUANTIDADE_CLIENTES = 10;
    public static void main(String[] args) {
        System.out.println("INICIANDO TESTE");
        System.out.println("Clientes: " + QUANTIDADE_CLIENTES);
        Thread[] clientes = new Thread[QUANTIDADE_CLIENTES];

        // Cria os clientes
        for (int i = 0; i < QUANTIDADE_CLIENTES; i++) {
            int idCliente = i + 1;
            clientes[i] = new Thread(() -> executarCliente(idCliente));
            clientes[i].start();
        }

        // Aguarda todos os clientes terminarem
        for (Thread cliente : clientes) {
            try {
                cliente.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Teste interrompido.");
            }
        }

        System.out.println("\nTESTE FINALIZADO");
    }

    private static void executarCliente(int idCliente) {
        try (
                Socket socket = new Socket(HOST, PORTA);
                PrintWriter saida = new PrintWriter(
                        socket.getOutputStream(), true
                );

                BufferedReader entrada = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                )
        ) {

            System.out.println(
                    "[Cliente " + idCliente + "] conectado."
            );

            // Recebe a mensagem inicial do servidor
            String mensagemInicial = entrada.readLine();

            if (mensagemInicial == null) {
                System.out.println(
                        "[Cliente " + idCliente + "] servidor fechou a conexão."
                );
                return;
            }

            // Verifica se o servidor recusou o cliente
            if (mensagemInicial.startsWith("ERRO")) {

                System.out.println(
                        "[Cliente " + idCliente + "] " + mensagemInicial
                );

                return;
            }

            System.out.println(
                    "[Cliente " + idCliente + "] servidor: "
                            + mensagemInicial
            );

            // TESTE DE SOMA
            saida.println("1|10,5");
            String resposta = entrada.readLine();
            System.out.println("[Cliente " + idCliente + "] soma: "+ resposta);

            // TESTE DE SUBTRAÇÃO
            saida.println("2|20,5");
            resposta = entrada.readLine();
            System.out.println("[Cliente " + idCliente + "] subtração: "+ resposta);

            // TESTE DE MULTIPLICAÇÃO
            saida.println("3|4,5");
            resposta = entrada.readLine();
            System.out.println("[Cliente " + idCliente + "] multiplicação: "+ resposta);

            // ENCERRAMENTO
            saida.println("0");
            System.out.println("[Cliente " + idCliente + "] encerrado.");

        } catch (IOException e) {
            System.out.println("[Cliente " + idCliente + "] erro: " + e.getMessage());
        }
    }
}