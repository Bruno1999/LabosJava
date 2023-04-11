package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;



/**
 * Klaca FakultetRacunarstva koja nasljeduje klasu ObrazovnaUstanova i implementira sucelje Diplomski
 */
public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski {

    //private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    /**
     * Konstruktor za klasu FakultetRacunarstva
     * @param naziv tipa String koji je nasljeden od nadklase ObrazovnaUstanova
     * @param predmeti tipa Predmet koji je nasljeden od nadklase ObrazovnaUstavnova
     * @param profesori tipa Profesor koji je nasljeden od nadklase ObrazovnaUstavnova
     * @param studenti tipa Student koji je nasljeden od nadklase ObrazovnaUstavnova
     * @param ispiti tipa Ispit koji je nasljeden od nadklase ObrazovnaUstavnova
     */

    public FakultetRacunarstva(long id, String naziv, List<Predmet> predmeti, List<Profesor> profesori, List<hr.java.vjezbe.entitet.Student> studenti, List<hr.java.vjezbe.entitet.Ispit> ispiti) {
        super(id,naziv, predmeti, profesori, studenti, ispiti);
    }

    /**
     * Metoda za odredivanje najuspjesnijeg studenta na godini
     * Nadjacana medota iz klase ObrazovnaUstanova
     * @param godina predaje se godina tipa Integer
     * @return vraca Student
     */
    @Override
    public hr.java.vjezbe.entitet.Student odrediNajuspjesnijegStudentaNaGodini(Integer godina) {
        List<hr.java.vjezbe.entitet.Ispit> ispiti = FakultetRacunarstva.this.getIspiti();
        hr.java.vjezbe.entitet.Ispit najbolji = null;
        for (int i = 0;i < ispiti.size();i++){
            if(ispiti.get(i).getDatumIVrijeme().getYear() == godina)
                if(najbolji == null || ispiti.get(i).getOcjena().getNumerickaVrijednost()<najbolji.getOcjena().getNumerickaVrijednost())
                    najbolji = ispiti.get(i);
        }
        return najbolji.getStudent();
    }


    /**
     * Funkcija za izracun konacne ocjene studija za studente
     * Overridana je funkcija
     * @param ispiti predaje se polje objekata za ispitni rok
     * @param ocjenaPisanjeDiplomskogRada predaje se ocjena pisanja diplomskog rada
     * @param ocjenaObranaDiplomskogRada predaje se ocjena obrane diplomskog rada
     * @return vraca BigDecimal vrijednost ocjene
     *
     */

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudente(List<hr.java.vjezbe.entitet.Ispit> ispiti, hr.java.vjezbe.entitet.Ocjena ocjenaPisanjeDiplomskogRada, Ocjena ocjenaObranaDiplomskogRada) {
        try {
            return odrediProsjekOcjenaNaIspitima(ispiti)
                    .multiply(new BigDecimal(3))
                    .add(ocjenaPisanjeDiplomskogRada.toBigDecimal())
                    .add(ocjenaObranaDiplomskogRada.toBigDecimal())
                    .divide(new BigDecimal(5));
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            hr.java.vjezbe.entitet.Student s = ispiti.get(0).getStudent();
            //logger.warn(String.format("Student %s %s zbog negativne ocjene na jednom od ispita ima prosjek 'nedovoljan (1)'!", s.getIme(), s.getPrezime()), e);
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

        List<hr.java.vjezbe.entitet.Ispit> ispiti = FakultetRacunarstva.this.getIspiti();
        Ispit najbolji = null;

        BigDecimal najboljiProsjek = new BigDecimal(0);

        for (var s : ispiti) {
            BigDecimal prosjek;
            try {
                prosjek = odrediProsjekOcjenaNaIspitima(getIspiti());
            } catch (NemoguceOdreditiProsjekStudentaException e) {
                //logger.info(String.format("Student %s %s zbog negativne ocjene na jednom od ispita ima prosjek 'nedovoljan (1)'!", s.getStudent(), s.getStudent().getPrezime()), e);
                continue;
            }

            int usporedbaDobi = najbolji != null
                    ? s.getStudent().getDatumRodenja().compareTo(najbolji.getStudent().getDatumRodenja())
                    : 1;

            if (prosjek.compareTo(najboljiProsjek) >= 0 || prosjek.equals(najboljiProsjek) && usporedbaDobi > 0) {
                najbolji = s;
                najboljiProsjek = prosjek;
            } else if (usporedbaDobi == 0) {
                String najmladjiStudenti = String.format("%s %s, %s %s",
                        najbolji.getStudent(),
                        najbolji.getStudent().getPrezime(),
                        s.getStudent(),
                        s.getStudent().getPrezime());

                System.out.println("Pronađeno je više najmlađih studenata: " + najmladjiStudenti);
                //logger.error("Pronađeno je više najmlađih studenata: " + najmladjiStudenti);

                throw new PostojiViseNajmladjihStudenataException(najmladjiStudenti);
            }
        }

        return najbolji.getStudent();
    }


}
