// client - socket Thread

import controller.RecvHandler;
import controller.SendHandler;
import java.net.Socket;


public class Main {
    public static void main(String[] args) throws Exception {
        /** setting **/
        Socket socket = new Socket("192.168.0.35", 7890);
        System.out.println("<Open Client>");

        /** send thread **/
        Thread sendThread = new Thread(new SendHandler(socket));
        sendThread.start();

        /** recv thread **/
        Thread recvThread = new Thread(new RecvHandler(socket));
        recvThread.start();
    }
}
