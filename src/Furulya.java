import java.util.*;
import java.applet.*;
import java.net.*;

public class Furulya extends Observable{
    private boolean onOff;
    private AudioClip clip;
    private Szoba szoba;

    public Furulya(Szoba sz) {
        szoba=sz;
        onOff=false;

        ClassLoader cldr = this.getClass().getClassLoader();
        java.net.URL soundURL = cldr.getResource("sounds/furulya.au");
        clip=Applet.newAudioClip(soundURL);
    }
    public void furulyaz() {
        onOff=!onOff;
        if (onOff) {
            clip.loop();
            this.setChanged();
            this.notifyObservers(onOff);
            szoba.lady.processKaticaEvent(new KaticaEvent(this,true));
        }
        else {
            clip.stop();
            this.setChanged();
            this.notifyObservers(onOff);
            szoba.lady.processKaticaEvent(new KaticaEvent(this,false));
        }
    }

    public void stop() {
        clip.stop();
    }
}

