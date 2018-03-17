import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class TestServer {

  public static void main(String args[]) throws Exception {
    if (args.length != 2) {
      System.err.println("usage java <consistency> <number of servers>");
      System.exit(0);
    }

    String consistencyProtocol = args[0].toLowerCase();
    int nServers = Integer.parseInt(args[1]);
    Registry localRegistry = LocateRegistry.createRegistry(4000);
    List<Server> serverList = new ArrayList<Server>();


    if (consistencyProtocol.equals("sequential")) {
      Server coordinator = SequentialCoordinator.getInstance(nServers);
      serverList.add(coordinator);
      for (int i=1; i<nServers; i++) {
        serverList.add(new SequentialServer(nServers));
      }

      for(int i=0; i<nServers; i++) {
        localRegistry.bind("Server"+i, serverList.get(i));
      }

      System.out.println(nServers-1 + " Server(s) and 1 Coordinator Server "+
                                  "started");
    }
  }
}
