import java.rmi.RemoteException;
import java.util.HashMap;

public abstract class Coordinator extends Server implements CoordinatorInterface {

    int counter=0;
    protected Coordinator() throws RemoteException {
      super();
    }

    protected Coordinator(String rmiIP, int rmiPort) throws RemoteException {
        super(rmiIP, rmiPort);
    }

    // Implement the uniqueID requested by the server
    public synchronized int getNextID() {
      return counter++;
    }

    public synchronized int readID() {
      return counter;
    }
}
