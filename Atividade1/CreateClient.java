import java.util.Scanner;
import clients.*;

public class CreateClient {

  private static String IPTCP = "127.0.0.1";
  private static String IPUDP = "127.0.0.2";

  public static void main(String[] args) {
    System.out.println("Digite o tipo de servidor: ");
    System.out.println("- TCP: 0");
    System.out.println("- UDP: 1");

    int op = 1;
    Scanner userEntry;

    try {
      userEntry = new Scanner(System.in);
      op = userEntry.nextInt();

      if (op == 1) {
        UDPClient client = new UDPClient(4000, IPUDP);
        client.accessServer(); // into a new thread?
      } else if (op == 0) {
        TCPClient client = new TCPClient(4000, IPTCP);
        client.accessServer(); // into a new thread?
      } else {
        throw new Error("opção inválida");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}