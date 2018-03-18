import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

class QuorumCoordinator extends Coordinator 
                        implements QuorumCoordinatorInterface{
  List<String> serverList;
  protected QuorumCoordinator() throws RemoteException {
    super();
  }

  protected QuorumCoordinator(List<String> list) throws RemoteException {
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
      if (s.equals("Server0")) 
        update(a);
      else {
        try {
          ServerToServerInterface server = (ServerToServerInterface) 
          Naming.lookup ( "//" + rmiIP+":"+rmiPort+ "/" + s);
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
