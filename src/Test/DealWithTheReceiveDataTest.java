package Test;

import java.io.IOException;
import static NodeData.DealWithTheReceivedData.dealWithTheReceiveDataPackage;

public class DealWithTheReceiveDataTest {
    public static void main(String[] args) throws IOException {
     byte[] b={0xA,0xD,0xD,0xd,0xC,0xc,0xA,0xD,0xc,0xC};
     dealWithTheReceiveDataPackage(b);
    }

    public static byte[] getInputArray() {
        byte[] blen = getBytes((short) 251);
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
