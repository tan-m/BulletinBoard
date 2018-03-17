import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.HashMap;

//Default naming of the servers, coordinator is Server0 by default
public abstract class Server extends UnicastRemoteObject implements ClientInterface  {

  protected HashMap<Integer,Article> articleHashMap = null;
  public int nServers;
  protected Server(int n) throws RemoteException {
    super();
    nServers = n;
    articleHashMap = new HashMap<Integer, Article>();
  }

    @Override
    public void ping() {
        System.out.println("hi");
    }

    @Override
    public List<String> read(IPAndPort ipPort, List<Integer> articleList) throws RemoteException {
        return null;
    }

    @Override
    public Article choose(IPAndPort ipPort, int id) throws RemoteException {
        return null;
    }

    @Override
    abstract public boolean post(IPAndPort ipPort, Article article) throws RemoteException;

    @Override
    abstract public boolean reply(IPAndPort ipPort, int parentID, Article article) throws RemoteException;

}
