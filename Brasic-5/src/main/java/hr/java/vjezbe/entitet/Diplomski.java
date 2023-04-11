package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.List;

/**
 * Sucelje Diplomski
 */
public interface Diplomski extends Visokoskolska{
    /**
     * Metoda za odredivanje studenta za rektorovu nagradu
     * @return vraca Student vrijednost, tj.vraca studenta koji dobitnik rektorove nagrade
     */
    public Student odrediStudentaZaRektorovuNagradu();

    /**
     * Metoda za izracun konacne ocjene za studenta
     * @param ispiti predaje se polje objekata za ispitni rok
     * @param ocjenaPisanjeDiplomskogRada predaje se ocjena pisanja diplomskog rada
     * @param ocjenaObranaDiplomskogRada predaje se ocjena obrane diplomskog rada
     * @return vraca BigDecimal vrijednost konacne ocjene
     */
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudente(List<Ispit> ispiti, Ocjena ocjenaPisanjeDiplomskogRada, Ocjena ocjenaObranaDiplomskogRada);

}
