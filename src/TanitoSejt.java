public class TanitoSejt extends IdegSejt{
    private MozgatoSejt tanulo;

    public TanitoSejt(MozgatoSejt f) {
        super();
        tanulo=f;
    }

    public TanitoSejt(MozgatoSejt f,double sA,double sB,double k) {
        super(sA,sB,k);
        tanulo=f;
    }

    protected void tuzel() {
        tanulo.tanul(0.01);
    }
}