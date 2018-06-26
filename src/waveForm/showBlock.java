package waveForm;

import RoadHoldBoundar.MainBoundar;
import sample.Main;

import javax.swing.*;
import java.awt.*;

import static RoadHoldBoundar.MainBoundar.waveJpanel;
import static RoadHoldBoundar.MainBoundar.waveMidJpanel;

/**
 * 显示波形图和频谱图以及设置框图
 */
public class showBlock {
    /**
     * 显示波形图
     */
    public static void showWaveBlock() throws InterruptedException {
        waveMidJpanel.setPreferredSize(new Dimension(600,(waveMidJpanel.getComponentCount()/3)*210));
        Dynamatic dynamatic=new Dynamatic(5000);
        waveMidJpanel.add(new JPanel().add(dynamatic));
        dynamatic.new DataGenerator(20).start();

//        waveJpanel.setViewportView((new Dynamatic()).getDynamatic(20));//全屏显示
        MainBoundar.jFrame.setVisible(true);
    }
}
