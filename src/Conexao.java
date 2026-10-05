import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Conexao {
    private static final int MAX_TENTATIVAS = 30;
    private static final int INTERVALO_ESPERA = 2;

    public static String receberMensagem(BufferedReader entrada) throws IOException {
        return entrada.readLine();
    }

    public static ConexaoServidor conectarAoServidor() throws IOException {
        Socket clienteSocket = null;
        BufferedReader entrada = null;
        PrintWriter saida = null;

        for (int tentativa = 1; tentativa <= MAX_TENTATIVAS; tentativa++) {
            try {
                clienteSocket = new Socket("localhost", 12345);
                entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                saida = new PrintWriter(clienteSocket.getOutputStream(), true);

                String mensagemInicial = receberMensagem(entrada);

                if (mensagemInicial != null && mensagemInicial.startsWith("OK")) {
                    System.out.println("CONECTADO AO SERVIDOR");
                    break;
                }

                System.out.println("[Tentativa " + tentativa + "/" + MAX_TENTATIVAS + "] Servidor cheio. Aguardando "
                        + INTERVALO_ESPERA + "s...");
                clienteSocket.close();
                clienteSocket = null;

            } catch (IOException e) {
                System.out.println("[Tentativa " + tentativa + "/" + MAX_TENTATIVAS
                        + "] Servidor indisponível. Aguardando " + INTERVALO_ESPERA + "s...");
            }

            if (tentativa == MAX_TENTATIVAS)
                break;

            try {
                Thread.sleep(INTERVALO_ESPERA * 1000L);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                if (clienteSocket != null)
                    clienteSocket.close();
                return null;
            }
        }

        if (clienteSocket == null) {
            return null;
        }

        return new ConexaoServidor(clienteSocket, entrada, saida);
    }

    public record ConexaoServidor(Socket socket, BufferedReader entrada, PrintWriter saida) {
    }
}
