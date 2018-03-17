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
        String rmiIP = "127.0.0.1";
        int rmiPort = 4000;

        Registry localRegistry = LocateRegistry.createRegistry(4000);

        List<Server> serverList = new ArrayList<>();
        List<String> serverNameList = new ArrayList<>();
        for (int i=0; i<numberOfServers; i++) {
            serverNameList.add("Server"+i);
        }



        if (consistency.equals("sequential")) {

            // Server0 is the coordinator
            serverList.add( new SequentialCoordinator(serverNameList));
            for (int i=1; i<numberOfServers; i++) {
                serverList.add(new SequentialServer());
            }

            for(int i=0; i<numberOfServers; i++) {
                                    // "Server0" etc
                localRegistry.bind(serverNameList.get(i), serverList.get(i));
            }

            System.out.println("servers started");


        } else if (consistency.equals("readyourwrite")) {

            for(int i=0; i<numberOfServers; i++) {

                serverList.add(new ReadYourWriteServer(rmiIP, rmiPort, serverNameList.get(i), serverNameList ));
                localRegistry.bind(serverNameList.get(i), serverList.get(i));
            }

            System.out.println("servers started");

        }





    }
}