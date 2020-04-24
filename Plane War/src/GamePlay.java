import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class GamePlay {
    public static boolean stop_flag = false;//为true时游戏结束
    public static boolean pause_flag = false;//为true时游戏暂停
    private static int choose;
    public static void main(String[] args) {
        JFrame frame = new gameFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //控制游戏重新开始的循环
        do {
            //控制游戏结束的循环
            while (!stop_flag) {
                try {
                    sleep(5);//让线程暂停一短时间，以免游戏元素的位置刷新过快
                } catch (InterruptedException e) {
                }
                if (!pause_flag) {
                    frame.getContentPane().repaint();
                }

            }
            if(Plane.life_num==0)
            choose=JOptionPane.showConfirmDialog(null,"是否重新开始：",
                    "你的生命数已经用尽",JOptionPane.YES_NO_OPTION);
            else
                choose=JOptionPane.showConfirmDialog(null,"是否重新开始：",
                        "恭喜你！通关了！",JOptionPane.YES_NO_OPTION);
            if(choose==JOptionPane.YES_OPTION) {
                stop_flag = false;
                frame.dispose();
                //重新游戏时初始化其中有意义的静态常量
                Plane.count=1;
                Enemy.enemy_group=1;
                Plane.life_num=3;
                enhanceElement.elementCatch=0;
                frame=new gameFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        }while(choose==JOptionPane.YES_OPTION);
    }
}
