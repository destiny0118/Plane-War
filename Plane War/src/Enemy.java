import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {
    private Image enemy;//enemy图像
    public int x, y;//enemy坐标
    public static int enemy_width = 34, enemy_height = 24;//敌机宽度，高度静态常量
    static int panel_width = gameFrame.WIDTH - 22;
    static int panel_height = gameFrame.HEIGHT - 81;
    public static int speed = 1;
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    private boolean flag;
    //敌机移动标记，true向下移动，false向上移动，第一种运动模式标记，第二种运动模式中作为开始随机移动的标记
    private int move_count;
    private int t;
    public static ArrayList enemybulletlist=new ArrayList();//所有敌机共用一个敌机子弹动态数组
    public static int enemy_group=1;//根据enemy_group来加载图像和设置位置，更新位置

    public Enemy(int i) {
        try{enemy= ImageIO.read(new File("image/enemy"+enemy_group+".png"));}
        catch(IIOException e){e.printStackTrace();}
        catch (IOException e){System.out.println(e.getMessage());}
        enemy_width=enemy.getWidth(null);
        enemy_height=enemy.getHeight(null);
        //根据i完成对飞机初始位置的设置,i为在动态数组中的位置
        if (enemy_group == 1) {
            x = panel_width + 2 * enemy_width * (i - 1);
            y = panel_height / 5 - 2 * enemy_height * (i - 1);
            //enemy = toolkit.getImage("image/enemy1.jpg");
            flag = true;
            move_count=0;
        }
        if (enemy_group == 2) {
            //enemy = toolkit.getImage("image/enemy1.jpg");
            //System.out.println(enemy.getHeight(null));
            //enemy_width = 46;
            //enemy_height = 60;
            flag = false;
            x = 3 * enemy_width * (-i);
            y = panel_height / 3;
            move_count = 0;
        }
    }

    //更新敌机位置,mark标志敌机之后的运动轨迹
    public static void updatePostion(ArrayList enemylist) {
        //System.out.println(enemylist.size());检测越界的敌机是否移出动态数组
        if (enemy_group == 1) {
            for (int i = 0; i < enemylist.size(); i++) {
                Enemy temp = (Enemy) enemylist.get(i);
                //判断敌机位置
                if (temp.y < panel_height / 6)
                    temp.flag = true;
                if (temp.y > panel_height / 3)
                    temp.flag = false;
                /*
                飞机最初在右边界
                控制敌机在gamePanel的高度1/6至1/3处移动
                当到达左边界时以3倍速度向下移动
                当到达下边界时删除这个敌机对象
                 */
                if (temp.y > panel_height) {
                    enemylist.remove(i);
                    //System.out.println(enemylist.size());
                }
                if (temp.x < 0)
                    temp.y += 3 * speed;
                else if (temp.flag) {
                    temp.x -= speed;
                    temp.y += speed;
                } else {
                    temp.x -= speed;
                    temp.y -= speed;
                }
                temp.move_count++;
                //此处是一个修正，让前面敌机产生子弹的频率较慢，以防止这一组敌机同时产生子弹
                if(temp.move_count%((20-i)*50)==0)
                    enemybulletlist.add(new EnemyBullet(temp.x+enemy_width/2-EnemyBullet.width/2,temp.y+enemy_height));
            }
        }

        if (enemy_group == 2) {
            Random rand = new Random();
            for (int i = 0; i < enemylist.size(); i++) {
                Enemy temp = (Enemy) enemylist.get(i);
                //当敌机的x移动到400处时，开始随机移动
                if (temp.x > 400)
                    temp.flag = true;
                else
                    temp.x += 3 * speed;
                //当开始随机移动后判断是否超出边界，因为初始位置有些敌机放在外面
                if (temp.flag && (temp.x < 0 || temp.x > panel_width || temp.y < 0 || temp.y > panel_height))
                    enemylist.remove(i);
                //如果move_count为300倍数且开始随机移动，则随机生成每个敌机的属性t
                if (temp.flag && temp.move_count%300== 0) {
                    temp.t = rand.nextInt(4);
                }
                /*
                随机移动时，依据每个敌机的属性t决定向哪移动
                当向某一方向移动300次后，重置move_count
                决定下一次的移动方向
                 */
                if (temp.flag) {
                    if (temp.t == 0) {
                        temp.x += 2*speed;
                        temp.y -= speed;
                    } else if (temp.t == 1) {
                        temp.x -= speed;
                        temp.y -= speed;
                    } else if (temp.t == 2) {
                        temp.x -= speed;
                        temp.y += 2*speed;
                    } else {
                        temp.x += 2*speed;
                        temp.y += 2*speed;
                    }
                    temp.move_count++;
                    if(temp.move_count%200==0)
                        enemybulletlist.add(new EnemyBullet(temp.x+enemy_width/2-EnemyBullet.width,temp.y+enemy_height));
                }
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return enemy;
    }
}
