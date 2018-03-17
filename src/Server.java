import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public abstract class Server extends UnicastRemoteObject implements  ClientInterface  {

//  protected List<Article> articleList = null;
    protected Map<Integer,Article> articleHashMap = null;

    protected Server() throws RemoteException {
      super();
      articleHashMap = new HashMap<>();
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

// if methods from the interface are not implemented they need not be declared abstract here.

}
