import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
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
      List<String> dupTitle = new LinkedList<String>();
      boolean[] visited = new boolean[size];
      Stack<Integer> stack = new Stack<>();

      for( int i=0; i < size; i++) {
        Article article = articleHashMap.get(i);
        String title = article.title;
        while( article.parentID != -1) {
          title = "\t"+title;
          article = articleHashMap.get(article.parentID);
        }
        titleList.add(title);
      }

      //run DFS
      int s = 0;
      stack.push(s);
      while(stack.empty() == false) {
        s = stack.peek();
        stack.pop();

        if(visited[s] == false) {
          dupTitle.add(titleList.get(s));
          visited[s] = true;
        }

        Iterator<Integer> itr = articleHashMap.get(s).childList.iterator();
        while (itr.hasNext()) {
          int v = itr.next();
          if( !visited[v])
            stack.push(v);
        }
      }

      return dupTitle;
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
