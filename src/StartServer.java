import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.net.*;

public class StartServer {
  public static void main (String args[]) throws Exception {
    ServerImpl[3] server;
    for( int i = 0 ; i < 3; i++)
      server[i] = new ServerImpl(5000+i);
    System.out.println("Servers started");
  }
}
