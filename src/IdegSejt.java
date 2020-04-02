public abstract class IdegSejt {
    protected byte ingerA,ingerB;   //0 vagy 1
    protected double szinapszisA,szinapszisB,ingerKuszob;

    public IdegSejt() {
        ingerA=0;
        ingerB=0;
        szinapszisA=0;
        szinapszisB=0;
        ingerKuszob=0;
    }

    public IdegSejt(double sA,double sB,double k) {
        ingerA=0;
        ingerB=0;
        szinapszisA=sA;
        szinapszisB=sB;
        ingerKuszob=k;
    }

    public void ingerelA() {
        ingerA=1;
        ingerel();
    }
    public void ingerelB() {
        ingerB=1;
        ingerel();
    }

    public void setSzinapszis(double a,double b) {
        szinapszisA=a;
        szinapszisB=b;
    }

    public void setIngerKuszob(double i) {ingerKuszob=i;}

    protected abstract void tuzel();

    private void ingerel() {
        if (ingerA*szinapszisA+ingerB*szinapszisB>ingerKuszob) {
            ingerA=0;ingerB=0;
            tuzel();
        }
    }
}

