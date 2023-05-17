package teste;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Socket servidor = new Socket("localhost", 12345); //conecta ao servidor na porta 12345
        System.out.println("Conectado ao servidor.");

        //lê a mensagem enviada pelo servidor
        byte[] buffer = new byte[1024];
        int bytesLidos = servidor.getInputStream().read(buffer);
        String mensagem = new String(buffer, 0, bytesLidos);
        System.out.println(mensagem);

        Scanner scanner = new Scanner(System.in);
        boolean acertou = false;
        while (!acertou) {
            System.out.print("Digite um número: ");
            int palpite = scanner.nextInt();

            //envia o palpite para o servidor
            servidor.getOutputStream().write((palpite + "\n").getBytes());

            //lê a mensagem enviada pelo servidor
            bytesLidos = servidor.getInputStream().read(buffer);
            mensagem = new String(buffer, 0, bytesLidos);
            System.out.println(mensagem);

            //verifica se o cliente acertou o número
            acertou = mensagem.contains("Parabéns");
        }

        scanner.close(); //fecha o scanner
        servidor.close(); //fecha a conexão com o servidor
    }
}

