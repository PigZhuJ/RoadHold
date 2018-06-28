package waveForm;

import RoadHoldBoundar.MainBoundar;

import javax.swing.*;
import java.awt.*;

import static RoadHoldBoundar.MainBoundar.waveMidJpanel;

/**
 * 显示波形图和频谱图以及设置框图
 */
public class showBlock {
    /**
     * 显示波形图
     */
    public static void showWaveBlock() throws InterruptedException {
        waveMidJpanel.setPreferredSize(new Dimension(600,(waveMidJpanel.getComponentCount()/2+1)*310));
        Dynamatic dynamatic=new Dynamatic(60000);
        waveMidJpanel.add(new JPanel().add(dynamatic));
        dynamatic.new DataGenerator(50).start();

//        waveJpanel.setViewportView((new Dynamatic()).getDynamatic(20));//全屏显示
        MainBoundar.jFrame.setVisible(true);
    }
}
