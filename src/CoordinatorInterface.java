import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// put only those methods which will be independent of the consistency.
// consistency dependent stuff put in new interface

public interface CoordinatorInterface extends Remote {

    int getNextID() throws RemoteException;


}
