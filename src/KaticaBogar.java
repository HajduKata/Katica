import java.util.*;
import java.awt.*;
import java.awt.geom.*;

public class KaticaBogar extends GraphComp{
    private   AffineTransform tx;
    private   final double    mozgasPontossag=1.0;
    private   Szoba           szoba;
    private   boolean         sertodott;
    private   Morog           morgas=null;
    private   ArrayList<KaticaListener> actionListeners;
    protected Szem            jobbSzem,balSzem;
    protected Potty[]         pottyok;
    protected Simogato        simogato;
    protected Fej             fej;
    protected Utkozo          utkozo;
    protected Agy             agy;
    protected Lat             latas=null;
    protected Hall            hallas=null;

    public KaticaBogar(Szoba sz) {
        super();
        w=70;h=80;
        szin=Color.RED;
        sertodott=false;
        szoba=sz;
        tx=new AffineTransform();

        tx.translate(5+Math.random()*20,5+Math.random()*100);
        newShape();
        jobbSzem=new Szem(this,15,65);
        balSzem=new Szem(this,45,65);
        latas =new Lat();
        hallas=new Hall();
        pottyok=new Potty[7];
        for(int i=0;i<7;i++) {pottyok[i]=new Potty(10.0);}
        pottyok[0].setPos(18,6);
        pottyok[1].setPos(41,6);
        pottyok[2].setPos(15,27);
        pottyok[3].setPos(30,43);
        pottyok[4].setPos(44,27);
        pottyok[5].setPos(8,48);
        pottyok[6].setPos(52,48);
        simogato=new Simogato(32,4);
        fej=new Fej(w,h);
        utkozo=new Utkozo(this,szoba,w,h);
        agy=new Agy(this);
    }

    public void start() {
        if (!sertodott) {
            if (latas ==null) latas =new Lat();
            else latas.folytat();
            if (hallas==null) hallas=new Hall();
            else hallas.folytat();
        }
        else if (morgas==null) morgas=new Morog();
        jobbSzem.start();
        balSzem.start();
    }

    public void stop() {
        if (latas !=null) {latas.stopp();latas=null;}
        if (hallas!=null) {hallas.stopp();hallas=null;}
        if (morgas!=null) {morgas.stopp();morgas=null;}
        jobbSzem.stop();
        balSzem.stop();
    }

    public void repaint() {szoba.repaint();}

    public void mozog(double dx,double dy) {
//        tx.preConcatenate(AffineTransform.getTranslateInstance(dx,dy));
        String s;
        if (dx<=0.0) {
            if (dy>=0.0) s=KaticaEvent.ELORE_MEGY;
            else s=KaticaEvent.HATRA_MEGY;
            processKaticaEvent(new KaticaEvent(this,s));
        }
        tx.translate(dx,dy);
        repaint();
    }

    public void forog(double theta) {
        String allapot;
        if (theta<=0) allapot=KaticaEvent.BALRA_FORDUL;
        else allapot=KaticaEvent.JOBBRA_FORDUL;
        processKaticaEvent(new KaticaEvent(this,allapot));
        tx.rotate(theta,w/2,h/2);
        repaint();
    }

    public void elore(){
        processKaticaEvent(new KaticaEvent(this,KaticaEvent.ELORE_MEGY));
        tx.translate(0,mozgasPontossag);
        repaint();
    }

    public void megsertodik() {
        if (!sertodott) {
            sertodott=true;
            //megáll
            //inger stopp
            latas.felfuggeszt();
            hallas.felfuggeszt();
            //szem becsuk
            jobbSzem.setSzin(Color.BLACK);
            balSzem.setSzin(Color.BLACK);
            //morog
            morgas=new Morog();
            morgas.start();
            repaint();
            processKaticaEvent(new KaticaEvent(this,KaticaEvent.MEGSERTODOTT));
        }
    }

    public void vigasztal() {
        if (sertodott) {
            sertodott=false;
            processKaticaEvent(new KaticaEvent(this,KaticaEvent.MEGVIGASZTALVA));
            //nem morog
            morgas.stopp();
            morgas=null;
            //szeme visszaall
            jobbSzem.setSzin(Color.WHITE);
            balSzem.setSzin(Color.WHITE);
            //visszajon a latas es a hallas
            if (latas!=null)  latas.folytat();
            if (hallas!=null) hallas.folytat();
            repaint();
        }
    }

