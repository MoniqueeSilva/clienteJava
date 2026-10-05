import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Conexao.ConexaoServidor conexao = Conexao.conectarAoServidor();

        if (conexao == null) {
            System.out.println("Não foi possível conectar após várias tentativas. Encerrando.");
            return;
        }

        Socket clienteSocket = conexao.socket();
        BufferedReader entrada = conexao.entrada();
        PrintWriter saida = conexao.saida();

        boolean executa = true;
        while (executa) {
            String opcao = Menu.mostrarMenu();
            String mensagem = Menu.criarMensagem(opcao);

            if (mensagem == null) {
                continue;
            }

            saida.println(mensagem);

            if (opcao.equals("0")) {
                executa = false;
            } else {
                String resposta = Conexao.receberMensagem(entrada);
                if (opcao.equals("4")) {
                    Imagem.processarImagem(resposta);
                } else {
                    System.out.println("Servidor: " + resposta);
                }
            }
        }

        entrada.close();
        saida.close();
        clienteSocket.close();
        System.out.println("Conexão encerrada.");
    }
}
