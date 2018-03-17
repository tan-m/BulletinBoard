import java.rmi.RemoteException;
import java.util.HashMap;

public abstract class Coordinator extends Server implements CoordinatorInterface {


    int counter=0;
    protected Coordinator() throws RemoteException {
      super();
    }

    // Implement the uniqueID requested by the server
    public synchronized int getNextID() {
      return counter++;
    }

}