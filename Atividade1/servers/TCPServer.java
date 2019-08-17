package servers;

import java.io.*;
import java.net.*;

/**
 * Classe servidor com protocolo TCP
 */
public class TCPServer {

  private static ServerSocket TCPSocket;

  public TCPServer(String ip, int port) {
    System.out.println("creating server port " + port + " and IP: " + ip);
    try {
      InetAddress address = InetAddress.getByName(ip);
      TCPSocket = new ServerSocket(port, 1, address);

    } catch (SocketException sockEx) {
      System.out.println("unable to open ");
      sockEx.printStackTrace();
      System.exit(1);
    } catch (IOException sockEx) {
      System.out.println("Erro ao abrir o socket ");
      System.exit(1);
    }

  }

  /**
   * inicia o servidor com IP e Porta passados
   */
  public void start() {
    try {

      int numMessages = 0;
      long initialTime = System.currentTimeMillis();

      Socket cliente = TCPSocket.accept();
      ObjectOutputStream msgOutput = new ObjectOutputStream(cliente.getOutputStream());
      ObjectInputStream msgInput = new ObjectInputStream(cliente.getInputStream());

      do {

        String msgFromClient = (String) msgInput.readObject();

        System.out.print(cliente.getInetAddress() + " | " + cliente.getPort());
        System.out.print(" : ");
        System.out.println(msgFromClient);

        String messageOut = "";

        numMessages++;

        if (msgFromClient.intern() == "NUMREQ") {
          System.out.println("> NUMREQ: " + numMessages);
          messageOut = Integer.toString(numMessages);
        } else if (msgFromClient.intern() == "UPTIME") {
          messageOut = Long.toString((System.currentTimeMillis() - initialTime) / 1000);
        }

        if (msgFromClient.intern() == "CLOSE") {

          break;
        }

        msgOutput.writeObject(messageOut);
        msgOutput.flush();
      } while (true);
    } catch (Exception ioEx) {
      ioEx.printStackTrace();
    } finally {
      System.out.println("\n Closing connection...");
      try {
        TCPSocket.close();

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}