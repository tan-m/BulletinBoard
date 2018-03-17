import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Server extends UnicastRemoteObject implements  ClientInterface, ServerToServerInterface {

//  protected List<Article> articleList = null;
    protected Map<Integer,Article> articleHashMap = null;

    String rmiIP = "127.0.0.1";
    int rmiPort = 4000;

    protected Server() throws RemoteException {
        super();
        articleHashMap = new HashMap<>();
    }


    protected Server(String rmiIP, int rmiPort) throws RemoteException {
      super();
      this.rmiIP = rmiIP;
      this.rmiPort = rmiPort;
      articleHashMap = new HashMap<>();
    }


    @Override
    public void ping() {
        System.out.println("hi");
    }


    @Override
    public void update(Article a) throws RemoteException {

        articleHashMap.put(a.uID, a);
        if (a.parentID != -1) {
            Article parent = articleHashMap.get(a.parentID);
            parent.childList.add(a.uID);
        }

        System.out.println("in update map is " + articleHashMap);
    }


    @Override
    public List<String> read(List<Integer> articleList) throws RemoteException {
        return null;
    }

    @Override
    public Article choose(int id) throws RemoteException {
        return null;
    }


// if methods from the interface are not implemented they need not be declared abstract here.


}
