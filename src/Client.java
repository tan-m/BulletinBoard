import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;

public class Client {

  List<ClientInterface> serverList = null;
  int nServers;
  //Constructor to connect to the Server 
  public Client(int nServers) {
    serverList = new ArrayList<ClientInterface>();  
    this.nServers = nServers;
  }

//Perform initiation of the client by checking if all servers could be reached
  void startClient() {
    try {
      String rmiIP = "127.0.0.1";
      int rmiport = 4000;

      for(int i = 0 ; i < nServers; i++) 
        serverList.add( (ClientInterface) Naming.lookup ( "//" + rmiIP+":"+rmiport+ "/Server"+i));
    }catch (Exception e) {
      System.out.println("Client bug: " + e);
    }
  }

//Have an Infinite loop process which performs the actions on the server
  void performAction() throws InterruptedException, InputMismatchException, 
                              RemoteException {
    Scanner scanner = new Scanner(System.in);
    while(true) {
      //pick a random server to connect to and perform the chosen action
      int server = new Random().nextInt(nServers);

      System.out.print("\nPossible client options are \n1.Post an Article"+
      "\n2.Read a list of Articles\n3.Choose an article for Content\n4.Reply"+
      " to an article\nAny other option disconnects the client\n\nPick one" +
      " number between 1-4: ");
      
      int choice = scanner.nextInt();
      if( choice > 0 && choice < 5) 
        switch(choice) {
          case 1: // post
            System.out.println("Posting a new Article, enter a string, 1-10"+
            " characters are title, rest are content");
            scanner = new Scanner(System.in);
            String article = scanner.nextLine();
            serverList.get(server).post(article.substring(0,10), 
                                        article.substring(10));
            break;
            
          case 2: //Read
            System.out.println("Reading list of articles");
            List<String> titleList = serverList.get(server).read();
            scanner = new Scanner(System.in);
            int size = titleList.size();
            int paging = 5;
            
            for(int i=0; i < size; i++) {
              System.out.println(titleList.get(i));
              if( i % paging == paging -1 || i == size-1) {
                System.out.println("\n-------------------------\n"+
                "Press any key to continue\n-------------------------");
                String line = scanner.nextLine();
              }
            }
            break;

          case 3: // Choose
            System.out.println("Choosing an article, enter a positive integer"+
              " that is within bounds");
            int printArticle = scanner.nextInt();
            String content = serverList.get(server).choose(printArticle);
            System.out.println(content);
            break;

          case 4: // Reply
            System.out.println("Replying to an article, enter a number and a"+
            " string, which is the parent and content of the reply" );
            int parent = scanner.nextInt();
            content = scanner.nextLine();
            serverList.get(server).reply(parent, content);
            break;
      }

      else {
        System.out.println("Invalid choice, terminating client Interface");
        break;
      }
      Thread.sleep(1000);
    }
  }

// Start the clients
  public static void main(String args[]) {
    if(args.length != 1) {
      System.err.println("Please pass the number of servers as an argument");
      System.exit(0);
    }
    Client c = new Client(Integer.parseInt(args[0]));
    c.startClient();

    try {
      c.performAction();
    } catch(InterruptedException e) {
      e.printStackTrace();
    } catch(InputMismatchException e) {
      System.out.println("Client terminating because of invalid input");
    } catch (RemoteException e) {
      e.printStackTrace();
    }

  }
}
