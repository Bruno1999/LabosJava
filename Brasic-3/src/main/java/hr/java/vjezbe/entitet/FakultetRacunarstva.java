package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Klaca FakultetRacunarstva koja nasljeduje klasu ObrazovnaUstanova i implementira sucelje Diplomski
 */
public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski{

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    /**
     * Konstruktor za klasu FakultetRacunarstva
     * @param naziv tipa String koji je nasljeden od nadklase ObrazovnaUstanova
     * @param predmeti tipa Predmet koji je nasljeden od nadklase ObrazovnaUstavnova
     * @param profesori tipa Profesor koji je nasljeden od nadklase ObrazovnaUstavnova
     * @param studenti tipa Student koji je nasljeden od nadklase ObrazovnaUstavnova
     * @param ispiti tipa Ispit koji je nasljeden od nadklase ObrazovnaUstavnova
     */

    public FakultetRacunarstva(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti, Ispit[] ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }



    /**
     * Metoda za odredivanje najuspjesnijeg studenta na godini
     * Nadjacana medota iz klase ObrazovnaUstanova
     * @param godina predaje se godina tipa Integer
     * @return vraca Student
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {
        Ispit[] ispiti = FakultetRacunarstva.this.getIspiti();
        Ispit najbolji = null;
        for (int i = 0;i < ispiti.length;i++){
            if(ispiti[i].getDatumIVrijeme().getYear() == godina)
                if(najbolji == null || ispiti[i].getOcjena()<najbolji.getOcjena())
                    najbolji = ispiti[i];
        }
        return najbolji.getIme();
    }

    /**
     * Metoda za odredivanje studenta za rektorovu nagradu
     * Nadjacana metoda iz sucelja Diplomski
     * @return vraca Student vrijednost, tj.vraca studenta koji dobitnik rektorove nagrade
     */
    /*@Override
    public Student odrediStudentaZaRektorovuNagradu(){
        Ispit[] ispiti = FakultetRacunarstva.this.getIspiti();
        Ispit najbolji = null;
        for (int i = 0;i < ispiti.length;i++){
            if(najbolji == null || ispiti[i].getOcjena()<najbolji.getOcjena())
                najbolji = ispiti[i];
            else if(ispiti[i].getOcjena().equals(najbolji.getOcjena()) && najbolji.getIme().getDatumRodenja().getYear() > ispiti[i].getIme().getDatumRodenja().getYear())
                najbolji = ispiti[i];

        }

        return najbolji.getIme();
    }*/

    /**
     * Funkcija za izracun konacne ocjene studija za studente
     * Overridana je funkcija
     * @param ispiti predaje se polje objekata za ispitni rok
     * @param ocjenaPisanjeDiplomskogRada predaje se ocjena pisanja diplomskog rada
     * @param ocjenaObranaDiplomskogRada predaje se ocjena obrane diplomskog rada
     * @return vraca BigDecimal vrijednost ocjene
     *
     */
   /* @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudente(Ispit[] ispiti, Integer ocjenaPisanjeDiplomskogRada, Integer ocjenaObranaDiplomskogRada) {
        BigDecimal konacnaOcjena = BigDecimal.valueOf(0);
        for (int i = 0;i < ispiti.length;i++){
            konacnaOcjena = (((BigDecimal.valueOf(ispiti[i].getOcjena()).multiply(BigDecimal.valueOf(3))).add(BigDecimal.valueOf(ocjenaPisanjeDiplomskogRada)).add(BigDecimal.valueOf(ocjenaObranaDiplomskogRada))).divide(BigDecimal.valueOf(5)));
        }
        return konacnaOcjena;
    }*/

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudente(Ispit[] ispiti, Integer ocjenaPisanjeDiplomskogRada, Integer ocjenaObranaDiplomskogRada) {
        try {
            return odrediProsjekOcjenaNaIspitima(ispiti)
                    .multiply(new BigDecimal(3))
                    .add(new BigDecimal(ocjenaPisanjeDiplomskogRada))
                    .add(new BigDecimal(ocjenaObranaDiplomskogRada))
                    .divide(new BigDecimal(5));
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            Student s = ispiti[0].getIme();
            logger.warn(String.format("Student %s %s zbog negativne ocjene na jednom od ispita ima prosjek 'nedovoljan (1)'!", s.getIme(), s.getPrezime()), e);
            return BigDecimal.ONE;
        }
    }

    /**
     * Metoda za odredivanje studenta za rektorovu nagradu
     * Overridana je funkcija
     * @return vraca Student vrijednost, tj.vraca studenta koji dobitnik rektorove nagrade
     */
    @Override
    public Student odrediStudentaZaRektorovuNagradu() {

        Ispit[] ispiti = FakultetRacunarstva.this.getIspiti();
        Ispit najbolji = null;

        //Student najboljiStudent = null;
        BigDecimal najboljiProsjek = new BigDecimal(0);

        for (var s : ispiti) {
            BigDecimal prosjek;
            try {
                prosjek = odrediProsjekOcjenaNaIspitima(getIspiti());
            } catch (NemoguceOdreditiProsjekStudentaException e) {
                logger.info(String.format("Student %s %s zbog negativne ocjene na jednom od ispita ima prosjek 'nedovoljan (1)'!", s.getIme(), s.getIme().getPrezime()), e);
                continue;
            }

            int usporedbaDobi = najbolji != null
                    ? s.getIme().getDatumRodenja().compareTo(najbolji.getIme().getDatumRodenja())
                    : 1;

            if (prosjek.compareTo(najboljiProsjek) >= 0 || prosjek.equals(najboljiProsjek) && usporedbaDobi > 0) {
                najbolji = s;
                najboljiProsjek = prosjek;
            } else if (usporedbaDobi == 0) {
                String najmladjiStudenti = String.format("%s %s, %s %s",
                        najbolji.getIme(),
                        najbolji.getIme().getPrezime(),
                        s.getIme(),
                        s.getIme().getPrezime());

                System.out.println("Pronađeno je više najmlađih studenata: " + najmladjiStudenti);
                logger.error("Pronađeno je više najmlađih studenata: " + najmladjiStudenti);

                throw new PostojiViseNajmladjihStudenataException(najmladjiStudenti);
            }
        }

        return najbolji.getIme();
    }


}
