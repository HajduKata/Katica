import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class GraphComp extends JComponent{
    protected double          x,y,w,h;
    protected Shape           shape;
    protected Area            area;
    protected Color           szin;
    private   AffineTransform lastTransform;

    public GraphComp() {
        x=0.0;y=0.0;
        w=0.0; h=0.0;
        szin=Color.BLACK;
        lastTransform=new AffineTransform();
    }

    public void paint(Graphics2D g,AffineTransform t) {
        Graphics2D g2=(Graphics2D)g.create();
        Area a=(Area)area.clone();
        a.transform(t);
        g2.setColor(szin);
        g2.fill(a);
        g2.draw(a);
        g2.dispose();
        lastTransform=t;
    }

    public Area getArea() {
        Area a=(Area)area.clone();
        a.transform(lastTransform);
        return a;
    }

    public void setPos(double dx,double dy) {
        x=dx;y=dy;
        newShape();
    }
    protected void newShape() {area=new Area(shape);}
}

