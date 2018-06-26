package NodeData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SavaData {
    public static void saveDataArrX(short shortx) throws IOException {
        System.out.println("here");
        BufferedWriter bw1=new BufferedWriter(new FileWriter("C:\\Users\\Pigzhu\\IdeaProjects\\RoadHold\\src\\DataFile\\testx.txt",true));
        bw1.write(shortx);
        bw1.close();

    }
    public static void saveDataArrY(short shorty) throws IOException {

        System.out.println("here");
        BufferedWriter bw2=new BufferedWriter(new FileWriter("C:\\Users\\Pigzhu\\IdeaProjects\\RoadHold\\src\\DataFile\\testy.txt",true));
        bw2.write(shorty);
        bw2.close();
    }
    public static void saveDataArrZ(short shortz) throws IOException {
        System.out.println("here");
        BufferedWriter bw3=new BufferedWriter(new FileWriter("C:\\Users\\Pigzhu\\IdeaProjects\\RoadHold\\src\\DataFile\\testz.txt",true));
        bw3.write(shortz);
        bw3.close();
    }


}
