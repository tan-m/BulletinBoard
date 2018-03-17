import java.rmi.RemoteException;

public class SequentialCoordinator extends Coordinator {

  //Make this a singleton class
  private static SequentialCoordinator seqCoord = null; 
  // globalID is to update the ID globally for each article.
  private int globalID;
  private static int nServers;


  private SequentialCoordinator(int nServers) throws RemoteException {
    super(nServers);
    this.nServers = nServers;
    globalID = 0;
  }

  public static SequentialCoordinator getInstance(int nServers) 
                                                  throws RemoteException {
    if (seqCoord == null) 
      seqCoord = new SequentialCoordinator(nServers);
    return seqCoord;
  }

  public boolean post(Article article) throws RemoteException{
    return false;
  }

  public boolean reply(int parentID, Article article) 
                  throws RemoteException {
    return false;
  }

  public int getNextID() {
    globalID = globalID + 1;
    return globalID;
  }
}
