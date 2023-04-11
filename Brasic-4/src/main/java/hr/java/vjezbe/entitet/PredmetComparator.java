package hr.java.vjezbe.entitet;

import java.util.Comparator;

public class PredmetComparator implements Comparator<Predmet> {
    @Override
    public int compare(Predmet o1, Predmet o2) {
        int comp = o1.getBrojEctsBodova().compare(o2.getBrojEctsBodova());
        return comp != 0 ? comp : o1.getBrojEctsBodova().compare(o2.getBrojEctsBodova());
        return 0;
    }
}
