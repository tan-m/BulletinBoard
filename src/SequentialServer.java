import java.rmi.RemoteException;

class SequentialServer extends Server{

    protected SequentialServer(int nServers) throws RemoteException {
        super(nServers);
    }

    @Override
    public boolean post(Article article) throws RemoteException {
        return false;
    }

    @Override
    public boolean reply(int parentID, Article article) throws RemoteException {
        return false;
    }
}
