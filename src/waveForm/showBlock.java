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
    public static void showXWaveBlock() throws InterruptedException {
        waveMidJpanel.setPreferredSize(new Dimension(600,(waveMidJpanel.getComponentCount()/2+1)*310));
        XDynamaticWave dynamatic=new XDynamaticWave(30000);
        waveMidJpanel.add(new JPanel().add(dynamatic));
        dynamatic.new DataXGenerator(30).start();

//        waveJpanel.setViewportView((new XYZDynamaticWave()).getDynamatic(20));//全屏显示
        MainBoundar.jFrame.setVisible(true);
    }
    /**
     * 显示波形图
     */
    public static void showYWaveBlock() throws InterruptedException {
        waveMidJpanel.setPreferredSize(new Dimension(600,(waveMidJpanel.getComponentCount()/2+1)*310));
        YDynamaticWave dynamatic=new YDynamaticWave(30000);
        waveMidJpanel.add(new JPanel().add(dynamatic));
        dynamatic.new DataYGenerator(30).start();

//        waveJpanel.setViewportView((new XYZDynamaticWave()).getDynamatic(20));//全屏显示
        MainBoundar.jFrame.setVisible(true);
    }
    /**
     * 显示波形图
     */
    public static void showZWaveBlock() throws InterruptedException {
        waveMidJpanel.setPreferredSize(new Dimension(600,(waveMidJpanel.getComponentCount()/2+1)*310));
        ZDynamaticWave dynamatic=new ZDynamaticWave(30000);
        waveMidJpanel.add(new JPanel().add(dynamatic));
        dynamatic.new DataZGenerator(30).start();

//        waveJpanel.setViewportView((new XYZDynamaticWave()).getDynamatic(20));//全屏显示
        MainBoundar.jFrame.setVisible(true);
    }
}
