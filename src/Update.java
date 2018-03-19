import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Update {

    public static void main (String args[]) {
        System.out.println("in main");
        try {
            ServerToServerInterface server = (ServerToServerInterface) Naming.lookup ( "//127.0.0.1:4000/Server1");

            Article a = new Article("h", "hello");

            server.update(a);

            System.out.println("after update");

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
