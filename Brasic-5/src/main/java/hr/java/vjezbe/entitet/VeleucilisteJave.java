package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 * Klasa VeleucilisteJave koja nasljeduje klasu ObrazovnaUstanova i implementira sucelje Visokoskolska
 */

public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska{

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public VeleucilisteJave(String naziv,List<Predmet> predmeti, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti ) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

    /**
     * Medoda za odredivanje najuspjesnijeg studenta na godini
     * Nadjacana metoda iz klase
     * @param godina predaje se godina tipa Integer
     * @return vraca objekt Student
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {
        List<Ispit> ispiti = VeleucilisteJave.this.getIspiti();
        Ispit najbolji = null;
        for (int i = 0;i < ispiti.size();i++){
            if(ispiti.get(i).getDatumIVrijeme().getYear() == godina)
                if(najbolji == null || ispiti.get(i).getOcjena().getNumerickaVrijednost()<najbolji.getOcjena().getNumerickaVrijednost())
                    najbolji = ispiti.get(i);
        }
        return najbolji.getStudent();
    }

    /**
     * Metoda za izracun konacne ocjene studija za studenta
     * @param ispiti predaje se polje objekata tipa Ispit
     * @param ocjenaPisanjeZavrsnogRada predaje se ocjena pisanja zavrsnog rada tipa Integer
     * @param ocjenaObranaZavrsnogRada predaje se ocjena obrane zavrsnog rada tipa Integer
     * @return vraca BigDecimal vrijednost ocjene
     */

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudente(List<Ispit> ispiti, Ocjena ocjenaPisanjeZavrsnogRada, Ocjena ocjenaObranaZavrsnogRada) {
        try {
            return odrediProsjekOcjenaNaIspitima(ispiti)
                    .multiply(BigDecimal.TWO)
                    .add(ocjenaPisanjeZavrsnogRada.toBigDecimal())
                    .add(ocjenaObranaZavrsnogRada.toBigDecimal())
                    .divide(new BigDecimal(4));
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            Student s = ispiti.get(0).getStudent();
            logger.warn(String.format("Student %s %s zbog negativne ocjene na jednom od ispita ima prosjek 'nedovoljan (1)'!", s.getIme(), s.getPrezime()), e);
            return BigDecimal.ONE;
        }
    }
}
