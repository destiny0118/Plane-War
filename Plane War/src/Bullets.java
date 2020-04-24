import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Bullets {
    private int x,y;//子弹的坐标
    public  static int width=15,height=15;//子弹大小常量
    public static  int speed=5;//子弹速度
    public  final Color color;

    public Bullets(int x,int y){
        //如果捕获到第二种增强道具，子弹大小加倍,否则将子弹大小重置为默认大小
        if(enhanceElement.elementCatch==2){
            width=30;
            height=30;
        }else{
            width=15;
            height=15;
        }
        this.x=x;
        this.y=y;
        //此处实现对每个子弹颜色的设置
        Random rand=new Random();
        color=new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
    }
    //玩家飞机子弹位置的更新及边界检测
    public static void updatePosition(ArrayList bulletlist){
        for(int i=0;i<bulletlist.size();i++){
            Bullets bullet=(Bullets) bulletlist.get(i);
            if(bullet.y<0)
                bulletlist.remove(i);
            else
                bullet.y-=speed;
        }
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
