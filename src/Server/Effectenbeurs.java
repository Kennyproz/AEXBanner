package Server;

import Shared.Fonds;
import Shared.IFonds;

import java.util.List;

/**
 * Created by Ken on 28-3-2017.
 */
public class Effectenbeurs implements IEffectenbeurs{

    private Fonds fonds;

    @Override
    public List<IFonds> getKoersen() {
        return null;
    }
}
