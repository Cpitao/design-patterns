import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Receiver implements IReceiver{
    private static final int listeningPort = 1337;
    private ServerSocket listeningSocket = null;

    private static final LinkedList<Peer> peers = new LinkedList<>();
    private static final Semaphore peerSemaphore = new Semaphore(1);

    private static final Map<String, ArrayDeque<String>> messageQueueMap = new HashMap<>();
    private static final Semaphore messageQueueMapSemaphore = new Semaphore(1);
    private boolean running = true;
    private boolean restart = false;

    private String clientName;

//    private LinkedList<Thread> threads = new LinkedList<>();

    public Receiver(String clientName) {
        this.clientName = clientName;
    }

    public void start() {
        running = true;
        if (listeningSocket != null) {
            return;
        }

        try {
            listeningSocket = new ServerSocket(listeningPort);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (running) {
            try {
                new Thread(new ConnectionHandler(listeningSocket.accept(), clientName)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            listeningSocket.close();
            listeningSocket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (restart){
            restart=false;
            start();
        }
    }

    public void stop() {
        running = false;
        // connect to receiver to stop its block on .accept() and end it
        try {
            Socket s = new Socket("127.0.0.1", listeningPort);
            new PrintWriter(s.getOutputStream(), true).println("Q");
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, ArrayDeque<String>> popMessageQueue() {
        try {
            messageQueueMapSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        Map<String, ArrayDeque<String>> m = new HashMap<>();
        for (String name : messageQueueMap.keySet())
        {
            m.put(name, messageQueueMap.get(name));
        }

        messageQueueMap.clear();
        messageQueueMapSemaphore.release();
        return m;
    }

    public void restartSocket() {
        restart=true;
        stop();
    }

    public LinkedList<Peer> getPeers() {
        return peers;
    }

    public static class ConnectionHandler extends Thread{
        private Socket clientSocket;
        private BufferedReader in;
        private String name;

        public ConnectionHandler(Socket socket, String name) {
            clientSocket = socket;
            this.name = name;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                StringBuilder message = new StringBuilder("");
                String s;
                while ((s = in.readLine()) != null) {
                    message.append(s);
                }
                parseMessage(message.toString());
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private void parseMessage(String message) {
            switch(message.charAt(0)) {
                // Message
                case 'M' -> {
                    try {
                        messageQueueMapSemaphore.acquire();

                        peerSemaphore.acquire();
                        String name = null;
                        for (Peer p : peers) {
                            if (p.getIp().equals(clientSocket.getInetAddress().toString().substring(1)))
                            {
                                name = p.getName();
                                break;
                            }
                        }
                        peerSemaphore.release();

                        if (name == null)
                            name = "unknown sender, IP: " + clientSocket.getInetAddress().toString().substring(1);

                        if (!messageQueueMap.containsKey(name)) {
                            messageQueueMap.put(name, new ArrayDeque<>());
                        }
                        messageQueueMap.get(name).add(message.substring(1));

                        messageQueueMapSemaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // Hello
                case 'H' -> {
                    try {
                        if (message.charAt(1) != 'R') {
                            Socket replySocket = new Socket(clientSocket.getInetAddress(), listeningPort);
                            PrintWriter out = new PrintWriter(replySocket.getOutputStream(), true);
                            out.println("HR" + name);
                            replySocket.close();
                        }
                        peerSemaphore.acquire();
                        peers.add(new Peer(message.substring(2), clientSocket.getInetAddress().toString().substring(1)));
                        peerSemaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {}

                }
                // Bye
                case 'B' -> {
                    try {
                        peerSemaphore.acquire();
                        peers.removeIf(peer -> (peer.getName().equals(message.substring(1)) &&
                                peer.getIp().equals(clientSocket.getInetAddress().toString().substring(1))));
                        peerSemaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // Ping
                case 'P' -> {
                    try {
                        Socket replyToListener = new Socket(clientSocket.getInetAddress(), listeningPort);
                        PrintWriter out = new PrintWriter(replyToListener.getOutputStream(), true);
                        replyToListener.close();
                        if (message.charAt(1) != 'R')
                            out.println("PR" + name); // ping reply
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
