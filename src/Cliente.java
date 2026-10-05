import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws IOException {
        // Chama a classe Conexao para conectar ao servidor
        Conexao.ConexaoServidor conexao = Conexao.conectarAoServidor();

        if (conexao == null) {
            System.out.println("Não foi possível conectar após várias tentativas. Encerrando.");
            return;
        }

        Socket clienteSocket = conexao.socket();
        BufferedReader entrada = conexao.entrada(); // Ler mensagem do servidor
        PrintWriter saida = conexao.saida(); // Escrever mensagem ao servidor 

        boolean executa = true;
        while (executa) {
            String opcao = Menu.mostrarMenu();
            String mensagem = Menu.criarMensagem(opcao); // Converte a opção numa mensagem de protocolo

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
