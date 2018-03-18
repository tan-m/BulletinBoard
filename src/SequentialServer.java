import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

class SequentialServer extends Server  {

    protected SequentialServer() throws RemoteException {
        super();
    }


    public boolean post(Article article) throws RemoteException {

        try {
            // coordinator will always be Server0
            SequentialCoordinatorInterface coordinator = (SequentialCoordinatorInterface) Naming.lookup ( "//" + rmiIP+":"+rmiPort+ "/Server0");
            coordinator.replicate(article);

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
