import java.rmi.RemoteException;

class SequentialServer extends Server{

    protected SequentialServer() throws RemoteException {
        super();
    }

    @Override
    public boolean post(IPAndPort ipPort, Article article) throws RemoteException {
        return false;
    }

    @Override
    public boolean reply(IPAndPort ipPort, int parentID, Article article) throws RemoteException {
        return false;
    }
}