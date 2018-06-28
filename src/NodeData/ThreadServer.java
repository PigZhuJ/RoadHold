package NodeData;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static NodeData.DealWithTheReceivedData.dealWithTheReceiveArr;

public class ThreadServer extends Thread {
    private Socket socket = null;//the Socket link to this Class
    // 最大的缓冲区大小 未解析的数据流
    int MaxCacheLen = 500;
    // 未处理的数据
    byte[] Caches = new byte[MaxCacheLen];
    int CacheLen = 0;

    /**
     * constructor
     *
     * @param socket
     */
    public ThreadServer(Socket socket) {
        this.socket = socket;
    }

    int num = 0;
    ExecutorService servicePool = Executors.newFixedThreadPool(100);

    @Override
    public void run() {
        try {
            InetAddress address = socket.getInetAddress();
            System.out.println("The Client Address is :" + address);//output the address of Client
            InputStream is = socket.getInputStream();//get inputStream
            byte[] byteBuffer = new byte[256];
            int b = 0;
            while ((b = is.read(byteBuffer))!=-1) {
                System.out.println("b="+b);
                byte[] dataArray=AddBytes(byteBuffer, b);
                System.out.println("收到的数据包长： "+dataArray.length);
                System.out.println(Arrays.toString(dataArray));
                //开始处理包显示波形
                SaveReceivedData srd=new SaveReceivedData(dataArray);
                servicePool.execute(srd);
                dealWithTheReceiveArr(dataArray,socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public byte[] AddBytes(byte[] message, int bytesRead) {
        byte[] dataArr = null;
        //采集到的数据与缓冲数据整合----------------------------------
        byte[] bytes = new byte[1024];
        if (CacheLen > 0) {
            for (int j = 0; j < CacheLen; j++) {
                bytes[j] = Caches[j];
            }
        }

        //CacheLen = 0;
        for (int j = 0; j < bytesRead; j++) {
            bytes[j + CacheLen] = message[j];
        }

        int count = bytesRead + CacheLen;

        CacheLen = 0;
        //---------------------------------
        int i = 0;
        //循环遍历每个字符
        while (i < count - 1) {
            //判断是否找到包头
            if ((bytes[i] == 0x0A) && (bytes[i + 1] == 0x0D)) {
                int start = i;
                i += 2;
                boolean flagEnd = false;
                while (i < count) {
                    i++;
                    if (i == count) break;
                    //判断是否找到包尾
                    if ((bytes[i - 1] == 0x0D) && (bytes[i] == 0x0A)) {
                        //找到包信息
                        flagEnd = true;
                        byte[] b = new byte[i - start + 1];
                        for (int k = 0; k < (i - start + 1); k++) {
                            b[k] = bytes[start + k];
                        }
                        checkThePackage chp=new checkThePackage(b);
                        if(chp.checkPackage()){
                         dataArr = b;
                         return dataArr;
                        }else {
                            if (chp.checkTheLength()) //如果数据段中包含0d 0a 结尾帧 通过此处进行跳过
                            {
                                continue;
                            }
                            if (chp.checkTheCRC())
                            {
                               //提取出有错误的帧 不放进缓冲区中
                            }
                            else
                            {
                                //放入缓冲中
                                CacheAdd(bytes[start]);
                                CacheAdd(bytes[start + 1]);
                                i = start + 1;
                            }
                        }
                        break;
                    }
                }

                if (!flagEnd) {
                    //放入缓冲中
                    for (int kk = start; kk < i; kk++) {
                        CacheAdd(bytes[kk]);
                    }
                }

            }//未找到包头
            else {
                //放入缓冲中
                CacheAdd(bytes[i]);
            }
            i++;
        }

        while (i < count) {
            //放入缓冲中
            CacheAdd(bytes[i]);
            i++;
        }
        return dataArr;
    }

    private void CacheAdd(byte b) {

        if (CacheLen == MaxCacheLen) {
            for (int i = 0; i < MaxCacheLen - 1; i++) {
                Caches[i] = Caches[i + 1];
            }
            Caches[MaxCacheLen - 1] = b;
        } else {
            Caches[CacheLen] = b;
            CacheLen++;
        }
    }


}
