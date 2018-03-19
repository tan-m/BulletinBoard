import java.util.List;
import java.util.Map;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerToServerInterface extends Remote {
    void update(Article a) throws RemoteException;
    String localChoose(int version, int id) throws RemoteException;
    List<String> localRead(int version) throws RemoteException;
    boolean synchPast(Map<Integer,Article> aHM) throws RemoteException;
}
