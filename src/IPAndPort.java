/* Used to store the IP and Port of a particular machine
 * Use case is to send the client, the Server IP and Port for connection
 */

import java.io.Serializable;

public class IPAndPort implements Serializable {
    String ip;
    int port;
    IPAndPort (String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String toString() {
        return ip+":"+port;
    }

    @Override
    public boolean equals(Object obj1) {
        if(obj1==null || !(obj1 instanceof IPAndPort))
            return false;
        IPAndPort obj = (IPAndPort) obj1;
        return ip.equals(obj.ip) && obj.port == port;
    }

    @Override
    public int hashCode() {
        return ip.hashCode() + port;
    }
}
