import java.rmi.Naming;

public class Client {

  //Constructor to connect to the Server with the IPAndPort
  public Client(IPAndPort ipPort) {
    Server server = (Server) Naming.lookup (ipPort + "/Server");
  }

  try {

    String ip = "127.0.0.1";
    int port = Integer.parseInt(args[1]);

    new ClientPingThread(server).start();
    boolean joinstatus = server.join(ip, port);
    System.out.println("join status :" + joinstatus);

    Thread.sleep(300);

  }catch (Exception e) {
    System.out.println("Client bug: " + e);
  }
}
