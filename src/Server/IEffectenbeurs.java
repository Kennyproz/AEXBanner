package Server;

import Shared.IFonds;

import java.rmi.Remote;
import java.util.List;

/**
 * Created by Ken on 28-3-2017.
 */
public interface IEffectenbeurs extends Remote {
    public List<IFonds> getKoersen();
}
