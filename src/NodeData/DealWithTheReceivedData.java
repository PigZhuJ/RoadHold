package NodeData;

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
        for (int i = 0; i <receiveByteArr.length-1; i++) {
            if ((receiveByteArr[i]==0XA)&&(receiveByteArr[i+1]==0XD)){//find header of pakage
                if(receiveByteArr[i+2]==0X21){
                    dealWithTheHelloPakage(receiveByteArr);//处理hello包
                }else if(receiveByteArr[i+1]==0X50){
                    dealWithTheReceivePakage();//处理收到的数据包
                }
            }
        }

    }

    private static void dealWithTheReceivePakage(byte[] receivearr) {

    }

    private static void dealWithTheHelloPakage(byte[] receiveByteArr) {
    }
}
