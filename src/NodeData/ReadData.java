package NodeData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 从响应的.txt文件中读取数据
 */
public class ReadData {

    public static byte[] readArrData(String DeviceNumber) throws IOException {
        String filepath="C:\\Users\\Pigzhu\\IdeaProjects\\RoadHold\\src\\DataFile";
        String fileName=DeviceNumber;
        String filePathName=filepath+"\\"+fileName+".txt";
        BufferedReader br=new BufferedReader(new FileReader(filePathName));
        String content=br.readLine();
        System.out.println("已经读到的内容:  "+content);
        byte[] readDataArray=getStringTransferToArray(content);
        return readDataArray;
    }

    private static byte[] getStringTransferToArray(String content) {
        String[] strAry = content.split(",");
        byte[] ary = new byte[strAry.length];
        for(int i = 0; i < strAry.length; i++){
            ary[i] = Byte.parseByte(strAry[i]);
        }

        return ary;
    }
}
