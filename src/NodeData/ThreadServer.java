package NodeData;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadServer extends Thread {
    private Socket socket = null;//the Socket link to this Class
    private int CacheLen = 0;//缓冲区指针
    private int MaxCacheLen = 500;// 最大的缓冲区大小 未解析的数据流
    private byte[] Caches = new byte[MaxCacheLen];// 缓冲区
    ExecutorService saveDataservicePool = Executors.newFixedThreadPool(100);//存数据的线程池
    ExecutorService dealDataservicePool = Executors.newFixedThreadPool(100);//处理数据的线程池

    /**
     * constructor
     *
     * @param socket
     */
    public ThreadServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();//获取输入流
            byte[] byteBuffer = new byte[256];
            int readBytesNumber = 0;//读到的数据
            while ((readBytesNumber = is.read(byteBuffer))!=-1) {
                System.out.println("b="+readBytesNumber);
                byte[] dataArray=AddBytes(byteBuffer, readBytesNumber);
                System.out.println("收到的数据包长： "+dataArray.length+"  "+Arrays.toString(dataArray));
                //开始把数据存下来
                SaveReceivedData srd=new SaveReceivedData(dataArray);
                saveDataservicePool.execute(srd);
                DealWithTheReceivedDataThread drt=new DealWithTheReceivedDataThread(dataArray,socket);
                dealDataservicePool.execute(drt);
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
