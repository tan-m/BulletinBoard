import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientInterface extends Remote {

    List<String> read () throws RemoteException;
    String choose (int id) throws RemoteException;
    boolean post (String title, String content) throws RemoteException;
    boolean reply (int parentID, String content) throws RemoteException;

}
