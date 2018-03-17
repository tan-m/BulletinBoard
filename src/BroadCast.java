import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class BroadCast extends Thread {

    String serverToBeSkipped;
    List<String> serverNameList;
    Article article;
    String rmiIP;
    int rmiPort;


    public BroadCast(String rmiIP, int rmiPort, String myServerName, List<String> serverNameList, Article a) {

        this.rmiIP = rmiIP;
        this.rmiPort = rmiPort;
        this.serverToBeSkipped = myServerName;
        this.serverNameList = serverNameList;
        this.article = a;
    }

    // sends to everyone except serverToBeSkipped
    void sendToEveryone() throws RemoteException, MalformedURLException, NotBoundException {

        for (String s : serverNameList) {

            if (serverToBeSkipped != null && s.equals(serverToBeSkipped)) continue;

            ServerToServerInterface server = (ServerToServerInterface) Naming.lookup("//" + rmiIP + ":" + rmiPort + "/" + s);
            server.update(article);

        }

    }

    @Override
    public void run() {
        try {

            // adding delay to mimic real world network delays
            Thread.sleep(800);
            sendToEveryone();

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
