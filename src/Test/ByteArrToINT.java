package Test;

import java.util.Arrays;

public class ByteArrToINT {
    public static void main(String[] args) {
        ByteArrToINT ba=new ByteArrToINT();
        byte[] b= ba.getBytes((short) 1024);
        System.out.println(Arrays.toString(b));
        System.out.println();
        System.out.println(  (0xff & b[0]) | (0xff00 & (b[1] << 8)));
    }

    public  byte[] getBytes(short data)
    {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        return bytes;
    }


}
