import java.util.LinkedList;

public interface INetworkingFacade {


    void broadcastHello(String name);
    void broadcastGoodbye(String name);

    void sendMessage(Peer peer, String message); // one to one
    void sendMessage(Peer[] peers, String message); // one to many
    void sendMessage(String message); // broadcast to all connected nodes

    LinkedList<Peer> getActiveNodes();

    Receiver registerListener();
    void unregisterListener();

    void restartSocket();
}
