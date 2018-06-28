package RoadHoldBoundar;

import NodeData.serverReceive;

import java.io.IOException;

public class MainBandarTest {
    public static void main(String[] args) throws IOException {
        MainBoundar mainBandar = new MainBoundar();
//        clientTest ct=new clientTest();
//        ct.start();
        serverReceive sr=new serverReceive();
        sr.receiveDataFromNode();
    }
}
