import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

public abstract class Server extends UnicastRemoteObject implements 
                             ClientInterface, ServerToServerInterface {
    protected Map<Integer,Article> articleHashMap = null;

    protected Server() throws RemoteException {
      super();
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

      for( int i=0; i < size; i++) {
        Article article = articleHashMap.get(i);
        String title = article.title;
        while( article.parentID != -1) {
          title = "\t"+title;
          article = articleHashMap.get(article.parentID);
        }
        titleList.add(title);
      }

      return titleList;
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
