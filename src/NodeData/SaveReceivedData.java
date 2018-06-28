package NodeData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class SaveReceivedData extends Thread {
    private byte[] receiveByteArr;

    public SaveReceivedData(byte[] receiveByteArr) {
        this.receiveByteArr = receiveByteArr;
    }

    @Override
    public void run() {
        try {
            save(receiveByteArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void  save(byte[] receiveByteArr) throws IOException {
        String s=ArraytoString(receiveByteArr);
        BufferedWriter bw=new BufferedWriter(new FileWriter("test.txt",true));
        bw.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(System.currentTimeMillis())+"   "+s);
        bw.write('\n');
        bw.close();
    }

    private String ArraytoString(byte[] DataArr) {
        String sx="";
        for (int i = 0; i <DataArr.length-1; i++) {
            sx=sx+DataArr[i]+",";
        }
        sx=sx+DataArr[DataArr.length-1];
        return sx;
    }
}
