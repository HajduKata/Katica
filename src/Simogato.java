import java.awt.*;
import java.awt.geom.*;

public class Simogato extends GraphComp {

    public Simogato(double dx,double dy) {
        super();
        x=dx;y=dy;
        w=6;h=35;
        szin=Color.DARK_GRAY;
        newShape();
    }

    protected void newShape() {
        shape=new Rectangle2D.Double(x,y,w,h);
        super.newShape();
    }
}