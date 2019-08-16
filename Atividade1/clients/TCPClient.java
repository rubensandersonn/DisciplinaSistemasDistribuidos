package clients;

import java.io.*;
import java.net.*;
import java.util.*;


public class TCPClient {
  private static InetAddress host;
  private static int PORT = 1234;
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

  public void accessServer() {
    try {
      TCPSocket = new Socket(host, PORT);

      Scanner userEntry = new Scanner(System.in);
      String message = "", response = "";

      ObjectOutputStream msgOutput = new ObjectOutputStream(TCPSocket.getOutputStream());
      ObjectInputStream msgInput = new ObjectInputStream(TCPSocket.getInputStream());

      do {

        System.out.println("enter message :");
        message = userEntry.nextLine();

        if (!message.equals("***CLOSE***")) {

          msgOutput.writeObject(message);
          msgOutput.flush();

          String msgFromServer = (String) msgInput.readObject();

          System.out.println(" \n SERVER-->>" + msgFromServer);
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