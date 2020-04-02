public class KaticaEvent extends java.util.EventObject {
    private String allapot = null;
    private double jobbSzemT = 0, balSzemT = 0, maxJobbSzemT = 0, maxBalSzemT = 0;
    private int tanulas = 0;
    private int furulyaszo = -1;
    public static final String ALL = "ÁLL";
    public static final String ELORE_MEGY = "ELÕRE MEGY";
    public static final String HATRA_MEGY = "HÁTRA MEGY";
    public static final String JOBBRA_FORDUL = "JOBBRA FORDUL";
    public static final String BALRA_FORDUL = "BALRA FORDUL";
    public static final String MEGSERTODOTT = "MEGSÉRTÕDÖTT";
    public static final String MEGVIGASZTALVA = "MEGVIGASZTALVA";


    public KaticaEvent(Object forras) {
        super(forras);
    }

    public KaticaEvent(Object forras, String allapot) {
        this(forras);
        this.allapot = allapot;
    }

    public KaticaEvent(Object forras, double maxJobbSzemT, double jobbSzemT,
                       double maxBalSzemT, double balSzemT) {
        this(forras);
        this.jobbSzemT = jobbSzemT;
        this.balSzemT = balSzemT;
        this.maxJobbSzemT = maxJobbSzemT;
        this.maxBalSzemT = maxBalSzemT;
    }

    public KaticaEvent(Object forras, int tanulas) {
        this(forras);
        this.tanulas = tanulas;
    }

    public KaticaEvent(Object forras, boolean szolFurulya) {
        this(forras);
        furulyaszo = szolFurulya ? 1 : 0;
    }

    public String getAllapot() {
        return allapot;
    }

    public double getJobbSzem() {
        return jobbSzemT;
    }

    public double getBalSzem() {
        return balSzemT;
    }

    public double getJobbSzemMax() {
        return maxJobbSzemT;
    }

    public double getBalSzemMax() {
        return maxBalSzemT;
    }

    public int getTanulas() {
        return tanulas;
    }

    public int szolFurulya() {
        return furulyaszo;
    }
}

