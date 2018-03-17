import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientInterface extends Remote {

    public void ping() throws RemoteException;
    List<String> read (IPAndPort ipPort, List<Integer> articleList)
                                            throws RemoteException;
    Article choose (IPAndPort ipPort, int id) throws RemoteException;
    boolean post (String title, String content) throws RemoteException;
    boolean reply (int parentID, String content) throws RemoteException;


}
