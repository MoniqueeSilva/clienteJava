import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class Imagem {
    public static void processarImagem(String resposta) {
        if (resposta == null || !resposta.startsWith("IMAGEM|")) {
            System.out.println("Servidor: " + resposta); // Imprime como texto normal
            return;
        }

        String imagemBase64 = resposta.substring("IMAGEM|".length()); // Pega só o conteúdo Base64 e fatia a string 
        System.out.println("Imagem recebida em Base64.");
        System.out.println("Tamanho do Base64: " + imagemBase64.length() + " caracteres");

        try {
            byte[] imagemBytes = Base64.getDecoder().decode(imagemBase64); // Converte o texto de volta para bytes
            String caminhoImagem = "imagem_recebida.jpg";
            Files.write(Path.of(caminhoImagem), imagemBytes); // Abre/cria o arquivo em binário

            File arquivo = new File(caminhoImagem);
            System.out.println("Imagem salva em: " + arquivo.getAbsolutePath());

            // Executa o Chrome
            Runtime.getRuntime().exec(new String[] { "google-chrome", arquivo.getAbsolutePath() });
            System.out.println("Imagem aberta no Chrome.");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: o conteúdo recebido não é um Base64 válido.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar ou abrir a imagem: " + e.getMessage());
        }
    }
}
