import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.*;

public class Client {

    private static final int port = 1337;
    private String clientName;
    private NetworkingFacade nf;
    private volatile boolean run = true;

    public Client(String name) {
        clientName = name;
    }

    public void serve() {
        System.out.println("Usage:\n To: name[,names] - sends message to selected clients\n" +
                " all - sends message to all active nodes\n" +
                " list - lists active nodes\n" +
                " restart - restarts listening socket\n" +
                " load - loads all unread messages\n" +
                " quit - sends goodbye message and quits the chat\n");

        // start listening
        nf = new NetworkingFacade(clientName);
        nf.registerListener();
        nf.broadcastHello(clientName);
        // run a thread to print new messages

        // handle user input
        Scanner scanner = new Scanner(System.in);
        while (run) {
            String input = scanner.nextLine();
            handleInput(input);
        }
        System.out.println("Quitting...");
    }

    // handle user input
    private void handleInput(String input) {
        if (input.equals("quit")) {
            shutdown();
        }
        else if (input.startsWith("To:"))
        {
            String[] to = input.substring(3).strip().split(",");
            Peer[] peers = new Peer[to.length];
            int i=0;
            for (String s: to)
            {
                Peer p = resolveName(s);
                if (p != null)
                {
                    peers[i] = p;
                    i++;
                }
                else
                    System.out.println("Client " + s + " unavailable");
            }
            if (i == 0) return;
            System.out.print("Message: ");
            String message = new Scanner(System.in).nextLine();
            nf.sendMessage(peers, message);
        }
        else if (input.strip().equals("all"))
        {
            System.out.println("Message: ");
            String message = new Scanner(System.in).nextLine();
            nf.sendMessage(message);
        }
        else if (input.strip().equals("list"))
        {
            for (Peer p : nf.getActiveNodes())
            {
                System.out.println(" - " + p.getName());
            }
        }
        else if (input.strip().equals("restart"))
        {
            nf.restartSocket();
        }
        else if (input.strip().equals("load"))
        {
            Map<String, ArrayDeque<String>> messages = nf.popMessages();
            for (String name : messages.keySet())
            {
                System.out.println("From: " + name);
                for (String m : messages.get(name))
                {
                    System.out.println("> " + m);
                }
            }
        }
    }

    private void shutdown()
    {
        System.out.println("Shutting down...");
        nf.unregisterListener();
        nf.broadcastGoodbye(clientName);
        run = false;
    }

    private Peer resolveName(String name) {
        LinkedList<Peer> peers = nf.getActiveNodes();
        for (Peer p: peers) {
            if (p.getName().equals(name)) return p;
        }

        return null;
    }

}
