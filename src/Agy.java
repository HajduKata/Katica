import java.awt.geom.*;

public class Agy{
    protected KaticaBogar bogar;
    private IdegSejt mozgato,tanito;
    private LampaFeny feny=null;

    public Agy(KaticaBogar k) {
        bogar=k;
        mozgato=new MozgatoSejt(this,1.0,0.1,0.8);
        tanito= new TanitoSejt((MozgatoSejt)mozgato,0.5,0.5,0.7);
    }

    public void fenyErzekel(LampaFeny f) {
        feny=f;
        mozgato.ingerelA();
        tanito.ingerelA();
    }

    public void hangErzekel() {
        bogar.szemeVillog(true);
        mozgato.ingerelB();
        tanito.ingerelB();
    }

    public void mozgat() {
        double k;               //kulonbseg
        double t1,t2,tm;        //latoter teruletek
        double diff=500;        //j-b metszetek teruletei kozti kulonbseg
        double maxJ,maxB;
        double erezJ,erezB;
        Area fA=feny.getArea();

        //ha csak a hang miatt megy
        if (fA==null) {
            bogar.elore();
            return;
        }

        Area j=new Area(bogar.jobbSzem.getLatoTer().getArea());
        Area b=new Area(bogar.balSzem.getLatoTer().getArea());
        Area m=new Area(bogar.jobbSzem.getLatoTer().getArea());
        if (j==null || b==null || m==null || fA==null) return;

        //az infohoz kell
        maxJ=terulet(j.getBounds2D());
        maxB=terulet(b.getBounds2D());
        Area jj=new Area(j),bb=new Area(b);
        jj.intersect(fA);bb.intersect(fA);
        erezJ=terulet(jj.getBounds2D());
        erezB=terulet(bb.getBounds2D());

        m.intersect(bogar.balSzem.getLatoTer().getArea());
        j.subtract(m);
        b.subtract(m);
        j.intersect(fA);
        b.intersect(fA);
        m.intersect(fA);

        if (!( j.isEmpty()) || !(b.isEmpty()) || !(m.isEmpty())) {
            t1=terulet(j.getBounds2D());
            t2=terulet(b.getBounds2D());
            tm=terulet(m.getBounds2D());
            k=t1-t2;
            //informacio
            bogar.processKaticaEvent(new KaticaEvent(this,maxJ,erezJ,maxB,erezB));

            //merre menjen?
            if (Math.abs(k)<Math.min(t1,t2) || Math.abs(k)<diff) { //elhanyagolhato kulonbseg
                bogar.elore();
            }
            else {
                if (k<0.0) {
                    bogar.forog(-Math.PI/256);
                }
                else
                if (k>0.0) {
                    bogar.forog(Math.PI/256);
                }
                else {
                    bogar.elore();
                }
            }
            bogar.repaint();
        }
        else {      //masik eset,mikor csak a hang miatt megy
            bogar.elore();
        }
    }

    public void felejt(double d) {
        if (mozgato instanceof MozgatoSejt)
            ((MozgatoSejt)mozgato).felejt(d);
    }

    private double terulet(Rectangle2D r) {return r.getWidth()*r.getHeight();}

}
