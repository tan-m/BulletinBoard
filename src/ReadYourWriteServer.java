import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class ReadYourWriteServer extends Server {

    String myServerName;
    List<String> serverNameList;

    protected ReadYourWriteServer(String rmiIP, int rmiPort, String myServerName, List<String> serverNameList) throws RemoteException {
        super(rmiIP, rmiPort);
        this.myServerName = myServerName;
        this.serverNameList = serverNameList;
    }

    public boolean post(Article a) throws RemoteException {
        update(a);
        new BroadCast(rmiIP, rmiPort, myServerName, serverNameList, a).start();
        // return to client immediately after updating your copy. Broadcast will happen in
        // a separate thread.
        return true;
    }

    int getIdFromCoordinator() throws RemoteException, NotBoundException, MalformedURLException {
        CoordinatorInterface coordinator = (CoordinatorInterface) Naming.lookup ( "//" + rmiIP+":"+rmiPort+ "/Server0");
        return coordinator.getNextID();
    }

    @Override
    public boolean post(String title, String content) throws RemoteException {
        try {
            int uID = getIdFromCoordinator();
            Article article = new Article(title, content, uID, -1);
            return  post(article);

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public boolean reply(int parentId, String content) throws RemoteException {
        try {
            int uID = getIdFromCoordinator();
            Article article = new Article(null, content, uID, parentId);
            return  post(article);

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
