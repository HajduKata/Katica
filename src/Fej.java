import java.awt.*;
import java.awt.geom.*;

public class Fej extends GraphComp {

    public Fej(double dw,double dh) {
        super();
        w=dw;h=dh;
        szin=Color.LIGHT_GRAY;
        newShape();
    }

    protected void newShape() {
        Shape s1=new Ellipse2D.Double(x,y,w,h);
        shape=new Ellipse2D.Double(x,y+h*0.7,w,h);
        Area a1=new Area(s1);
        area=new Area(shape);
        area.intersect(a1);
    }
}