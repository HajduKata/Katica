import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class Utkozo extends GraphComp {
    private KaticaBogar bogar;
    private Szoba       szoba;

    public Utkozo(KaticaBogar k,Szoba sz,double dw,double dh) {
        super();
        bogar=k;
        szoba=sz;
        w=dw*0.5;h=dh*0.1;
        x=dw/2-w/2;y=dh*0.95;
        szin=Color.DARK_GRAY;
        newShape();
    }

    protected void newShape() {
        Shape s1=new Rectangle2D.Double(x,y,2,h);
        Shape s2=new Rectangle2D.Double(x+w-2,y,2,h);
        shape=new Rectangle2D.Double(x,y+h,w,2);
        Area a1=new Area(s1);
        Area a2=new Area(s2);
        area=new Area(shape);
        area.add(a1);
        area.add(a2);
    }

    public void paint(Graphics2D g,AffineTransform t) {
        Object o;
        super.paint(g,t);
        Iterator it=szoba.items.iterator();
        while (it.hasNext()) {
            o=it.next();
            if (o instanceof Area) {
                if (g.hit(bogar.getArea().getBounds(),(Area)o,false)) {
                    bogar.megsertodik();
                    break;
                }
            }
        }
    }
}