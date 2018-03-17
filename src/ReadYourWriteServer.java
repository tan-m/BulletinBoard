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


    @Override
    public boolean post(String title, String content) throws RemoteException {
        Article article = new Article(title, content);
        return  post(article);

    }

    @Override
    public boolean reply(int parentId, String content) throws RemoteException {
        Article article = new Article(content, parentId);
        return post(article);
    }

}
