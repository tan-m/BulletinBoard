import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerToServerInterface extends Remote {
    void update(Article a) throws RemoteException;
}
