package RoadHoldBoundar;

import waveForm.showBlock;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;

/**
 * 生成一个带有右键弹出菜单的JTree
 */
public class myTree implements ActionListener, MouseListener {

    JTree jTree;
    JPopupMenu jPopupMenu;
    JMenuItem jMenuItem1, jMenuItem2, jMenuItem3, jMenuItem4, jMenuItem5, jMenuItem6, jMenuItem7, jMenuItem8,
            jMenuItem9, jMenuItem10, jMenuItem11, jMenuItem12, jMenuItem13;

    /**
     * 返回节点树的根节点
     *
     * @return
     */
    public DefaultMutableTreeNode addTreeRootNode() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("节点");
        return top;
    }

    /**
     * 返回增加了子节点之后的Jtree
     *
     * @param top 输入的根节点
     * @param i   节点的序号
     * @return 返回曾姐了节点的top
     */
    public DefaultMutableTreeNode addNodes(DefaultMutableTreeNode top, int i) {
        DefaultMutableTreeNode elementNodes1 = new DefaultMutableTreeNode("节点" + i);
        DefaultMutableTreeNode elementNodes2 = new DefaultMutableTreeNode("节点" + (i + 1));
        DefaultMutableTreeNode elementNodes3 = new DefaultMutableTreeNode("节点" + (i + 2));
        top.add(elementNodes1);
        top.add(elementNodes2);
        top.add(elementNodes3);
        return top;
    }

    public JTree getMyTree(DefaultMutableTreeNode top) {
        jTree = new JTree(top);
//        jTree.setEditable(true);
        jTree.setShowsRootHandles(true);
        jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree.addMouseListener(this);
        jTree.setCellEditor(new DefaultTreeCellEditor(jTree, new DefaultTreeCellRenderer()));

        jPopupMenu = new JPopupMenu();
        jMenuItem1 = new JMenuItem("显示x轴波形");
        jMenuItem1.addActionListener((ActionListener) this);

        jMenuItem2 = new JMenuItem("关闭x轴波形");
        jMenuItem2.addActionListener((ActionListener) this);

        jMenuItem3 = new JMenuItem("打开y轴波形");
        jMenuItem3.addActionListener((ActionListener) this);

        jMenuItem4 = new JMenuItem("关闭y轴波形");
        jMenuItem4.addActionListener((ActionListener) this);

        jMenuItem5 = new JMenuItem("打开z轴波形");
        jMenuItem5.addActionListener((ActionListener) this);

        jMenuItem6 = new JMenuItem("关闭z轴波形");
        jMenuItem6.addActionListener((ActionListener) this);

        jMenuItem7 = new JMenuItem("显示x轴频谱");
        jMenuItem7.addActionListener((ActionListener) this);

        jMenuItem8 = new JMenuItem("关闭x轴频谱");
        jMenuItem8.addActionListener((ActionListener) this);

        jMenuItem9 = new JMenuItem("显示y轴频谱");
        jMenuItem9.addActionListener((ActionListener) this);

        jMenuItem10 = new JMenuItem("关闭y轴频谱");
        jMenuItem10.addActionListener((ActionListener) this);

        jMenuItem11 = new JMenuItem("显示z轴频谱");
        jMenuItem11.addActionListener((ActionListener) this);

        jMenuItem12 = new JMenuItem("关闭z轴频谱");
        jMenuItem12.addActionListener((ActionListener) this);

        jMenuItem13 = new JMenuItem("设置参数");
        jMenuItem13.addActionListener((ActionListener) this);
        jPopupMenu.add(jMenuItem1);
        jPopupMenu.add(jMenuItem2);
        jPopupMenu.add(jMenuItem3);
        jPopupMenu.add(jMenuItem4);
        jPopupMenu.add(jMenuItem5);
        jPopupMenu.add(jMenuItem6);
        jPopupMenu.add(jMenuItem7);
        jPopupMenu.add(jMenuItem8);
        jPopupMenu.add(jMenuItem9);
        jPopupMenu.add(jMenuItem10);
        jPopupMenu.add(jMenuItem11);
        jPopupMenu.add(jMenuItem12);
        jPopupMenu.add(jMenuItem13);
        return jTree;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jMenuItem1) {
            System.out.println("show the area1");
            MainBoundar.jTextArea.append((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(System.currentTimeMillis()) + "  开始显示x轴波形图\n");
            try {
                showBlock.showXWaveBlock();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.out.println("已经添加了");
        } else if (e.getSource() == jMenuItem2) {
            System.out.println("show the area 2");
            MainBoundar.jTextArea.append((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(System.currentTimeMillis()) + "  关闭波形显示\n");
        } else if (e.getSource() == jMenuItem3) {
            System.out.println("show the area3");
            try {
                showBlock.showYWaveBlock();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            MainBoundar.jTextArea.append((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(System.currentTimeMillis()) + "  开始显示y轴波形图\n");
        } else if (e.getSource() == jMenuItem4) {
            System.out.println("show the area4");
            MainBoundar.jTextArea.append((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(System.currentTimeMillis()) + "  关闭频谱图\n");
        } else if (e.getSource() == jMenuItem5) {
            System.out.println("show the area5");
            try {
                showBlock.showZWaveBlock();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            MainBoundar.jTextArea.append((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(System.currentTimeMillis()) +"  开始显示z轴波形图\n");
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        TreePath path = jTree.getPathForLocation(e.getX(), e.getY()); // 关键是这个方法的使用
        if (path == null) {
            return;
        }
        jTree.setSelectionPath(path);
        TreePath treePath = jTree.getSelectionPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
        if (e.getButton() == 3 && (node.isRoot() == false)) {
            jPopupMenu.show(jTree, e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
