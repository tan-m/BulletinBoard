import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

class SequentialCoordinator extends Coordinator{

    List<String> serverList;


    protected SequentialCoordinator() throws RemoteException {
        super();
    }

    protected SequentialCoordinator(List<String> list) throws RemoteException {
        super();
        this.serverList = list;
    }

    public boolean post(Article article) throws RemoteException {

        this.replicate(article);
        return true;

    }

    @Override
    public boolean post(String title, String content) throws RemoteException {
        Article article = new Article(title, content);
        return  post(article);
    }

    @Override
    public boolean reply(int parentID, String content) throws RemoteException {
        Article article = new Article(content, parentID);
        return post(article);
    }

    @Override
    public void replicate(Article a) throws RemoteException {

        a.uID = getNextID();

        for (String s : serverList) {
            if (s.equals("Server0")) {

                // local call
                update(a);

            } else {
                try {

                    ServerToServerInterface server = (ServerToServerInterface) Naming.lookup ( "//" + rmiIP+":"+rmiPort+ "/" + s);
                    server.update(a);

                } catch (NotBoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}