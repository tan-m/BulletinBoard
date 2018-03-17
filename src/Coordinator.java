import java.rmi.RemoteException;
import java.util.HashMap;

public abstract class Coordinator extends Server {

  protected HashMap<Integer,Article> articleHashMap = null;
    protected Coordinator(int nServers) throws RemoteException {
      super(nServers);
      articleHashMap = new HashMap<Integer, Article>();
    }

    // Implement the uniqueID requested by the server
    public int getNextID() {
      return 1;
    }

}
