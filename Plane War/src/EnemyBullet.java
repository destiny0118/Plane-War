import java.util.ArrayList;

public class EnemyBullet {
    private int x,y;//子弹的坐标
    private final int panel_width=gameFrame.WIDTH-22;//获取panel的高度宽度(22,81为与frame的差额）
    private static  int panel_height=gameFrame.HEIGHT-81;
    public static  int width=15,height=15;//子弹大小常量
    public static  int speed=4;//子弹速度
    public EnemyBullet(int x,int y){
        this.x=x;
        this.y=y;
    }
    public static void updatePosition(ArrayList enemybulletlist){
        for(int i=0;i<enemybulletlist.size();i++){
            EnemyBullet temp=(EnemyBullet) enemybulletlist.get(i);
            if(temp.y>panel_height)
                enemybulletlist.remove(i);
            else
                temp.y+=speed;
        }
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
