package NodeData;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 监听端口，收集socket
 */
public class serverReceive {
    private int clientNumber = 0;//记录客户端数量
    private int portNumber = 8889;//监听8888号端口

    public void receiveDataFromNode() throws IOException {
        Socket socket = null;
        ServerSocket serverSocket = new ServerSocket(portNumber);//construct serverSocket listen the port 8888
        //loop wait until the client connected
        while (true) {
            System.out.println("stop here");
            socket = serverSocket.accept();//block until the client to connect
            System.out.println("have received the socket," + "the socket ipAddress is: " + socket.getInetAddress().getHostAddress());
            ThreadServer ts = new ThreadServer(socket);
            ts.start();
            clientNumber++;
            System.out.println(clientNumber);//output the number of Client
            InetAddress address = socket.getInetAddress();
            System.out.println("The Client Address is :" + address);//output the address of Client
        }
    }
}
