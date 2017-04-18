package Server;

import Shared.Fonds;
import Shared.IFonds;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ken on 28-3-2017.
 *
 * This provides a fake beurs to fetch data from.
 * It refreshes the numbers every second.
 */
public class MockEffectenbeurs extends UnicastRemoteObject implements IEffectenbeurs {

    Timer timer;
    ArrayList<IFonds> fonds;

    public MockEffectenbeurs() throws RemoteException {
        fonds = new ArrayList<>();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    changeKoersen();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        },10,  1000);
    }

    @Override
    public List<IFonds> getKoersen() throws RemoteException {
        return fonds;
    }

    private void changeKoersen() throws RemoteException{
        fonds.clear();

        double randomNum = ThreadLocalRandom.current().nextDouble(1, 100);
        double randomNum1 = ThreadLocalRandom.current().nextDouble(1, 100);
        double randomNum2 = ThreadLocalRandom.current().nextDouble(1, 100);

        fonds.add(new Fonds("Ken", (double)Math.round(randomNum * 100d) / 100d));
        fonds.add(new Fonds("Max", (double)Math.round(randomNum1 * 100d) / 100d));
        fonds.add(new Fonds("GSO", (double)Math.round(randomNum2 * 100d) / 100d));

        System.out.println("Current at: " + new java.util.Date() + " , " +  fonds);
    }

    public void stopRefreshing() {
        timer.cancel();
    }
}
