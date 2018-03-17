import java.rmi.RemoteException;
import java.util.HashMap;

public abstract class Coordinator extends Server {

  protected Coordinator(int nServers) throws RemoteException {
    super(nServers);
  }

  abstract int getNextID();
}
