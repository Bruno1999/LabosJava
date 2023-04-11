package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.util.List;


/**
 * Sucelje Visokoskolska
 */
public interface Visokoskolska {

    /**
     * Metoda za izracun konacne ocjene studenta
     * @param ispiti predaje polje objekata tipa Ispit
     * @param ocjenaPisanjeZavrsnogRada predaje ocjenu pisanja zavrsnog rada
     * @param ocjenaObranaZavrsnogRada predaje ocjenu obrane zavrsnog rada
     * @return vraca BigDecimal vrijednost konacne ocjene
     */
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudente(List<Ispit> ispiti, Ocjena ocjenaPisanjeZavrsnogRada, Ocjena ocjenaObranaZavrsnogRada);


    /**
     * Metoda za odredivanje prosjeka ocjena na ispitima
     * @param ispiti predaje polje objekata tipa Ispit
     * @return vraca BigDecimal vrijednost prosjecne ocjene
     */
    default BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispiti) throws NemoguceOdreditiProsjekStudentaException {
        BigDecimal sum = new BigDecimal(0);
        int count = 0;

        for (var i : ispiti) {
            if (i.getOcjena().getNumerickaVrijednost() > 1) {
                sum = sum.add(new BigDecimal(i.getOcjena().getNumerickaVrijednost()));
                count++;
            } else {
                throw new NemoguceOdreditiProsjekStudentaException(String.format("Student %s %s je ocjenjen negativom ocjenom iz predmeta %s (%s)!",
                        i.getStudent().getIme(),
                        i.getStudent().getPrezime(),
                        i.getStudent().getJmbag(),
                        i.getStudent().getDatumRodenja()));
            }
        }

        return sum.divide(new BigDecimal(count));
    };



    /**
     * Metoda za filtriranje polozenih ispita
     * @param ispiti predaje polje objekata tipa Ispit
     * @return vraca objekt tipa Ispit
     */

    private Ispit[] filtrirajPolozeneIspite(List<Ispit> ispiti){
        Ispit pozIspiti[] = null;
        for (int i = 0;i<ispiti.size();i++){
            if(ispiti.get(i).getOcjena().getNumerickaVrijednost() > 1){
                pozIspiti[i] = ispiti.get(i);
            }
        }
        return pozIspiti;
    };

    /**
     * Metoda za filtriranje ispita po studentu
     * @param ispiti predaje polje objekata tipa Ispit
     * @param student predaje polje objekata tipa Student
     * @return vraca polje objekata tipa Ispit
     */
    default Ispit[] filtrirajIspitePoStudentu(List<Ispit> ispiti,Student student){
        Ispit pozIspiti[] = null;
        for (int i = 0;i<ispiti.size();i++){
            if(ispiti.get(i).getStudent().getIme().equals(student.getIme())){
                pozIspiti[i] = ispiti.get(i);
            }
        }
        return pozIspiti;

    };
}
