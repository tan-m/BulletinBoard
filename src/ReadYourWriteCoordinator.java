import java.rmi.RemoteException;
import java.util.List;

public class ReadYourWriteCoordinator extends Coordinator {


    protected ReadYourWriteCoordinator() throws RemoteException {
        super();
    }

    String myServerName;
    List<String> serverNameList;

    protected ReadYourWriteCoordinator(String rmiIP, int rmiPort, String myServerName, List<String> serverNameList) throws RemoteException {
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

    @Override
    public boolean post(String title, String content) throws RemoteException {
        int uID = getNextID();
        Article article = new Article(title, content, uID, -1);
        return post(article);
    }

    @Override
    public boolean reply(int parentID, String content) throws RemoteException {
        int uID = getNextID();
        Article article = new Article(null, content, uID, parentID);
        return post(article);
    }

}