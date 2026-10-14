import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class Aluno implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String matricula;
    private String curso;

    public Aluno(String nome, String matricula, String curso) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
    }

    public void serializaUmAluno(OutputStream destino) throws IOException {
        // 1. Serializa o objeto em memória para descobrir o tamanho
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(buffer);
        objOut.writeObject(this);
        objOut.flush();
        byte[] dados = buffer.toByteArray();

        // 2. Monta o cabeçalho "5|TAMANHO\n"
        String cabecalho = "5|" + dados.length + "\n";

        // 3. Envia o cabeçalho e os bytes
        destino.write(cabecalho.getBytes(StandardCharsets.UTF_8));
        destino.write(dados);
        destino.flush();

        System.out.println("Dados do aluno enviados (" + dados.length + " bytes).");
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return curso;
    }
}