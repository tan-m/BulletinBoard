import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

class SequentialServer extends Server implements ServerToServerInterface {

    String rmiIP = "127.0.0.1";
    int rmiPort = 4000;
    protected SequentialServer() throws RemoteException {
        super();
    }

    protected SequentialServer(String rmiIP, int rmiPort) throws RemoteException {
        super();
        this.rmiIP = rmiIP;
        this.rmiPort = rmiPort;
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

    }


    public boolean post(Article article) throws RemoteException {

        try {
            // coordinator will always be Server0
            CoordinatorInterface coordinator = (CoordinatorInterface) Naming.lookup ( "//" + rmiIP+":"+rmiPort+ "/Server0");
            System.out.println("Here");
            coordinator.replicate(article);

            System.out.println("article list is " + articleHashMap);

            return true;

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return false;

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