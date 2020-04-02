import java.awt.*;
import java.awt.geom.*;

public class Potty extends GraphComp{

    public Potty(double w) {
        super();
        this.w=w;
        szin=Color.BLACK;
    }

    protected void newShape() {
        shape=new Ellipse2D.Double(x,y,w,w);
        super.newShape();
    }
}
