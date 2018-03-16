import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer {
    public static void main(String args[]) throws Exception {
        Server ser = new SequentialServer();
        Server ser1 = new SequentialServer();

        Registry localRegistry = LocateRegistry.createRegistry(4000);
        localRegistry.bind("Server", ser);
        localRegistry.bind("Server1", ser1);


        System.out.println("server started");

    }
}