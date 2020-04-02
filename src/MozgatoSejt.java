public class MozgatoSejt extends IdegSejt {
    private Agy agy;    //a sejt tulajdonosa
    private double initB;

    public MozgatoSejt(Agy a) {
        super();
        agy=a;
    }

    public MozgatoSejt(Agy a,double sA,double sB,double k) {
        super(sA,sB,k);
        initB=sB;
        agy=a;
    }

    protected void tuzel() {
        agy.mozgat();
    }

    public void tanul(double tanuloSuly) {
        szinapszisB+=tanuloSuly;
//        agy.bogar.processKaticaEvent(new KaticaEvent(this,(int)(tanuloSuly*1000)));
        agy.bogar.processKaticaEvent(new KaticaEvent(this,(int)(szinapszisB*1000)));
    }

    public void felejt(double d) {
        if (szinapszisB-d<initB) szinapszisB=initB;
        else szinapszisB-=d;
//        agy.bogar.processKaticaEvent(new KaticaEvent(this,(int)(-d*1000)));
        agy.bogar.processKaticaEvent(new KaticaEvent(this,(int)(szinapszisB*1000)));
    }

}
