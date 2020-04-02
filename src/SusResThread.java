public class SusResThread extends Thread {
    protected volatile boolean fut=true;
    protected volatile boolean felfuggesztve;
    private int sleepingTime=100;

    public SusResThread() {
        super();
        setPriority(Thread.MIN_PRIORITY);
        start();
    }

    public void start() {
        fut=true;
        super.start();
    }

    public synchronized void felfuggeszt() {
        if (!felfuggesztve)
            felfuggesztve=true;
    }

    public synchronized void folytat() {
        if (felfuggesztve) {
            felfuggesztve = false;
            notify();
        }
    }

    public void stopp() {fut=false;}
}
