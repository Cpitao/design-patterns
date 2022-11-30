import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;

public class Sender implements ISender{

    private int targetPort;

    public Sender(int targetPort) {
        this.targetPort = targetPort;
    }

    @Override
    public void sendMessage(String message, Peer peer) throws IOException {
        Socket socket = new Socket(peer.getIp(), targetPort);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println("M" + message);
        socket.close();
    }

    @Override
    public void sendMessage(String message, Peer[] peers) throws IOException {
        for (Peer p: peers) {
            sendMessage(message, p);
        }
    }

    @Override
    public void broadcast(String message) throws IOException {
        // I'd have done it using UDP and 'normal' broadcast, but it was supposed to be TCP only
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets))
            {
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                    String address = inetAddress.getHostAddress();
                    if (address.lastIndexOf('.') == -1) continue;
                    String addressBase = address.substring(0, address.lastIndexOf('.') + 1);


                    for (int i=0; i < 255; i++) {
                        if (isThisMyIpAddress(InetAddress.getByName(addressBase + i))) continue;

                        int finalI = i;
                        // try to ping many hosts with many threads to actually ever finish doing that
                        new Thread(() -> {
                            Socket socket;
                            try {
                                socket = new Socket(addressBase + finalI, targetPort);
                            } catch (IOException e) {
                                return;
                            }
                            PrintWriter out = null;
                            try {
                                out = new PrintWriter(socket.getOutputStream(), true);
                            } catch (IOException e) {
                                return;
                            }
                            out.println(message);
                            try {
                                socket.close();
                            } catch (IOException e) {}
                        }).start();
                    }
                }
            }
        }

    // v source: https://stackoverflow.com/questions/2406341/how-to-check-if-an-ip-address-is-the-local-host-on-a-multi-homed-system
    private static boolean isThisMyIpAddress(InetAddress addr) {
        // Check if the address is a valid special local or loop back
        if (addr.isAnyLocalAddress() || addr.isLoopbackAddress())
            return true; // Was local sub-net.

        // Check if the Non-local address is defined on any Local-interface.
        try {
            return NetworkInterface.getByInetAddress(addr) != null;
        } catch (SocketException e) {
            return false;
        }
    }

    @Override
    public void broadcastHello(String name) throws IOException {
        broadcast("H " + name);
    }

    @Override
    public void broadcastGoodbye(String name) throws IOException {
        broadcast("B" + name);
    }

    @Override
    public void ping(Peer peer) throws IOException {
        sendMessage("P", peer);
    }
}
