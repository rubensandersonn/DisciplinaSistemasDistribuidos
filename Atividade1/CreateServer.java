import java.util.Scanner;
import servers.*;

public class CreateServer {

  private static String IPTCP = "127.0.0.1";
  private static String IPUDP = "127.0.0.2";

  public static void main(String[] args) {
    System.out.println("Digite o tipo de servidor: ");
    System.out.println("- TCP: 0");
    System.out.println("- UDP: 1");

    Scanner userEntry;
    int op = 1;

    try {
      userEntry = new Scanner(System.in);
      op = userEntry.nextInt();

      if (op == 1) {
        UDPServer server = new UDPServer(IPUDP, 4000);
        server.start(); // into a new thread
      } else if (op == 0) {
        TCPServer server = new TCPServer(IPTCP, 4000);
        server.start(); // into a new thread
      } else {
        throw new Error("opção inválida");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}