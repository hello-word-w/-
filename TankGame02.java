package tankgame2;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

@SuppressWarnings({"all"})
public class TankGame02 extends JFrame {

    //定义一个Panel
    MyPanel mp = null;//先设置为空
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {


        TankGame02 tankGame02 = new TankGame02();
    }

    public TankGame02() {
        System.out.println("请输入选择 1: 新游戏 2:继续上局");
        String key = scanner.next();
        mp = new MyPanel(key);
        //将mp放入到Thread，并启动
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//把面板(就是游戏的绘图区域)
        this.setSize(1400, 795);
        this.addKeyListener(mp);//让JFrame监听mp的键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭小框即退出程序
        this.setVisible(true);
        //在JFrame 中增加响应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("监听到关闭窗口");
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}
