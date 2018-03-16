import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class StartServer {
    public static void main(String args[]) throws Exception {

        if (args.length != 2) {
            System.err.println("usage java <consistency> <number of servers>");
        }

        String consistency = args[0];
        int numberOfServers = Integer.parseInt(args[1]);

        Registry localRegistry = LocateRegistry.createRegistry(4000);

        List<Server> serverList = new ArrayList<>();


        if (consistency.equals("sequential")) {

            for (int i=0; i<numberOfServers; i++) {
                serverList.add(new SequentialServer());

            }

//            Server coordinator = new SequentialCoordinator();

            for(int i=0; i<numberOfServers; i++) {
                localRegistry.bind("Server"+i, serverList.get(i));
            }

            System.out.println("server started");
        }




    }
}