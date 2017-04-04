package Shared;

/**
 * Created by Max Meijer on 28/03/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class Fonds implements IFonds {

    private String name;
    private double koers;

    public Fonds(String name, double koers) {
        this.name = name;
        this.koers = koers;
    }

    @Override
    public String getNaam() {
        return name;
    }

    @Override
    public double getKoers() {
        return koers;
    }

    @Override
    public String toString() {
        return name + ": €" + koers;
    }
}
