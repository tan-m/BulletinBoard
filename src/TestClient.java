import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestClient {

  static List<ClientInterface> serverList = new ArrayList<ClientInterface>();  
  public static void main(String args[]) throws Exception {
    int nServers = 0;
    if (args.length != 1) 
      nServers = 3;
    else
      nServers = Integer.parseInt(args[0]);
    startClient(nServers);
    Random random = new Random();
    for( int i = 0; i< 100;i++);
  }

//Perform initiation of the client by checking if all servers could be reached
  static void startClient(int nServers) {
    try {
      String rmiIP = "127.0.0.1";
      int rmiport = 4000;
      for(int i = 0 ; i < nServers; i++) 
        serverList.add( (ClientInterface) Naming.lookup 
                      ( "//" + rmiIP+":"+rmiport+ "/Server"+i));
    }catch (Exception e) {
      System.out.println("Client bug: " + e);
    }
  }
}
