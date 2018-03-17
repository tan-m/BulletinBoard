import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Server extends UnicastRemoteObject implements  ClientInterface, ServerToServerInterface {

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
    public void update(Article a) throws RemoteException {

        System.out.println("article is " + a);

        System.out.println("in update");
        articleHashMap.put(a.uID, a);
        if (a.parentID != -1) {
            Article parent = articleHashMap.get(a.parentID);
            parent.childList.add(a.uID);
        }

        System.out.println("in update map is " + articleHashMap);
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
