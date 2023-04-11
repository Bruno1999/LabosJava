package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public interface Diplomski extends Visokoskolska{

    public Student odrediStudentaZaRektorovuNagradu();

    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudente(Ispit[] ispiti, Integer ocjenaPisanjeDiplomskogRada, Integer ocjenaObranaDiplomskogRada);

}
