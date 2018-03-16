import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public abstract class Server extends UnicastRemoteObject implements ClientInterface  {

    protected Server() throws RemoteException {
        super();
    }

    @Override
    public void ping() {
        System.out.println("hi");
    }
}