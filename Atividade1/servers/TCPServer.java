package servers;

import java.io.*;
import java.net.*;

public class TCPServer {
  // private static final int PORT = 1234;
  private static ServerSocket TCPSocket;

  public TCPServer(String ip, int port) {
    System.out.println("creating server port " + port + " and IP: " + ip);
    try {
      InetAddress address = InetAddress.getByName(ip);
      TCPSocket = new ServerSocket(port, 1, address);
      // TCPSocket.bind(new InetSocketAddress(ip, port));

    } catch (SocketException sockEx) {
      System.out.println("unable to open ");
      sockEx.printStackTrace();
      System.exit(1);
    } catch (IOException sockEx) {
      System.out.println("Erro ao abrir o socket ");
      System.exit(1);
    }

  }

  // public static void main(String[] args) {

  // }

  public void start() {
    try {

      int numMessages = 0;

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

        if (msgFromClient.intern() == "REQNUM") {
          System.out.println("> REQNUM: " + numMessages);
          messageOut = Integer.toString(numMessages);
        } else if (msgFromClient.intern() == "UPTIME") {
          messageOut = "nudes";
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