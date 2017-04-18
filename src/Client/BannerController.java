package Client;

import Server.Effectenbeurs;
import Server.IEffectenbeurs;
import Server.MockEffectenbeurs;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ken on 28-3-2017.
 *
 * This class provides the link between the effectenbeurs and the banner.
 */
class BannerController {

    private AEXBanner banner;
    private IEffectenbeurs effectenbeurs;
    private Timer pollingTimer;

    BannerController(AEXBanner banner) throws RemoteException {

        this.banner = banner;
        this.effectenbeurs = new MockEffectenbeurs();

        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();
        pollingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    banner.setKoersen(effectenbeurs.getKoersen().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        },100, 2000);
    }

    void setEffectenbeurs(IEffectenbeurs beurs) {
        this.effectenbeurs = beurs;
    }

    // Stop banner controller
    void stop() {
        pollingTimer.cancel();
        // Stop simulation timer of effectenbeurs
        // TODO

    }

}
