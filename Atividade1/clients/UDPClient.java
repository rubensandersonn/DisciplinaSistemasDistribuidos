package clients;

import java.io.*;
import java.net.*;
import java.util.*;


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

  public void accessServer() {
    try {
      datagramSocket = new DatagramSocket();

      Scanner userEntry = new Scanner(System.in);
      String message = "", response = "";

      do {

        System.out.println("enter message :");
        message = userEntry.nextLine();

        if (!message.equals("***CLOSE***")) {
          outPacket = new DatagramPacket(message.getBytes(), message.length(), host, PORT);

          datagramSocket.send(outPacket);
          buffer = new byte[256];
          inPacket = new DatagramPacket(buffer, buffer.length);

          datagramSocket.receive(inPacket);
          response = new String(inPacket.getData(), 0, inPacket.getLength());
          System.out.println(" \n SERVER-->>" + response);
        } else {
          userEntry.close();
        }

      } while (!message.equals("***CLOSE***"));
    } catch (IOException ioEx) {
      ioEx.printStackTrace();
    }

    finally {
      System.out.println("\n closing connection.... ");
      datagramSocket.close();
    }
  }
}