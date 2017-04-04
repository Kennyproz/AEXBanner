package Client;

import Server.IEffectenbeurs;
import Server.MockEffectenbeurs;

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

    BannerController(AEXBanner banner) {

        this.banner = banner;
        this.effectenbeurs = new MockEffectenbeurs();

        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();
        pollingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                banner.setKoersen(effectenbeurs.getKoersen().toString());
            }
        },100, 2000);
    }

    // Stop banner controller
    void stop() {
        pollingTimer.cancel();
        // Stop simulation timer of effectenbeurs
        // TODO

    }

}