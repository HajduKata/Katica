import java.awt.*;
import java.awt.geom.*;

public class Szem extends GraphComp {
    private KaticaBogar bogar;
    private Villogtato runner=null;
    private boolean vill;
    private boolean szinValt;
    private Color alapSzin;
    protected LatoTer latoTer;

    public Szem(KaticaBogar k,double dx,double dy) {
        super();
        bogar=k;
        alapSzin=Color.WHITE;
        x=dx; y=dy; w=10.0; h=10.0; szin=alapSzin;
        vill=false;
        szinValt=false;
        latoTer=new LatoTer(x+w/2,y+h);
        newShape();
    }

    public void paint(Graphics2D g,AffineTransform t) {
        if (vill)
            szin=(!szinValt)?Color.WHITE:Color.YELLOW;
        else szin=alapSzin;
        super.paint(g,t);
        latoTer.paint(g,t);
    }

    public void villog(boolean b) {
        if (b) {
            if (!vill) {
                vill=true;
                runner=new Villogtato();
                runner.start();
            }
        }
        else
        if (vill) {
            runner.stopp();
            runner=null;
            vill=false;
        }
    }

    public LatoTer getLatoTer() {return latoTer;}
    public void setSzin(Color c) {alapSzin=c;}
    public void stop() {
        if (runner!=null) {runner.stopp();runner=null;}
    }

    public void start() {
        villog(vill);
    }

    protected void newShape() {
        shape=new Ellipse2D.Double(x,y,w,h);
        super.newShape();
    }

    private class Villogtato extends Thread {
        private long sleepingTime=400;
        private volatile boolean fut=true;

        public void run() {
            while (fut) {
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e){}
                szinValt=!szinValt;
                bogar.repaint();
            }
        }
        public void stopp() {fut=false;}
    }
}