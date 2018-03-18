import java.rmi.Remote;
import java.util.List;
import java.rmi.RemoteException;

public interface QuorumCoordinatorInterface extends Remote {
  void replicate(Article a) throws RemoteException;
  List<String> consensusRead() throws RemoteException;
  String consensusChoose(int id) throws RemoteException;
}
