package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Klasa VeleucilisteJave koja nasljeduje klasu ObrazovnaUstanova i implementira sucelje Visokoskolska
 */

public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska{

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public VeleucilisteJave(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti, Ispit[] ispiti) {
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
        Ispit[] ispiti = VeleucilisteJave.this.getIspiti();
        Ispit najbolji = null;
        for (int i = 0;i < ispiti.length;i++){
            if(ispiti[i].getDatumIVrijeme().getYear() == godina)
                if(najbolji == null || ispiti[i].getOcjena()<najbolji.getOcjena())
                    najbolji = ispiti[i];
        }
        return najbolji.getIme();
    }

    /**
     * Metoda za izracun konacne ocjene studija za studenta
     * @param ispiti predaje se polje objekata tipa Ispit
     * @param ocjenaPisanjeZavrsnogRada predaje se ocjena pisanja zavrsnog rada tipa Integer
     * @param ocjenaObranaZavrsnogRada predaje se ocjena obrane zavrsnog rada tipa Integer
     * @return vraca BigDecimal vrijednost ocjene
     */
    /*@Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudente(Ispit[] ispiti, Integer ocjenaPisanjeZavrsnogRada, Integer ocjenaObranaZavrsnogRada) {
        BigDecimal konacnaOcjena = BigDecimal.valueOf(0);
        for (int i = 0;i < ispiti.length;i++){
            konacnaOcjena = (((BigDecimal.valueOf(ispiti[i].getOcjena()).multiply(BigDecimal.TWO)).add(BigDecimal.valueOf(ocjenaPisanjeZavrsnogRada)).add(BigDecimal.valueOf(ocjenaObranaZavrsnogRada))).divide(BigDecimal.valueOf(4)));
        }
        return konacnaOcjena;
    }*/

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudente(Ispit[] ispiti, Integer ocjenaPisanjeZavrsnogRada, Integer ocjenaObranaZavrsnogRada) {
        try {
            return odrediProsjekOcjenaNaIspitima(ispiti)
                    .multiply(BigDecimal.TWO)
                    .add(new BigDecimal(ocjenaPisanjeZavrsnogRada))
                    .add(new BigDecimal(ocjenaObranaZavrsnogRada))
                    .divide(new BigDecimal(4));
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            Student s = ispiti[0].getIme();
            logger.warn(String.format("Student %s %s zbog negativne ocjene na jednom od ispita ima prosjek 'nedovoljan (1)'!", s.getIme(), s.getPrezime()), e);
            return BigDecimal.ONE;
        }
    }




}
