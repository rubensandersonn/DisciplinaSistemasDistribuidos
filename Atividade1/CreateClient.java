
/**
 * @author Rubens Anderson de Sousa Silva
 */

import java.util.Scanner;
import clients.*;

/**
 * Classe que cria um cliente de forma dinâmica com os IPs e Portas disponíveis.
 */
public class CreateClient {

  private static String IPTCP = "127.0.0.1";
  private static String IPUDP = "127.0.0.2";
  private static int PORTTCP = 4000;
  private static int PORTUDP = 4000;

  public static void main(String[] args) {
    System.out.println("Digite o tipo de servidor: ");
    System.out.println("- TCP [IP:" + IPTCP + " | PORT:" + PORTTCP + "] - 0");
    System.out.println("- UDP [IP:" + IPUDP + " | PORT:" + PORTUDP + "] - 1");

    int op = 1;
    Scanner userEntry;

    try {
      userEntry = new Scanner(System.in);
      op = userEntry.nextInt();

      if (op == 1) {
        UDPClient client = new UDPClient(PORTUDP, IPUDP);
        client.accessServer(); // into a new thread?
      } else if (op == 0) {
        TCPClient client = new TCPClient(PORTTCP, IPTCP);
        client.accessServer(); // into a new thread?
      } else {
        throw new Error("opção inválida. ESPERADO: '0' ou '1'");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}