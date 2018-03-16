import java.rmi.RemoteException;
import java.util.HashMap;

public abstract class Coordinator extends Server {

  protected HashMap<Integer,Article> articleHashMap = null;
    protected Coordinator() throws RemoteException {
      super();
      articleHashMap = new HashMap<Integer, Article>();
    }

    // Implement the uniqueID requested by the server
    public int getNextID() {

      System.out.println("hi");
      return 1;

    }

}
