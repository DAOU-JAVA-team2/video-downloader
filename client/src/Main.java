// client - socket Thread please

import controller.ViewController;

import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
        /** socket setting **/
        Socket socket = new Socket("localhost", 7890);
        System.out.println("<Open Client>");

        /** controller **/
        new ViewController(socket);
    }
}
