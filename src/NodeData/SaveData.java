package NodeData;

import java.io.*;

public class SaveData {
    /**
     * 将数组存入到文件之中
     * @param receiveByteArr 待存入的数组
     * @param filePathName 文件路径名字
     * @return 返回是否存入到文件中去了
     */
    public static void saveArrData(String filePathName,byte[] receiveByteArr) throws IOException {
        String arrToString=getArrayTransferToString(receiveByteArr);
        BufferedWriter bw=new BufferedWriter(new FileWriter(filePathName,true));
        bw.write(arrToString+'\n');
        System.out.println(arrToString);
        bw.close();
    }

    private static String getArrayTransferToString(byte[] receiveByteArr) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < receiveByteArr.length; i++){
            sb.append(receiveByteArr[i]).append(",");
        }
        sb.deleteCharAt(sb.length() -1);
        return sb.toString();
    }
}
