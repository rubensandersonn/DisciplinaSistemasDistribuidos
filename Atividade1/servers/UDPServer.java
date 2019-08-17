package servers;

import java.io.*;
import java.net.*;

/**
 * Classe servidor protocolo UDP
 */
public class UDPServer {
  // private static final int PORT = 1234;
  private static DatagramSocket datagramSocket;
  private static DatagramPacket inPacket, outPacket;
  private static InetAddress address;
  private static byte[] buffer;

  public UDPServer(String ip, int port) {
    System.out.println("creating server port " + port + " and IP: " + ip);
    try {
      address = InetAddress.getByName(ip);

      datagramSocket = new DatagramSocket(port, address);
    } catch (SocketException sockEx) {
      System.out.println("unable to open ");
      System.exit(1);
    } catch (UnknownHostException e) {
      System.out.println("IP Invalido");
      e.printStackTrace();
    }

  }

  /**
   * inicia o servidor no IP e Porta passados
   */

  public void start() {
    try {
      String messageIn = "", messageOut = "";

      int numMessages = 0;
      long initialTime = System.currentTimeMillis();

      InetAddress clientAddress = null;
      int clientPort = 0;
      do {
        buffer = new byte[256];
        inPacket = new DatagramPacket(buffer, buffer.length);

        datagramSocket.receive(inPacket);

        clientAddress = inPacket.getAddress();
        clientPort = inPacket.getPort();

        messageIn = new String(inPacket.getData(), 0, inPacket.getLength());

        System.out.print(clientAddress);
        System.out.print(" : ");
        System.out.println(messageIn);

        numMessages++;

        if (messageIn.intern() == "NUMREQ") {
          messageOut = Integer.toString(numMessages);
        } else if (messageIn.intern() == "UPTIME") {
          messageOut = Long.toString((System.currentTimeMillis() - initialTime) / 1000);

        }

        outPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(), clientAddress, clientPort);
        datagramSocket.send(outPacket);

      } while (true);
    } catch (IOException ioEx) {
      ioEx.printStackTrace();
    } finally {
      System.out.println("\n Closing connection...");
      datagramSocket.close();
    }
  }

}