import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SequentialCoordinatorInterface extends Remote {
    void replicate(Article a) throws RemoteException;
}
