package NodeData;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 处理接收到的数据
 */
public class DealWithTheReceivedData {
    /**
     * deal with the byte Array that was received
     * @param receiveByteArr
     */
    public static void dealWithTheReceiveArr(byte[] receiveByteArr){
        // the size of receiveByteArr[] is 3072
        //beigin to find header of pakage
        byte[] receiveByteMidArr;
        short packageLen=0;//2 bytes
        for (int i = 0; i <receiveByteArr.length-1; i++) {
            byte[] receiveMidBytes;
            if ((receiveByteArr[i]==0XA)&&(receiveByteArr[i+1]==0XD)){//find header of pakage
                //读数据包的长度
                packageLen= (short) ((0xff & receiveByteArr[i+2]) | (0xff00 & (receiveByteArr[i+3] << 8)));
                receiveByteMidArr=new byte[packageLen+4];//中间数组长度
                System.arraycopy(receiveByteArr,i,receiveByteMidArr,0,packageLen+4);//copy Array
                if(receiveByteMidArr[4]==0X21){
                    dealWithTheHelloPakage(receiveByteMidArr);//处理hello包
                    break;
                }else if(receiveByteMidArr[4]==0X50){
                    dealWithTheReceivePakage(receiveByteMidArr);//处理收到的数据包
                    break;
                }
            }
        }

    }
    /**
     * deal with the data package
     * @param receiveByteArr
     */
    private static void dealWithTheHelloPakage(byte[] receiveByteArr) {
        byte[] DeviceNumberByteArr=new byte[8];
        System.arraycopy(receiveByteArr,5,DeviceNumberByteArr,0,5);
        String DeviceNumber=new String(receiveByteArr, Charset.forName("utf-8"));
        System.out.println(DeviceNumber);//输出器件
    }

    /**
     * deal with the hello package
     * @param receiveByteArr
     */
    private static void dealWithTheReceivePakage(byte[] receiveByteArr) {

    }

}
