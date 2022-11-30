import java.io.IOException;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class NetworkingFacade implements INetworkingFacade {
    private Receiver receiver;
    private final int defaultPort = 1337;
    private String clientName;

    public NetworkingFacade(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public void broadcastHello(String name) {
        Sender sender = new Sender(defaultPort);
        try {
            sender.broadcastHello(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void broadcastGoodbye(String name) {
        Sender sender = new Sender(defaultPort);
        try {
            sender.broadcastGoodbye(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(Peer peer, String message) {
        if (peer == null) return;
        Sender sender = new Sender(defaultPort);
        try {
            sender.sendMessage(message, peer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(Peer[] peers, String message) {
        Sender sender = new Sender(defaultPort);
        try {
            sender.sendMessage(message, peers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message) {
        Sender sender = new Sender(defaultPort);
        LinkedList<Peer> llPeers = getActiveNodes();
        try {
            sender.sendMessage(message, llPeers.toArray(new Peer[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LinkedList<Peer> getActiveNodes() {
        return receiver.getPeers();
    }

    @Override
    public Receiver registerListener() {
        receiver = new Receiver(clientName);
        new Thread(receiver::start).start();
        return receiver;
    }

    @Override
    public void unregisterListener() {
        receiver.stop();
    }

    @Override
    public void restartSocket() {
        receiver.restartSocket();
    }

    public Map<String, ArrayDeque<String>> popMessages() {
        return receiver.popMessageQueue();
    }
}
