package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public interface Visokoskolska {


    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudente(Ispit[] ispiti,Integer ocjenaPisanjeZavrsnogRada,Integer ocjenaObranaZavrsnogRada);

    default BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispiti){
        BigDecimal sumaOcjena = BigDecimal.valueOf(0);
        for (int i = 0;i < ispiti.length;i++){
            if(ispiti[i].getOcjena() > 1){
                sumaOcjena = sumaOcjena.add(BigDecimal.valueOf(ispiti[i].getOcjena()));
            }
        }
        BigDecimal prosjecnaOcjena = sumaOcjena.divide(BigDecimal.valueOf(ispiti.length));
        return prosjecnaOcjena;
    };


    private Ispit[] filtrirajPolozeneIspite(Ispit[] ispiti){
        Ispit pozIspiti[] = null;
        for (int i = 0;i<ispiti.length;i++){
            if(ispiti[i].getOcjena() > 1){
                pozIspiti[i] = ispiti[i];
            }
        }
        return pozIspiti;
    };

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
