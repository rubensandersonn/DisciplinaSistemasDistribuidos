package clients;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Classe cliente TCP
 */
public class UDPClient {
  private static InetAddress host;
  private static int PORT = 1234;
  private static DatagramSocket datagramSocket;
  private static DatagramPacket inPacket, outPacket;
  private static byte[] buffer;

  public UDPClient(int port, String ip) {
    try {
      host = InetAddress.getByName(ip);
      PORT = port;
    } catch (UnknownHostException uhEx) {
      System.out.println("HOST ID not found.. ");
      System.exit(1);
    }
  }

  /**
   * Classe a ser invocada para conectar ao servidor
   */
  public void accessServer() {
    try {
      datagramSocket = new DatagramSocket();

      Scanner userEntry = new Scanner(System.in);
      String message = "", response = "";

      do {

        System.out.println("DIGITE O COMANDO :");
        System.out.println("UPTIME : tempo online do servidor UDP (em segundos)");
        System.out.println("NUMREQ : numero de requisicoes que o servidor recebeu");
        System.out.println("CLOSE : Encerrar a conexao");
        System.out.println("enter message :");

        message = userEntry.nextLine();

        if (!message.equals("CLOSE")) {

          if (message.intern() == "NUMREQ") {
            outPacket = new DatagramPacket(message.getBytes(), message.length(), host, PORT);

            datagramSocket.send(outPacket);
            buffer = new byte[256];
            inPacket = new DatagramPacket(buffer, buffer.length);

            datagramSocket.receive(inPacket);
            response = new String(inPacket.getData(), 0, inPacket.getLength());
            System.out.println(" \n SERVER-->>" + response);

          } else if (message.intern() == "UPTIME") {
            outPacket = new DatagramPacket(message.getBytes(), message.length(), host, PORT);

            datagramSocket.send(outPacket);
            buffer = new byte[256];
            inPacket = new DatagramPacket(buffer, buffer.length);

            datagramSocket.receive(inPacket);
            response = new String(inPacket.getData(), 0, inPacket.getLength());
            System.out.println(" \n SERVER-->>" + response);
          } else {
            System.out.println("COMANDO INVALIDO");
          }

        } else {
          userEntry.close();
        }

      } while (!message.equals("CLOSE"));
    } catch (IOException ioEx) {
      ioEx.printStackTrace();
    }

    finally {
      System.out.println("\n closing connection.... ");
      datagramSocket.close();
    }
  }
}