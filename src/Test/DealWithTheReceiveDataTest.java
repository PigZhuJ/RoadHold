package Test;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import static NodeData.DealWithTheReceivedData.dealWithTheReceiveArr;

public class DealWithTheReceiveDataTest {
    public static void main(String[] args) throws IOException {
        System.out.println(Arrays.toString(getBytes((short) 255)));
        System.out.println(Arrays.toString(getInputArray()));
        Socket socket=new Socket();
        dealWithTheReceiveArr(getInputArray(),socket);
        System.out.println(Arrays.toString("节点1".getBytes()));
    }

    public static byte[] getInputArray() {
        byte[] blen = getBytes((short) 241);
        byte[] b = new byte[255];
        for (int i = 0; i < b.length; i++) {
            b[i] = 0;
        }
        b[0] = 0xA;
        b[1] = 0xD;
        b[2] = blen[0];
        b[3] = blen[1];
        b[4] = 0x21;
        b[5] = 0;
        b[6] = -24;
        b[7] = -118;
        b[8] = -126;
        b[9] = -25;
        b[10] = -126;
        b[11] = -71;
        b[12] = 49;

        b[239] = 0xD;
        b[240] = 0xA;
        return b;
    }


    public static byte[] getBytes(short data) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        return bytes;
    }
}
