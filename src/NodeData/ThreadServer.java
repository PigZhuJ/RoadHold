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
    private Socket socket = null;//the Socket link to this Class
    // 最大的缓冲区大小 未解析的数据流
    int MaxCacheLen = 1500;
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

    @Override
    public void run() {
        try {
            InetAddress address = socket.getInetAddress();
            System.out.println("The Client Address is :" + address);//output the address of Client
            InputStream is = socket.getInputStream();//get inputStream
            byte[] byteBuffer = new byte[255];
            int b = 0;
            while (true) {
                b = is.read(byteBuffer);
                byte[] dataArray=AddBytes(byteBuffer, b);
                System.out.println(Arrays.toString(dataArray));
                //开始处理包显示波形
                dealWithTheReceiveArr(dataArray,socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public byte[] AddBytes(byte[] message, int bytesRead) {
        byte[] dataArr = null;
        //采集到的数据与缓冲数据整合----------------------------------
        byte[] bytes = new byte[2048];
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
            if ((bytes[i] == 0xA) && (bytes[i + 1] == 0xD)) {
                int start = i;
                i += 2;
                boolean flagEnd = false;
                while (i < count) {
                    i++;
                    if (i == count) break;
                    //判断是否找到包尾
                    if ((bytes[i - 1] == 0xD) && (bytes[i] == 0xA)) {
                        //找到包信息
                        flagEnd = true;
                        byte[] b = new byte[i - start + 1];
                        for (int k = 0; k < (i - start + 1); k++) {
                            b[k] = bytes[start + k];

                        }
                        dataArr = b;
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
