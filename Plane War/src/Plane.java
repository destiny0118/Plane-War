import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Plane {
    public static int x,y;//飞机坐标
    private static final int panel_width=gameFrame.WIDTH-22;//获取panel的高度宽度(22,81为与frame的差额）
    private static final int panel_height=gameFrame.HEIGHT-81;
    public static int plane_width,plane_height;//存储飞机宽度和高度的静态变量
    private Image plane;
    public static int speed=4;
    public static int count=1;//加载飞机图像标记
    public static int life_num=3;//生命数量
    public static boolean up_flag=false;
    public static boolean down_flag=false;
    public static boolean left_flag=false;
    public static boolean right_flag=false;

    public Plane(){
        try{
            plane= ImageIO.read(new File("image/plane"+count+".png"));
            plane_width=plane.getWidth(null);
            plane_height=plane.getHeight(null);
        }
        catch(IOException e){System.out.println(e.toString());}
        //将飞船坐标设为底部中央
        x=panel_width/2-plane_width/2;
        y=panel_height-plane_height;
    }
    //获取飞船的private属性
    public int getX(){return x;}
    public int getY(){return y;}
    public Image getPlane(){return plane;}
    /*
    位置更新静态方法，通过类名在paint中调用，当相关属性为真时控制玩家飞机的移动
     */
    public static void updatePosition(){
        if(up_flag&&y>0)
            y=y-speed;
        if(down_flag&&(y+plane_height)<panel_height)
            y=y+speed;
        if(left_flag&&x>0)
            x=x-speed;
        if(right_flag&&(x+plane_width)<panel_width)
            x=x+speed;

    }
    public void moveup(){
        if(y>0)
            y=y-speed;
    }
    public void movedown(){
        if((y+plane_height)<panel_height)
           y=y+speed;
    }
    public void moveleft(){
        if(x>0)
            x=x-speed;
    }
    public void moveright(){
        if((x+plane_width)<panel_width)
           x=x+speed;
    }
}
