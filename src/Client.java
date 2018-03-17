import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

public class Client {

  List<ClientInterface> serverList = null;
  int nServers;
  //Constructor to connect to the Server 
  public Client(int nServers) {
    serverList = new ArrayList<ClientInterface>();  
    this.nServers = nServers;
  }


  void startClient() {

    try {
      String rmiIP = "127.0.0.1";
      int rmiport = 4000;

      for(int i = 0 ; i < nServers; i++) {
        serverList.add( (ClientInterface) Naming.lookup ( "//" + rmiIP+":"+rmiport+ "/Server"+i));
        serverList.get(i).ping();
      }
    }catch (Exception e) {
      System.out.println("Client bug: " + e);
    }

  }

  public static void main(String args[]) {
    if(args.length != 1) {
      System.err.println("Please pass the number of servers as an argument");
      System.exit(0);
    }
    Client c = new Client(Integer.parseInt(args[0]));
    c.startClient();
  }

}
