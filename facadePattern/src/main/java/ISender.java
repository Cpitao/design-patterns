import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public interface ISender {

    void sendMessage(String message, Peer peer) throws IOException; // unicast
    void sendMessage(String message, Peer[] peers) throws IOException; // multicast

    void broadcast(String message) throws IOException;
    void broadcastHello(String name) throws IOException;
    void broadcastGoodbye(String name) throws IOException;

    void ping(Peer peer) throws IOException;
}
