import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TesteClientes {
    private static final String HOST = "localhost";
    private static final int PORTA = 12345;

    private static final int QUANTIDADE_CLIENTES = 10;
    public static void main(String[] args) {
        System.out.println("INICIANDO TESTE");
        System.out.println("Clientes: " + QUANTIDADE_CLIENTES);
        Thread[] clientes = new Thread[QUANTIDADE_CLIENTES];

        for (int i = 0; i < QUANTIDADE_CLIENTES; i++) {
            int idCliente = i + 1;
            clientes[i] = new Thread(() -> executarCliente(idCliente));
            clientes[i].start();
        }

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

            String mensagemInicial = entrada.readLine();

            if (mensagemInicial == null) {
                System.out.println(
                        "[Cliente " + idCliente + "] servidor fechou a conexão."
                );
                return;
            }

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

            saida.println("1|10,5");
            String resposta = entrada.readLine();
            System.out.println("[Cliente " + idCliente + "] soma: "+ resposta);

            saida.println("2|20,5");
            resposta = entrada.readLine();
            System.out.println("[Cliente " + idCliente + "] subtração: "+ resposta);

            saida.println("3|4,5");
            resposta = entrada.readLine();
            System.out.println("[Cliente " + idCliente + "] multiplicação: "+ resposta);

            Thread.sleep(3000);
            saida.println("0");
            System.out.println("[Cliente " + idCliente + "] encerrado.");

        } catch (IOException e) {
            System.out.println("[Cliente " + idCliente + "] erro: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
