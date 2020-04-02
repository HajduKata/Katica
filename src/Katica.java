import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Katica extends JApplet{
    protected Szoba szoba;
    protected InfoSav info;
    private JButton fel,le,jobbra,balra;
    private JButton bForgat;
    protected Action furulyaAction,navigAction;

    public Katica() {
        getRootPane().putClientProperty("defeatSystemEventQueueCheck",Boolean.TRUE);
    }

    public void init() {
        Container cp = getContentPane();
//        cp.setLayout(new BorderLayout());
        szoba=new Szoba();
        info =new InfoSav(this);

        szoba.lady.addKaticaListener(info);

        cp.add(szoba);
        cp.add(BorderLayout.SOUTH,info);
        cp.setFocusable(true);

        //billentyuk regisztralasa
        furulyaAction=new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                szoba.furulya.furulyaz();
            }
        };

        szoba.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F,0),"furulyaAction");
        szoba.getActionMap().put("furulyaAction",furulyaAction);

        navigAction=new NavigAction();
        szoba.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("Q"),"navigAction");
        szoba.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"),"navigAction");
        szoba.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("K"),"navigAction");
        szoba.getActionMap().put("navigAction",navigAction);

        szoba.setFocusable(true);
    }

    class NavigAction extends AbstractAction{
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("q")) szoba.zsebi.forog(Math.PI/64);
            if (e.getActionCommand().equalsIgnoreCase("w")) szoba.zsebi.forog(-Math.PI/64);
            if (e.getActionCommand().equalsIgnoreCase("k")) szoba.zsebi.kapcsol();
        }
    };


    public void start() {
        szoba.lady.start();
    }

    public void stop() {
        szoba.stop();
        info.stop();
    }

    public static void main(String[] args) {
        Katica kati=new Katica();
        Console.run(kati, 800, 550);
    }

}
