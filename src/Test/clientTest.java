package Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static Test.DealWithTheReceiveDataTest.getInputArray;

public class clientTest extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i <1000 ; i++) {
                Socket socket = new Socket("127.0.0.1", 8888);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.write(getInputArray());
                System.out.println("have send");
                Thread.sleep(5000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
