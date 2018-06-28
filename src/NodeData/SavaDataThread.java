package NodeData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SavaDataThread extends Thread {
    public int[] xDataArr;
    public int[] yDataArr;
    public int[] zDataArr;

    public SavaDataThread(int[] xDataArr, int[] yDataArr, int[] zDataArr) {
        this.xDataArr = xDataArr;
        this.yDataArr = yDataArr;
        this.zDataArr = zDataArr;
    }

    @Override
    public void run() {
        try {
            savexyzData(xDataArr,yDataArr,zDataArr);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private synchronized void savexyzData(int[] xDataArr, int[] yDataArr, int[] zDataArr) throws IOException {
        BufferedWriter bw=new BufferedWriter(new FileWriter("testxyz.txt",true));
            for (int i = 0; i < xDataArr.length; i++) {
            String s=""+Integer.toHexString(xDataArr[i])+"  "+Integer.toHexString(yDataArr[i])+"  "+Integer.toHexString(zDataArr[i]);
            bw.write(s);
            bw.write('\n');
        }
        bw.close();
    }

}
