import java.awt.*;
import java.awt.geom.*;

public class Targy extends GraphComp{
    private int alakzat;
    private double sugar;
    public static final int ELLIPSZIS=0;
    public static final int NEGYZET=1;

    public Targy(int alakzat,double dx,double dy,double dw,double dh,Color color) {
        super();
        this.alakzat=alakzat;
        x=dx;y=dy;
        w=dw;h=dh;
        szin=color;
        newShape();
    }

    protected void newShape() {
        switch (alakzat) {
            case ELLIPSZIS : {
                shape=new Rectangle2D.Double(x,y,w,h);
                break;
            }
            case NEGYZET : {
                shape=new Ellipse2D.Double(x,y,w,h);
                break;
            }
        }
        super.newShape();
    }
}