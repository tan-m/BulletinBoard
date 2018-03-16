import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.HashMap;

public class Coordinator extends Server {

  protected HashMap<Integer,Article> articleHashMap = null;
    protected Server() throws RemoteException {
      super();
      articleHashMap = new HashMap<Integer, Article>();
    }

    // Implement the uniqueID requested by the server
    public int getNextID() {
        System.out.println("hi");
    }

}
