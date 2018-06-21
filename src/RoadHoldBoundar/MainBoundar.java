package RoadHoldBoundar;

import javax.swing.*;
import java.awt.*;

/**
 * 公路支架主界面显示
 */
public class MainBoundar {
    public static JFrame jFrame;
    public static JScrollPane jScrollPane1, jScrollPane2;
    public static JTabbedPane jTabbedPane;
    public static JScrollPane waveJpanel;
    public static JSplitPane jSplitPane;
    public static JTextArea jTextArea;
    public static JPanel waveMidJpanel;
    public MainBoundar() {
        jFrame = new JFrame("公路支架");
        Container container = jFrame.getContentPane();
        jFrame.setSize(new Dimension(1200, 730));
        jFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width - 10, Toolkit.getDefaultToolkit().getScreenSize().height - 10);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(true);
        jFrame.setLocationRelativeTo(null);
        setJMeauBarOnBandar(jFrame);
        splitMainBandar(container);
        jFrame.setVisible(true);
    }


    public void splitMainBandar(Container container) {
        jTabbedPane = new JTabbedPane();
        waveJpanel = new JScrollPane();
        waveJpanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        waveMidJpanel=new JPanel();//because the JscorllPane do not have flowLayout
        waveMidJpanel.setLayout(new FlowLayout());
        waveJpanel.setViewportView(waveMidJpanel);
        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jTabbedPane, waveJpanel);
        container.add(jSplitPane, BorderLayout.CENTER);
        jSplitPane.setContinuousLayout(true);
        jSplitPane.setDividerLocation(260);
        jSplitPane.setEnabled(true);
        addTabbedTree(jTabbedPane);
    }


    public void addTabbedTree(JTabbedPane jTabbedPane) {
        myTree tree = new myTree();
        jScrollPane1 = getBeAddedTreeScrollPane((tree.getMyTree(tree.addNodes(tree.addTreeRootNode(), 1))));
        jScrollPane2 = getAddedTextAreaScrollPane();
        jTabbedPane.add("节点信息", jScrollPane1);
        jTabbedPane.add("日志信息", jScrollPane2);

    }


    public JScrollPane getBeAddedTreeScrollPane(JTree jtree) {

        JScrollPane jScrollPane = new JScrollPane(jtree);
        return jScrollPane;
    }

    public JScrollPane getAddedTextAreaScrollPane() {
        jTextArea = new JTextArea();
        jTextArea.setFont(new Font("楷体",Font.PLAIN,13));
        jTextArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        return jScrollPane;
    }

    public void setJMeauBarOnBandar(JFrame jFrame) {
        JMenuBar jMenuBar = new JMenuBar();//菜单栏
        jFrame.setJMenuBar(jMenuBar);
        jMenuBar.setMargin(new Insets(20, 30, 20, 30));
        jMenuBar.setFont(new Font("楷体", Font.PLAIN, 30));
        JMenu jMenu1 = new JMenu("文件");
        JMenu jMenu2 = new JMenu("工具栏2");
        JMenu jMenu3 = new JMenu("工具栏3");
        JMenu jMenu4 = new JMenu("工具栏4");
        JMenu jMenu5 = new JMenu("工具栏5");

        jMenu1.setMargin(new Insets(10, 10, 10, 10));
        jMenu1.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenu2.setMargin(new Insets(10, 10, 10, 10));
        jMenu2.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenu3.setMargin(new Insets(10, 10, 10, 10));
        jMenu3.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenu4.setMargin(new Insets(10, 10, 10, 10));
        jMenu4.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenu5.setMargin(new Insets(10, 10, 10, 10));
        jMenu5.setFont(new Font("楷体", Font.PLAIN, 14));

        jMenuBar.add(jMenu1);
        jMenuBar.add(jMenu2);
        jMenuBar.add(jMenu3);
        jMenuBar.add(jMenu4);
        jMenuBar.add(jMenu5);

        JMenuItem jMenuItem1 = new JMenuItem("菜单栏选项1");
        jMenuItem1.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem1.setIconTextGap(10);
        JMenuItem jMenuItem2 = new JMenuItem("菜单栏选项2");
        jMenuItem2.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem2.setIconTextGap(10);
        JMenuItem jMenuItem3 = new JMenuItem("菜单栏选项3");
        jMenuItem3.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem3.setIconTextGap(10);
        JMenuItem jMenuItem4 = new JMenuItem("菜单栏选项4");
        jMenuItem4.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem4.setIconTextGap(10);

        JMenuItem jMenuItem5 = new JMenuItem("菜单栏选项5");
        jMenuItem5.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem5.setIconTextGap(10);
        JMenuItem jMenuItem6 = new JMenuItem("菜单栏选项6");
        jMenuItem6.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem6.setIconTextGap(10);
        JMenuItem jMenuItem7 = new JMenuItem("菜单栏选项7");
        jMenuItem7.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem7.setIconTextGap(10);
        JMenuItem jMenuItem8 = new JMenuItem("菜单栏选项8");
        jMenuItem8.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem8.setIconTextGap(10);

        JMenuItem jMenuItem9 = new JMenuItem("菜单栏选项9");
        jMenuItem9.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem9.setIconTextGap(10);
        JMenuItem jMenuItem10 = new JMenuItem("菜单栏选项10");
        jMenuItem10.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem10.setIconTextGap(10);
        JMenuItem jMenuItem11 = new JMenuItem("菜单栏选项11");
        jMenuItem11.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem11.setIconTextGap(10);
        JMenuItem jMenuItem12 = new JMenuItem("菜单栏选项12");
        jMenuItem12.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem12.setIconTextGap(10);

        JMenuItem jMenuItem13 = new JMenuItem("菜单栏选项13");
        jMenuItem13.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem13.setIconTextGap(10);
        JMenuItem jMenuItem14 = new JMenuItem("菜单栏选项14");
        jMenuItem14.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem14.setIconTextGap(10);
        JMenuItem jMenuItem15 = new JMenuItem("菜单栏选项15");
        jMenuItem15.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem15.setIconTextGap(10);
        JMenuItem jMenuItem16 = new JMenuItem("菜单栏选项16");
        jMenuItem16.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem16.setIconTextGap(10);

        JMenuItem jMenuItem17 = new JMenuItem("菜单栏选项17");
        jMenuItem17.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem17.setIconTextGap(10);
        JMenuItem jMenuItem18 = new JMenuItem("菜单栏选项18");
        jMenuItem18.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem18.setIconTextGap(10);
        JMenuItem jMenuItem19 = new JMenuItem("菜单栏选项19");
        jMenuItem19.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem19.setIconTextGap(10);
        JMenuItem jMenuItem20 = new JMenuItem("菜单栏选项20");
        jMenuItem20.setFont(new Font("楷体", Font.PLAIN, 14));
        jMenuItem20.setIconTextGap(10);

        jMenu1.add(jMenuItem1);
        jMenu1.add(jMenuItem2);
        jMenu1.add(jMenuItem3);
        jMenu1.add(jMenuItem4);

        jMenu2.add(jMenuItem5);
        jMenu2.add(jMenuItem6);
        jMenu2.add(jMenuItem7);
        jMenu2.add(jMenuItem8);

        jMenu3.add(jMenuItem9);
        jMenu3.add(jMenuItem10);
        jMenu3.add(jMenuItem11);
        jMenu3.add(jMenuItem12);

        jMenu4.add(jMenuItem13);
        jMenu4.add(jMenuItem14);
        jMenu4.add(jMenuItem15);
        jMenu4.add(jMenuItem16);

        jMenu5.add(jMenuItem17);
        jMenu5.add(jMenuItem18);
        jMenu5.add(jMenuItem19);
        jMenu5.add(jMenuItem20);


    }


}