    public void paint(Graphics2D g) {
        //test
        super.paint(g,tx);
        //simogato
        simogato.paint(g,tx);
        //fej
        fej.paint(g,tx);
        //szemek(2)
        jobbSzem.paint(g,tx);
        balSzem.paint(g,tx);
        //pottyok(7 db)
        for(int i=0;i<7;i++) {
            pottyok[i].paint(g,tx);}
        //utkozo
        utkozo.paint(g,tx);
    }

    public Rectangle2D getBounds2D()     {return getArea().getBounds2D();}
    public Potty[]     getPottyok()      {return pottyok;}
    public boolean     isSertodott()     {return sertodott;}

    public synchronized void addKaticaListener(KaticaListener kl) {
        if (actionListeners==null) {
            actionListeners=new ArrayList<KaticaListener>();
        } else if (actionListeners.contains(kl)) {return;}
        actionListeners.add(kl);
    }

    public synchronized void removeKaticaListener(KaticaListener kl) {
        if (actionListeners==null) return;
        if (actionListeners.remove(kl) && actionListeners.size()==0)
            actionListeners=null;
    }

    protected synchronized void processKaticaEvent(KaticaEvent e) {
        if (actionListeners==null) return;
        Iterator it=actionListeners.listIterator();
        while (it.hasNext()) {
            ((KaticaListener)it.next()).actionPerformed(e);
        }
    }

    protected void szemeVillog(boolean b) {
        jobbSzem.villog(b);balSzem.villog(b);}

    protected void newShape() {
        shape=new Ellipse2D.Double(x,y,w,h);
        super.newShape();
    }

    private class Lat extends SusResThread implements Observer{
        private int sleepingTime=100;
        private boolean vanFeny=false;
        private LampaFeny feny=null;
        public Lat() {super();}

        public void run() {
            while (fut) {
                try {
                    if (felfuggesztve) {            // ha felfuggesztve,akkor var
                        synchronized(this) {
                            while (felfuggesztve)
                                wait();
                        }
                    }
                }
                catch (InterruptedException e) {}
                if (vanFeny) {
                    //metszes vizsgalata
                    Area fA=feny.getArea();
                    Area j=new Area(jobbSzem.getLatoTer().getArea());    //jobb latoter
                    Area b=new Area(balSzem.getLatoTer().getArea());     //bal  latoter
                    Area m=new Area(jobbSzem.getLatoTer().getArea());    //metszet latoter
                    if (j==null || b==null || m==null || fA==null) break;

                    m.intersect(balSzem.getLatoTer().getArea());
                    j.subtract(m);
                    b.subtract(m);
                    j.intersect(fA);
                    b.intersect(fA);
                    m.intersect(fA);
                    if (!(j.isEmpty() && b.isEmpty() && m.isEmpty())) {
                        agy.fenyErzekel(feny);
                    }
                }
                try {Thread.sleep(sleepingTime);}
                catch (InterruptedException e) {}
                agy.felejt(0.001);
            }
        }

        public void update(Observable o,Object obj) {
            if (obj.equals(true) && (o instanceof LampaFeny)) {
                feny=(LampaFeny)o;
                vanFeny=true;
            }
            else vanFeny=false;
        }
    }

    private class Hall extends SusResThread implements Observer{
        private int sleepingTime=100;
        private boolean vanHang=false;
        public Hall() {super();}

        public void run() {
            while (fut) {
                try {
                    if (felfuggesztve) {            // ha felfuggesztve,akkor var
                        synchronized(this) {
                            szemeVillog(false);
                            while (felfuggesztve)
                                wait();
                        }
                    }
                }
                catch (InterruptedException e) {}

                if (vanHang) agy.hangErzekel();
                else szemeVillog(false);
                try {Thread.sleep(sleepingTime);}
                catch (InterruptedException e) {}
            }
        }

        public void update(Observable o, Object obj) {
            if (obj.equals(true) && (o instanceof Furulya)) {
                vanHang=true;
            }
            else vanHang=false;
        }
    }

    private class Morog extends Thread{
        private long sleepingTime=400;
        private volatile boolean fut=true;

        public void run() {
            while (fut) {
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e){}
                java.awt.Toolkit.getDefaultToolkit().beep();
            }
            agy.felejt(100);
        }
        public void stopp() {fut=false;}
    }
}
