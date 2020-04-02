import java.awt.*;
import java.awt.geom.*;

public class LatoTer extends GraphComp{
    private double szog=80.0,r=90.0;

    public LatoTer(double rx,double ry) {
        super();
        x=rx; y=ry;
        newShape();
    }

    protected void newShape() {
        Arc2D.Double iv=new Arc2D.Double(x-r,y-r,2*r,2*r,270-szog/2,szog,Arc2D.OPEN);
        shape=new GeneralPath(iv);
        ((GeneralPath)shape).lineTo((float)x,(float)y);
        ((GeneralPath)shape).closePath();
        super.newShape();
    }

    public void paint(Graphics2D g,AffineTransform t) {
        Composite c=g.getComposite();
        AlphaComposite alfa=AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.0f); //atlatszo
        g.setComposite(alfa);
        super.paint(g,t);
        g.setComposite(c);
    }
}

