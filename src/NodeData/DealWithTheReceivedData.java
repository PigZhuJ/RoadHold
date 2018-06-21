package NodeData;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static NodeData.CreatFile.creatSaveDataFile;
import static NodeData.SaveData.saveArrData;

/**
 * 处理接收到的数据
 */
public class DealWithTheReceivedData {
    /**
     * deal with the byte Array that was received
     * @param receiveByteArr
     */
    public static void dealWithTheReceiveArr(byte[] receiveByteArr) throws IOException {
        // the size of receiveByteArr[] is 3072
        //beigin to find header of pakage
        byte[] receiveByteMidArr;
        short packageLen=0;//2 bytes
        for (int i = 0; i <receiveByteArr.length-1; i++) {
            byte[] receiveMidBytes;
            if ((receiveByteArr[i]==0xA)&&(receiveByteArr[i+1]==0xD)){//find header of pakage
                //读数据包的长度
                packageLen= (short) ((0xff & receiveByteArr[i+2]) | (0xff00 & (receiveByteArr[i+3] << 8)));
                System.out.println("packageLen: "+packageLen);//打桩测试
                System.out.println("The Begin Index of i :"+i);
                receiveByteMidArr=new byte[packageLen];//中间数组长度
                System.out.println("receiveByteMidArr : "+receiveByteMidArr.length);
                System.arraycopy(receiveByteArr,i,receiveByteMidArr,0,packageLen);//copy Array
                if(receiveByteMidArr[4]==0x21){
                    System.out.println("Begin to deal with the Hello package!");
                    dealWithTheHelloPakage(receiveByteMidArr);//处理hello包
                    break;
                }else if(receiveByteMidArr[4]==0x50){
                    dealWithTheReceiveDataPackage(receiveByteMidArr);//处理收到的数据包
                    break;
                }
            }
        }

    }
    /**
     * deal with the data package
     * @param receiveByteArr
     */
    private static void dealWithTheHelloPakage(byte[] receiveByteArr) throws IOException {
        byte[] DeviceNumberByteArr=new byte[8];
        System.arraycopy(receiveByteArr,5,DeviceNumberByteArr,0,8);
        System.out.println(Arrays.toString(DeviceNumberByteArr));//打桩输出
        String DeviceNumber=DeviceNumberByteArr[0]+""+DeviceNumberByteArr[1]+DeviceNumberByteArr[2]+DeviceNumberByteArr[3]
                +DeviceNumberByteArr[4]+DeviceNumberByteArr[5]+DeviceNumberByteArr[6]+DeviceNumberByteArr[7];
        System.out.println(DeviceNumber);//输出器件名字
        //创建文件
        String filePathName=creatSaveDataFile(DeviceNumber);

//            sendConfigurePackage();

    }

    /**
     * deal with the hello package
     * @param receiveByteArr
     */
    private static void dealWithTheReceiveDataPackage(byte[] receiveByteArr) throws IOException {
        //查询器件号
        byte[] DeviceNumberByteArr=new byte[8];
        System.arraycopy(receiveByteArr,5,DeviceNumberByteArr,0,8);
        System.out.println(Arrays.toString(DeviceNumberByteArr));//打桩输出
        String DeviceNumber=DeviceNumberByteArr[0]+""+DeviceNumberByteArr[1]+DeviceNumberByteArr[2]+DeviceNumberByteArr[3]
                +DeviceNumberByteArr[4]+DeviceNumberByteArr[5]+DeviceNumberByteArr[6]+DeviceNumberByteArr[7];
        System.out.println(DeviceNumber);//输出节点名字
        String filepath="C:\\Users\\Pigzhu\\IdeaProjects\\RoadHold\\src\\DataFile";
        String fileName=DeviceNumber;
        String filePathName=filepath+"\\"+fileName+".txt";
        saveArrData(filePathName,receiveByteArr);//把采集到的数据存进去
    }

}
