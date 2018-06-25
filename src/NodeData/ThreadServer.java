package NodeData;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

import static NodeData.DealWithTheReceivedData.dealWithTheReceiveArr;

public class ThreadServer extends Thread {
    private Socket socket=null;//the Socket link to this Class

    /**
     * constructor
     * @param socket
     */
    public ThreadServer(Socket socket) {
        this.socket=socket;
    }
    int num=0;

    @Override
    public void  run() {
        try {
            InetAddress address=socket.getInetAddress();
            System.out.println("The Client Address is :"+address);//output the address of Client
            InputStream is=socket.getInputStream();//get inputStream
            DataInputStream dataInputStream=new DataInputStream(is);
            byte[] receiveByteArr=new byte[255];//get the byte Array that received
            while (dataInputStream.read(receiveByteArr)!=-1){
                System.out.println(Arrays.toString(receiveByteArr));//Output the array which received
//                dealWithTheReceiveArr(receiveByteArr,socket);//deal with the package
                num++;
                System.out.println("num: "+num);
            }
            dataInputStream.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
