import java.rmi.RemoteException;

class SequentialServer extends Server{

    protected SequentialServer(int nServers) throws RemoteException {
        super(nServers);
    }

    @Override
    public boolean post(String title, String comment) throws RemoteException {
      return false;
    }

    @Override
    public boolean reply(int parentID, String comment) throws RemoteException {
      return false;
    }
}
