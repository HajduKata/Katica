import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class Szoba extends JPanel {
    public KaticaBogar lady;
    public Zseblampa   zsebi;
    public Furulya     furulya;
    public Targy[]     targyak;
    public ArrayList<Area>   items;
    private MouseHandler mouseHandler=new MouseHandler();

    public Szoba() {
        super();
        initComponents();
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    private void initComponents() {
        setLayout(null);
        this.setBorder(BorderFactory.createEtchedBorder());
        lady =new KaticaBogar(this);
        zsebi=new Zseblampa(this);
        zsebi.addObserver(lady.latas);
        furulya=new Furulya(this);
        furulya.addObserver(lady.hallas);
        items=new ArrayList<Area>();
        items.add(zsebi.getArea());
        //targyak
        targyak=new Targy[6];
        double minW=20,minH=20;
        //ellipszis alaku targyak
        for(int i=0;i<3;i++) {
            targyak[i]=new Targy(Targy.ELLIPSZIS,100+Math.random()*700,Math.random()*500,
                    minW+Math.random()*40,minH+Math.random()*40,
                    Color.getHSBColor((float)Math.random(),(float)Math.random(),(float)Math.random()));
            items.add(targyak[i].getArea());
        }
        //negyzet alaku targyak
        for(int i=3;i<6;i++) {
            targyak[i]=new Targy(Targy.NEGYZET,100+Math.random()*700,Math.random()*500,
                    minW+Math.random()*40,minH+Math.random()*40,
                    Color.getHSBColor((float)Math.random(),(float)Math.random(),(float)Math.random()));
            items.add(targyak[i].getArea());
        }
    }

    public void stop() {
        lady.stop();
        furulya.stop();
    }

    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g.create();
        RenderingHints beallitasok;
        beallitasok = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        beallitasok.put (RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints (beallitasok);
        for(int i=0;i<targyak.length;i++) {
            targyak[i].paint(g2,new AffineTransform());
        }
        lady.paint(g2);
        zsebi.paint(g2);
        g2.dispose();
    }

    private class MouseHandler extends MouseInputAdapter {
        private Point2D lastDragPoint=new Point2D.Double();
        private boolean felvette=false;

        public void mousePressed(MouseEvent e) {
            Rectangle2D r=zsebi.getBounds2D();
            if (r.contains(e.getPoint())) {         //ha a zseblampara kattintott
                if (e.getButton()==e.BUTTON1) {     //bal gombbal
                    lastDragPoint=e.getPoint();
                    felvette=true;
                }
                if (e.getButton()==e.BUTTON3) {     //jobb gombbal
                    zsebi.kapcsol();
                }
            } else felvette=false;
            r=lady.getBounds2D();
            if (r.contains(e.getPoint())) {         //ha a katicara kattintott
                Potty[] p=lady.getPottyok();
                for(int i=0;i<p.length;i++) {
                    //ha pottyre kattintottunk
                    if (p[i].getArea().getBounds2D().contains(e.getPoint())) {
                        lady.megsertodik();
                        break;
                    }
                }
                //ha simogato gombra kattintott
                if (lady.simogato.getArea().getBounds2D().contains(e.getPoint())) {
                    lady.vigasztal();
                }

            }
        }

        public void mouseDragged(MouseEvent e) {
            if (felvette) {
                double dx=e.getX()-lastDragPoint.getX();
                double dy=e.getY()-lastDragPoint.getY();
                lastDragPoint=e.getPoint();
                zsebi.mozog(dx,dy);
                repaint();
            }
        }

        public void mouseReleased(MouseEvent e) {
            lastDragPoint.setLocation(0.0,0.0);
            felvette=false;
        }
    }
}
