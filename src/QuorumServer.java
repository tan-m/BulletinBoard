import java.net.MalformedURLException;
import java.rmi.Naming;
import java.util.List;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

class QuorumServer extends Server  {
  QuorumCoordinatorInterface coordinator = null;
  protected QuorumServer() throws RemoteException {
    super();
    try {
      coordinator = (QuorumCoordinatorInterface) 
      Naming.lookup ( "//" + rmiIP+":"+rmiPort+ "/Server0");
    } catch (NotBoundException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  public boolean post(Article article) throws RemoteException {
    // coordinator will always be Server0
    try {
      coordinator.replicate(article);
      return true;
    } catch(Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean post(String title, String content) throws RemoteException {
    Article article = new Article(title, content);
    return  post(article);
  }

  public boolean reply(int parentId, String content) throws RemoteException {
    Article article = new Article(content, parentId);
    return post(article);
  }

  public List<String> read() throws RemoteException {
    List<String> titleList = null;
    try {
      titleList = coordinator.consensusRead();
    } catch(Exception e) {
      e.printStackTrace();
    }
    return titleList;
  }

  public String choose(int id) throws RemoteException {
    String content = null;
    try {
      content = coordinator.consensusChoose(id);
    } catch(Exception e) {
      e.printStackTrace();
    }
    return content;
  }

}
