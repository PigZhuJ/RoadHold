package RoadHoldBoundar;

import NodeData.serverReceive;

import java.io.IOException;

public class MainBandarTest {
    public static void main(String[] args) throws IOException {
        MainBoundar mainBandar = new MainBoundar();
        serverReceive sr=new serverReceive();
        sr.receiveDataFromNode();
    }
}
