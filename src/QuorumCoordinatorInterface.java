import java.rmi.Remote;
import java.rmi.RemoteException;

public interface QuorumCoordinatorInterface extends Remote {
  void replicate(Article a) throws RemoteException;
}
