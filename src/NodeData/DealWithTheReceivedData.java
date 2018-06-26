package NodeData;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

import static NodeData.CreatFile.creatSaveDataFile;
import static NodeData.SavaData.*;

/**
 * 处理接收到的数据
 */
public class DealWithTheReceivedData {
    /**
     * deal with the byte Array that was received
     *
     * @param receiveByteArr
     */
    public static void dealWithTheReceiveArr(byte[] receiveByteArr, Socket socket) throws IOException {

        //beigin to find header of pakage
        int packagenum = 0;//测试用
        byte[] receiveByteMidArr;
        short packageLen = 0;//2 bytes
        //读数据包的长度
        packageLen = (short) ((0xff & receiveByteArr[2]) | (0xff00 & (receiveByteArr[3] << 8)));
        System.out.println("packageLen: " + packageLen);//打桩测试
        receiveByteMidArr = new byte[packageLen];//中间数组长度
        System.out.println("receiveByteMidArr : " + receiveByteMidArr.length);
        System.arraycopy(receiveByteArr, 11, receiveByteMidArr, 0, packageLen);//copy Array
        if (receiveByteArr[4] == 0x21) {
            System.out.println("Begin to deal with the Hello package!");
            dealWithTheHelloPakage(receiveByteMidArr, socket);//处理hello包
        } else if (receiveByteArr[4] == (byte) 0xD5) {
            dealWithTheReceiveDataPackage(receiveByteMidArr);//处理收到的数据包
            packagenum++;
            System.out.println("处理的包数目： " + packagenum);
        }else {
            System.out.println("包错误");
        }
    }


    /**
     * deal with the data package
     *
     * @param receiveByteArr
     */
    private static void dealWithTheHelloPakage(byte[] receiveByteArr, Socket socket) throws IOException {
        byte[] DeviceNumberByteArr = new byte[8];
        System.arraycopy(receiveByteArr, 5, DeviceNumberByteArr, 0, 8);
        System.out.println(Arrays.toString(DeviceNumberByteArr));//打桩输出
        String DeviceNumber = DeviceNumberByteArr[0] + "," + DeviceNumberByteArr[1] + "," + DeviceNumberByteArr[2] + "," + DeviceNumberByteArr[3]
                + "," + DeviceNumberByteArr[4] + "," + DeviceNumberByteArr[5] + "," + DeviceNumberByteArr[6] + "," + DeviceNumberByteArr[7];
        System.out.println(DeviceNumber);//输出器件名字
        //创建文件
        String filePathName = creatSaveDataFile(DeviceNumber);
        sendConfigurePackage(DeviceNumber, socket);

    }

    /**
     * 发送配置信息
     *
     * @param socket
     */
    private static void sendConfigurePackage(String DeviceNumber, Socket socket) throws IOException {
        byte[] configArr;//生成的数组长度是33个字节
        configArr = getConfigurePackage(DeviceNumber);
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.write(configArr);
        System.out.println("已经发送给了Socket");
    }


    private static byte[] getConfigurePackage(String DeviceNumber) {
        byte[] generateConfigByte = new byte[33];
        generateConfigByte[0] = 0xA;
        generateConfigByte[1] = 0xD;
        generateConfigByte[2] = (byte) ((short) 33 & 0xff);
        generateConfigByte[3] = (byte) (((short) 33 & 0xff00) >> 8);
        generateConfigByte[4] = (byte) 0xB1;
        String[] len = DeviceNumber.split(",");
        System.out.println(len.length);
        generateConfigByte[5] = Byte.parseByte(len[0]);
        generateConfigByte[6] = Byte.parseByte(len[1]);
        generateConfigByte[7] = Byte.parseByte(len[2]);
        generateConfigByte[8] = Byte.parseByte(len[3]);
        generateConfigByte[9] = Byte.parseByte(len[4]);
        generateConfigByte[10] = Byte.parseByte(len[5]);
        generateConfigByte[11] = Byte.parseByte(len[6]);
        generateConfigByte[12] = Byte.parseByte(len[7]);
        generateConfigByte[18] = 0x01;
        generateConfigByte[21] = (byte) ((short) 1600 & 0xff);
        generateConfigByte[22] = (byte) (((short) 1600 & 0xff00) >> 8);
        generateConfigByte[31] = 0xD;
        generateConfigByte[32] = 0xA;
        System.out.println(Arrays.toString(generateConfigByte));
        return generateConfigByte;
    }

    /**
     * deal with the hello package
     *
     * @param receiveByteArr
     */
    public  static void dealWithTheReceiveDataPackage(byte[] receiveByteArr) throws IOException {
        for (int i = 0; i < receiveByteArr.length; i+=2) {
            if(i%6==0){
                System.out.println(receiveByteArr[i]+","+receiveByteArr[i+1]);
                short xData=(short) ((0xff & receiveByteArr[i]) | (0xff00 & (receiveByteArr[i+1] << 8)));
                saveDataArrX(xData);
                System.out.println("Datax="+xData);
                getWaveData.xData=xData;
            }else if(i%6==2){
                System.out.println(receiveByteArr[i]+","+receiveByteArr[i+1]);
                short yData=(short) ((0xff & receiveByteArr[i]) | (0xff00 & (receiveByteArr[i+1] << 8)));
                saveDataArrY(yData);
                System.out.println("Datay="+yData);
                getWaveData.yData=yData;
            }else if(i%6==4){
                System.out.println(receiveByteArr[i]+","+receiveByteArr[i+1]);
                short zData=(short) ((0xff & receiveByteArr[i]) | (0xff00 & (receiveByteArr[i+1] << 8)));
                saveDataArrZ(zData);
                System.out.println("Dataz="+zData);
                getWaveData.zData=zData;
            }
        }
    }

}
