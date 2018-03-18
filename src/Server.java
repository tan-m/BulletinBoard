import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Iterator;

public abstract class Server extends UnicastRemoteObject implements 
                             ClientInterface, ServerToServerInterface {
    protected Map<Integer,Article> articleHashMap = null;

    String rmiIP = "127.0.0.1";
    int rmiPort = 4000;

    protected Server() throws RemoteException {
        super();
        articleHashMap = new HashMap<>();
    }


    protected Server(String rmiIP, int rmiPort) throws RemoteException {
      super();
      this.rmiIP = rmiIP;
      this.rmiPort = rmiPort;
      articleHashMap = new HashMap<>();
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

        System.out.println("in update map is " + articleHashMap);
    }

  // Read function call takes a list of integers and returns titleList to client
    @Override
    public List<String> read() throws RemoteException {
      int size = articleHashMap.size();
      List<String> titleList = new LinkedList<String>();
      List<String> dupList = new LinkedList<String>();
      LinkedList<Integer>queue = new LinkedList<Integer>();
      boolean[] visited = new boolean[size];

      for( int i=0; i < size; i++) {
        Article article = articleHashMap.get(i);
        String content = null;
        String title = i + ". ";
        if(article.title == null) {
          content = article.content.length()<10?
                    article.content:article.content.substring(0,10);
          title = title + content;
        } else {
          visited[i] = true; 
          content = article.title;
          title = title + content;
          queue.add(i);
        }
        while( article.parentID != -1) {
          title = "\t"+title;
          article = articleHashMap.get(article.parentID);
        }
        titleList.add(title);
      }

      while( queue.size() != 0 ) {
        int s = queue.poll();
        dupList.add(titleList.get(s));
        Iterator<Integer> itr = articleHashMap.get(s).childList.iterator();
        while (itr.hasNext()) {
          int v = itr.next();
          if( !visited[v]) {
            visited[v] = true;
            queue.push(v);
            //dupList.add(titleList.get(v));
          }
        }
      }
      return dupList;
    }

  // Choose method takes an ID that returns the article to the client
    @Override
    public String choose(int id) throws RemoteException {
      String content = articleHashMap.get(id).content;
      if( content == null )
        System.out.println("Attempting to access values outside scope");
      return content;
    }
}
