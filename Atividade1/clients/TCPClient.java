
package clients;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Classe cliente TCP
 */
public class TCPClient {
  private static InetAddress host;
  private static int PORT;
  private static Socket TCPSocket;

  public TCPClient(int port, String ip) {
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
      TCPSocket = new Socket(host, PORT);

      Scanner userEntry = new Scanner(System.in);
      String message = "";

      ObjectOutputStream msgOutput = new ObjectOutputStream(TCPSocket.getOutputStream());
      ObjectInputStream msgInput = new ObjectInputStream(TCPSocket.getInputStream());

      do {

        System.out.println("DIGITE O COMANDO :");
        System.out.println("UPTIME : tempo online do servidor TCP (em segundos)");
        System.out.println("REQNUM : numero de requisicoes que o servidor recebeu");
        System.out.println("***CLOSE*** : Encerrar a conexao");
        System.out.println("enter message :");
        message = userEntry.nextLine();

        if (!message.equals("***CLOSE***")) {

          if (message.intern() == "REQNUM") {
            msgOutput.writeObject(message);
            msgOutput.flush();

            String msgFromServer = (String) msgInput.readObject();

            System.out.println(" \n SERVER-->>" + msgFromServer);
          } else if (message.intern() == "UPTIME") {

            msgOutput.writeObject(message);
            msgOutput.flush();

            String msgFromServer = (String) msgInput.readObject();
            System.out.println(" \n SERVER-->>" + msgFromServer);
          } else {
            System.out.println("COMANDO INVALIDO");
          }

        } else {
          userEntry.close();
          msgInput.close();
          msgOutput.close();
        }

      } while (!message.equals("***CLOSE***"));
    } catch (Exception ioEx) {
      ioEx.printStackTrace();
    }

    finally {
      System.out.println("\n closing connection.... ");
      try {
        TCPSocket.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}