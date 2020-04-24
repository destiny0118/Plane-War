import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;

class gameFrame extends JFrame {
    public final static  int WIDTH = 1200;
    public final static  int HEIGHT = 1000;
    public static gamePanel panel;
    JMenuBar menuBar;//声明一个菜单栏对象
    JMenu menu, help, music,plane_choose;//声明菜单对象
    JMenuItem plane_speed, enemy_speed,bullet_speed, exit, help1, about;//声明菜单项对象
    JMenuItem plane1,plane2,plane3,plane4;
    JRadioButtonMenuItem on, off;//复选框，将其加入JCheckBoxGroup()以实现单选
    boolean music_flag = true;//控制音乐开关标记
    InputStream in;//音频文件输入流
    AudioStream audioStream;
    Toolkit toolkit=Toolkit.getDefaultToolkit();
    public gameFrame() {
        //设置frame的大小，图标，标题
        super("Plane::War");
        //Dimension screenSize=toolkit.getScreenSize();
        setSize(WIDTH, HEIGHT);
        Image plane = new ImageIcon("image/plane3.png").getImage();
        setIconImage(plane);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menu = new JMenu("菜单");
        help = new JMenu("帮助");
        music = new JMenu("音乐");
        plane_choose=new JMenu("选择飞机");

        plane_speed = new JMenuItem("飞机速度");
        enemy_speed = new JMenuItem("敌机速度");
        bullet_speed=new JMenuItem("子弹速度");
        exit = new JMenuItem("退出");
        help1 = new JMenuItem("帮助");
        about = new JMenuItem("关于");
        //存储飞机图像的菜单项
        plane1=new JMenuItem(new ImageIcon("image/plane1.png"));
        plane2=new JMenuItem(new ImageIcon("image/plane2.png"));
        plane3=new JMenuItem(new ImageIcon("image/plane3.png"));
        plane4=new JMenuItem(new ImageIcon("image/plane4.png"));

        on = new JRadioButtonMenuItem("开", true);
        off = new JRadioButtonMenuItem("关", false);
        ButtonGroup group = new ButtonGroup();
        group.add(on);
        group.add(off);
        music.add(on);
        music.add(off);

        menuBar.add(menu);
        menuBar.add(help);
        menuBar.add(plane_choose);

        menu.add(plane_speed);
        menu.add(enemy_speed);
        menu.add(bullet_speed);
        menu.add(music);
        menu.add(exit);
        help.add(help1);
        help.add(about);
        plane_choose.add(plane1);
        plane_choose.add(plane2);
        plane_choose.add(plane3);
        plane_choose.add(plane4);

        MenuItemListener listener = new MenuItemListener();//菜单项监听器
        //为每个菜单项添加监听器
        plane_speed.addActionListener(listener);
        enemy_speed.addActionListener(listener);
        bullet_speed.addActionListener(listener);
        exit.addActionListener(listener);
        help1.addActionListener(listener);
        about.addActionListener(listener);
        plane1.addActionListener(listener);
        plane2.addActionListener(listener);
        plane3.addActionListener(listener);
        plane4.addActionListener(listener);
        RadioButtonListener btnlistener = new RadioButtonListener();
        on.addActionListener(btnlistener);
        off.addActionListener(btnlistener);

        try {
            in = new FileInputStream("music/dragon rider.wav");
            audioStream = new AudioStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (music_flag) {
            AudioPlayer.player.start(audioStream);
        }
        panel = new gamePanel();
        panel.setFocusable(true);//设置panel获得焦点事件
        getContentPane().add(panel);
        setVisible(true);
    }

    /*
    内部动作监听类
    实现对菜单项动作的监听
    可以实现选择飞机，更改飞机速度，敌机速度，玩家子弹速度，还包括游戏说明
     */
    private class MenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
         //游戏暂停标志
            GamePlay.pause_flag=true;
            JMenuItem source = (JMenuItem) e.getSource();
            if (source == plane_speed) {
                String msg = JOptionPane.showInputDialog(null,
                        "请输入飞机速度：(整数，初始速度为4)", null);
                Plane.speed = Integer.parseInt(msg);
            } else if (source == enemy_speed) {
                String msg = JOptionPane.showInputDialog(null,
                        "请输入敌机速度：(整数，初始速度为1)", null);
                Enemy.speed = Integer.parseInt(msg);
            }else if(source==bullet_speed){
                String msg = JOptionPane.showInputDialog(null,
                        "请输入玩家子弹速度：(整数，初始速度为5)", null);
                Bullets.speed = Integer.parseInt(msg);
            }else if(source==help1){
                JOptionPane.showMessageDialog(null,
                        "本游戏中玩家通过方向键控制飞机上下左右移动，通过空格键发射子弹。"+
                        "\n玩家的飞机共有三次生命，期间可以通过吃到增强道具来强化能力。"+
                        "\n增强道具有两种，分别为发射两发子弹，子弹大小加倍。");
            }else if(source==about){
                JOptionPane.showMessageDialog(null,
                        "Plane::War" + "\n" + "2018级***");
            }else if(source==exit){
                System.exit(0);
            }else if(source==plane1){
                Plane.count=1;
                panel.plane=new Plane();
            }else if(source==plane2){
                Plane.count=2;
                panel.plane=new Plane();
            }else if(source==plane3){
                Plane.count=3;
                panel.plane=new Plane();
            }else if(source==plane4){
                Plane.count=4;
                panel.plane=new Plane();
            }
            GamePlay.pause_flag=false;
            //gameFrame.this.notifyAll();
        }
    }

    //对单选按钮的监听
    private class RadioButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JRadioButtonMenuItem source = (JRadioButtonMenuItem) e.getSource();
            if (source == on) {
                music_flag = true;
                AudioPlayer.player.start(audioStream);
            } else {
                music_flag = false;
                AudioPlayer.player.stop(audioStream);
            }
        }
    }
}
