import java.util.List;
import java.rmi.Naming;
import java.util.Random;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;

class QuorumCoordinator extends Coordinator 
                        implements QuorumCoordinatorInterface{
  int nServers, readQuorum, writeQuorum;
  int[] readQList, writeQList;

  protected QuorumCoordinator() throws RemoteException {
    super();
  }

  protected QuorumCoordinator(int nServers) throws RemoteException {
    super();
    this.nServers = nServers;
    readQuorum = (int)( nServers/2 + 1);
    writeQuorum = (int)(nServers/2 + 1);
    //
    Random random = new Random();
    readQList = random.ints(0,nServers).distinct().limit(readQuorum).toArray();
    writeQList = random.ints(0,nServers).distinct().limit(writeQuorum).toArray();
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
