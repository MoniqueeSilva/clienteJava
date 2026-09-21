import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket clienteSocket = new Socket("localhost",12345);
        System.out.println("conectado");
        Scanner teclado = new Scanner(System.in);
        Scanner e = new Scanner(clienteSocket.getInputStream());
        PrintStream ESCREVE_NO_SOCKET = new PrintStream(clienteSocket.getOutputStream());
        while(teclado.hasNextLine()){
            //escrever no socket
            ESCREVE_NO_SOCKET.println(teclado.nextLine());
            //ler do socket
            String temp = e.nextLine();
            System.out.println(temp);
        }
        ESCREVE_NO_SOCKET.close();
        teclado.close();
        clienteSocket.close();
    }
}
