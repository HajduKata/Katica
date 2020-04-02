import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;

public class Zseblampa extends JComponent{
    private Szoba           szoba;
    private boolean         kapcsolo;
    private LampaFeny       feny;
    private Area            lampA;
    private AffineTransform tx;
    private double w,h;

    public Zseblampa(Szoba sz) {
        szoba=sz;
        w=21;   // 7 tobbszorose
        h=32;   // 4 tobbszorose
        tx=new AffineTransform();
        tx.translate(10.0,10.0);
        kapcsolo = false;
        feny=new LampaFeny();
        feny.setState(kapcsolo);
    }

    public Area getFeny() {return feny.getArea();}

    public void mozog(double dx,double dy) {
        tx.preConcatenate(AffineTransform.getTranslateInstance(dx,dy));
        repaint();
    }

    public void forog(double theta) {
        tx.rotate(theta,w/2,h/2);
        repaint();
    }

    public void elore() {
        tx.translate(0,10);
        repaint();
    }

    public void hatra() {
        tx.translate(0,-10);
        repaint();
    }

    public void kapcsol() {
        kapcsolo=!(kapcsolo);
        feny.setState(kapcsolo);
        if (!kapcsolo)
            szoba.lady.processKaticaEvent(new KaticaEvent(this,1,0,1,0));
        repaint();
    }

    public void paint(Graphics2D g) {
        Graphics2D g2=(Graphics2D)g.create();
        AffineTransform t=new AffineTransform();
        Rectangle2D.Double lamp=new Rectangle2D.Double(0,0,w*5/7,h*3/4);
        Rectangle2D.Double lamp2=new Rectangle2D.Double(0,0,w,h/4);
        lampA=new Area(lamp);
        lampA.transform(tx);
        g2.setColor(Color.BLACK);
        g2.fill(lampA);

        Area lamp2A=new Area(lamp2);
        t.setTransform(tx);
        t.translate(-w/7,h*3/4);
        lamp2A.transform(t);
        g2.setColor(Color.CYAN);
        g2.fill(lamp2A);
        lampA.add(lamp2A);
        g2.setColor(Color.BLACK);
        g2.draw(lampA);
        if (kapcsolo) {
            feny.paint(g2,tx);
            feny.notifyObservers(true);
        }
        else feny.notifyObservers(false);
        g2.dispose();
    }

    public void addObserver(Observer o) {
        feny.addObserver(o);
    }

    public Rectangle2D getBounds2D() {return lampA.getBounds2D();}
    public Area getArea() {return lampA;}
    public void repaint() {szoba.repaint();}
}

class LampaFeny extends Observable{
    private boolean allapot;
    private Area fenyArea;

    public LampaFeny() {
        allapot=false;
        fenyArea=new Area();
    }
    public void setState(boolean s) {
        allapot=s;
        this.setChanged();
    }

    public void paint(Graphics2D g,AffineTransform t) {
        Graphics2D g2=(Graphics2D)g.create();
        Arc2D.Double sh=new Arc2D.Double(-52,-57,120,180,225,90,Arc2D.PIE);
        fenyArea=new Area(sh);
        fenyArea.transform(t);
        AlphaComposite alfa=AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f); //felig atlatszo
        g2.setComposite(alfa);
        g2.setColor(Color.YELLOW);
        g2.fill(fenyArea);
        g2.draw(fenyArea);
        g2.dispose();
    }

    public Area getArea() {return (allapot)?fenyArea:null;}
}