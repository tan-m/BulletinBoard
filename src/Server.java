import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.HashMap;

//Default naming of the servers, coordinator is Server0 by default
public abstract class Server extends UnicastRemoteObject implements ClientInterface  {

  public HashMap<Integer,Article> articleHashMap = null;
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
    public List<String> read(List<Integer> articleList) throws RemoteException {
        return null;
    }

    @Override
    public Article choose(int id) throws RemoteException {
        return null;
    }

    @Override
    abstract public 
    boolean post(String title, String content) throws RemoteException;

    @Override
    abstract public 
    boolean reply(int parentID, String content) throws RemoteException;

}
