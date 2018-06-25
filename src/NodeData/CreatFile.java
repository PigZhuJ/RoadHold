package NodeData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CreatFile {
    /**
     * 创建保存数据的文件
     * @param DeviceNumber 创建保存数据的文件名，也就是***设备名***
     * @return
     */
    public static String creatSaveDataFile(String DeviceNumber) throws IOException {
        String filepath="C:\\Users\\zhujian\\IdeaProjects\\RoadHold\\src\\DataFile";
        String fileName=DeviceNumber;
        System.out.println(fileName);
        File file=new File(filepath+"\\"+fileName+".txt");
        boolean isCreated=file.createNewFile();
        if(isCreated==true){
            System.out.println("已经创建了存储设备："+fileName+"的文件");
        }
        return file.getAbsolutePath();
    }
}
