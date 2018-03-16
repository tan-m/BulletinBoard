import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface ClientInterface extends Remote {

    List<String> read (IPAndPort ipPort, List<int> articleList) 
                                            throws RemoteException;
    Article choose (IPAndPort ipPort, int id) throws RemoteException;
    boolean post (IPAndPort ipPort, Article article) throws RemoteException;
    boolean reply (IPAndPort ipPort, int parentID, Article article) 
                                            throws RemoteException;

}
