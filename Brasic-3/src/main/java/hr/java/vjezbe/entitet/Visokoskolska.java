package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;


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
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudente(Ispit[] ispiti,Integer ocjenaPisanjeZavrsnogRada,Integer ocjenaObranaZavrsnogRada);


    /**
     * Metoda za odredivanje prosjeka ocjena na ispitima
     * @param ispiti predaje polje objekata tipa Ispit
     * @return vraca BigDecimal vrijednost prosjecne ocjene
     */
    default BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispiti) throws NemoguceOdreditiProsjekStudentaException {
        BigDecimal sum = new BigDecimal(0);
        int count = 0;

        for (var i : ispiti) {
            if (i.getOcjena() > 1) {
                sum = sum.add(new BigDecimal(i.getOcjena()));
                count++;
            } else {
                throw new NemoguceOdreditiProsjekStudentaException(String.format("Student %s %s je ocjenjen negativom ocjenom iz predmeta %s (%s)!",
                        i.getIme().getIme(),
                        i.getIme().getPrezime(),
                        i.getIme().getJmbag(),
                        i.getIme().getDatumRodenja()));
            }
        }

        return sum.divide(new BigDecimal(count));
    };



    /**
     * Metoda za filtriranje polozenih ispita
     * @param ispiti predaje polje objekata tipa Ispit
     * @return vraca objekt tipa Ispit
     */

    private Ispit[] filtrirajPolozeneIspite(Ispit[] ispiti){
        Ispit pozIspiti[] = null;
        for (int i = 0;i<ispiti.length;i++){
            if(ispiti[i].getOcjena() > 1){
                pozIspiti[i] = ispiti[i];
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
    default Ispit[] filtrirajIspitePoStudentu(Ispit[] ispiti,Student student){
        Ispit pozIspiti[] = null;
        for (int i = 0;i<ispiti.length;i++){
            if(ispiti[i].getIme().getIme().equals(student.getIme())){
                pozIspiti[i] = ispiti[i];
            }
        }
        return pozIspiti;

    };
}
