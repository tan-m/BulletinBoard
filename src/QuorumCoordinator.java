import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

class QuorumCoordinator extends Coordinator 
                        implements QuorumCoordinatorInterface{
  int nServers;
  protected QuorumCoordinator() throws RemoteException {
    super();
  }

  protected QuorumCoordinator(int nServers) throws RemoteException {
    super();
    this.nServers = nServers;
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
    for (int i = 0; i < nServers; i++ ) {
      if (i == 0)
        update(a);
      else {
        try {
          ServerToServerInterface server = (ServerToServerInterface) 
          Naming.lookup ( "//" + rmiIP+":"+rmiPort+ "/Server" + i);
          server.update(a);
        } catch (NotBoundException e) {
          e.printStackTrace();
        } catch (MalformedURLException e) {
          e.printStackTrace();
        }
      }
    }// end for of server list
  } // end replicate method 
}
