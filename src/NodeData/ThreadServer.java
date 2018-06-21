package NodeData;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    @Override
    public void  run() {
        try {
            InputStream is=socket.getInputStream();//get inputStream
            DataInputStream dataInputStream=new DataInputStream(is);
            byte[] receiveByteArr=new byte[3072];//get the byte Array that received
            while (dataInputStream.read(receiveByteArr)!=-1){
                System.out.println(Arrays.toString(receiveByteArr));//Output the array which received
                dealWithTheReceiveArr(receiveByteArr);//deal with the
            }
            dataInputStream.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
