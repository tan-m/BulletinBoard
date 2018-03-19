import java.util.List;
import java.util.Map;
import java.rmi.Naming;
import java.util.Random;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;

class QuorumCoordinator extends Coordinator 
                        implements QuorumCoordinatorInterface {
  int nServers, readQuorum, writeQuorum;
  int[] readQList, writeQList;
  Thread synchThread = null;

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
    readQList = random.ints(1,nServers).distinct().limit(readQuorum).toArray();
    writeQList = random.ints(1,nServers).distinct().limit(writeQuorum).toArray();
    //Make an assumption that coordinator is always updated.
    readQList[0] = 0;
    writeQList[0] = 0;

    //create a thread to frequently synch the servers
    synchThread = new Thread("Synch Thread") {
      public void run() {
        try {
          synch();
        } catch(Exception e) {
          e.printStackTrace();
        }
      }
    };
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
    for (int index = 0; index < writeQList.length; index++ ) {
      int i = writeQList[index];
      if (i == 0) update(a);
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

  // Implement the consensusChoose
  public String consensusChoose(int id) throws RemoteException {
    String content = null;
    int uID = readID();
    //Check if the ID requesting is within bounds of ID
    if(id > uID || id < 0 )
      return null;

    for( int i = 0 ; i < readQList.length; i++) {
      int s = readQList[i];
      if( s == 0 )  
        content = choose(id);
      else 
        try {
          ServerToServerInterface server = (ServerToServerInterface) 
          Naming.lookup ( "//" + rmiIP+":"+rmiPort+ "/Server" + s);
          String localContent = server.localChoose(uID, id);
        } catch (NotBoundException e) {
          e.printStackTrace();
        } catch (MalformedURLException e) {
          e.printStackTrace();
        } catch( Exception e) {
          System.out.println("Haven't updated the server yet");
        }
    } // end the read quorum

    return content;
  }

  // Implement the consensusRead
  public List<String> consensusRead() throws RemoteException {
    List<String> titleList = null;

    for( int i = 0 ; i < readQList.length; i++) {
      int s = readQList[i];
      if( s == 0 ) 
        titleList = read();
      else 
        try {
          ServerToServerInterface server = (ServerToServerInterface) 
          Naming.lookup ( "//" + rmiIP+":"+rmiPort+ "/Server" + s);
          List<String> tList = server.localRead(readID());
        } catch (NotBoundException e) {
          e.printStackTrace();
        } catch (MalformedURLException e) {
          e.printStackTrace();
        } catch( Exception e) {
          System.out.println("Haven't updated the server yet");
        }
    } // end the read quorum

    return titleList;
  }
  // Synch all the servers frequently
  public void synch() throws RemoteException {

    Map<Integer,Article> articleHashMap = getHashMap();
    for( int i = 1; i < nServers; i++) {
      try {
        ServerToServerInterface server = (ServerToServerInterface) 
        Naming.lookup ( "//" + rmiIP+":"+rmiPort+ "/Server" + i);
        boolean updated = server.synchPast(articleHashMap);
        if (!updated)
          System.out.println("Failed to synch the past update, will " +
          "try again");
      } catch (NotBoundException e) {
        e.printStackTrace();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch( Exception e) {
        System.out.println("Haven't updated the server yet");
      }
    }
  }
}
