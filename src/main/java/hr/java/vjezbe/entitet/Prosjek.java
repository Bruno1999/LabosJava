package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class Prosjek extends Entitet {
    BigDecimal prosjekOcjena;

    public Prosjek(long id,BigDecimal prosjekOcjena) {
        super(id);
        this.prosjekOcjena = prosjekOcjena;
    }
}
