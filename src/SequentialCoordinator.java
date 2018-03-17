import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

class SequentialCoordinator extends Coordinator{

    List<String> serverList;
    String rmiIP = "127.0.0.1";
    int rmiPort = 4000;

    protected SequentialCoordinator() throws RemoteException {
        super();
    }

    protected SequentialCoordinator(List<String> list) throws RemoteException {
        super();
        this.serverList = list;
    }

    @Override
    public boolean post(String title, String content) throws RemoteException {
        return false;
    }

    @Override
    public boolean reply(int parentID, String content) throws RemoteException {
        return false;
    }

    @Override
    public void replicate(Article a) throws RemoteException {

        a.uID = getNextID();

        System.out.println("id is " + a.uID);
        System.out.println("list " + serverList);

        for (String s : serverList) {
            if (s.equals("Server0")) {

                articleHashMap.put(a.uID, a);
                if (a.parentID != -1) {
                    Article parent = articleHashMap.get(a.parentID);
                    parent.childList.add(a.uID);
                }

                System.out.println("primary updated");

            } else {
                try {

                    System.out.println("in coordinator. the string is");

                    ServerToServerInterface server = (ServerToServerInterface) Naming.lookup ( "//" + rmiIP+":"+rmiPort+ "/" + s);
                    System.out.println("error here ");

                    server.update(a);

                } catch (NotBoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}