package teste;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(12345); //cria um socket servidor na porta 12345
        System.out.println("Servidor iniciado. Aguardando conexão...");

        //gera um número aleatório entre 1 e 100
        Random random = new Random();
        int numero = random.nextInt(100) + 1;

        Socket cliente = server.accept(); //aguarda um cliente se conectar
        System.out.println("Cliente conectado.");

        //envia uma mensagem para o cliente informando que o jogo começou
        cliente.getOutputStream().write(("Jogo de adivinhação iniciado! Tente adivinhar um número entre 1 e 100.\n").getBytes());

        boolean acertou = false;
        while (!acertou) {
            //lê a mensagem enviada pelo cliente
            byte[] buffer = new byte[1024];
            int bytesLidos = cliente.getInputStream().read(buffer);
            String mensagem = new String(buffer, 0, bytesLidos);

            //converte a mensagem para um número
            int palpite = Integer.parseInt(mensagem.trim());

            //compara o palpite do cliente com o número gerado pelo servidor
            if (palpite == numero) {
                acertou = true;
                cliente.getOutputStream().write(("Parabéns, você acertou o número!\n").getBytes());
            } else if (palpite < numero) {
                cliente.getOutputStream().write(("O número que você digitou é menor do que o número secreto. Tente novamente.\n").getBytes());
            } else {
                cliente.getOutputStream().write(("O número que você digitou é maior do que o número secreto. Tente novamente.\n").getBytes());
            }
        }

        cliente.close(); //fecha a conexão com o cliente
        server.close(); //encerra o servidor
    }
}

