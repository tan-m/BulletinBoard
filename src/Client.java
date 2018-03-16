import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

public class Client {

  //Constructor to connect to the Server with the IPAndPort
  public Client() {


  }

  List<String> getServerList() {
    List<String> list = new ArrayList<>();
    return list;
  }


  void startClient() {

    try {

      String rmiIP = "127.0.0.1";
      int rmiport = 4000;

      ClientInterface server = (ClientInterface) Naming.lookup ( "//" + rmiIP+":"+rmiport+ "/Server");

      ClientInterface server1 = (ClientInterface) Naming.lookup ( "//" + rmiIP+":"+rmiport+ "/Server1");

      server.ping();
      server1.ping();


    }catch (Exception e) {
      System.out.println("Client bug: " + e);
    }

  }

  public static void main(String args[]) {
    Client c = new Client();
    c.startClient();
  }


}
