import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        System.out.print("Name: ");
        String name = new Scanner(System.in).nextLine();

        Client client = new Client(name);
        client.serve();
    }
}
