package App;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ken on 28-3-2017.
 */
public class MockEffectenbeurs implements IEffectenbeurs {
    @Override
    public List<IFonds> getKoersen() {

        ArrayList<IFonds> fonds = new ArrayList<IFonds>();
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10);
        int randomNum1 = ThreadLocalRandom.current().nextInt(1, 10);
        int randomNum2 = ThreadLocalRandom.current().nextInt(1, 10);

        fonds.add(new Fonds("Ken", randomNum));
        fonds.add(new Fonds("Max", randomNum1));
        fonds.add(new Fonds("GSO", randomNum2));
        return fonds;
    }
}
