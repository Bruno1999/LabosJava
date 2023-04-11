package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska{



    public VeleucilisteJave(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti, Ispit[] ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

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

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudente(Ispit[] ispiti, Integer ocjenaPisanjeZavrsnogRada, Integer ocjenaObranaZavrsnogRada) {
        BigDecimal konacnaOcjena = BigDecimal.valueOf(0);
        for (int i = 0;i < ispiti.length;i++){
            konacnaOcjena = (((BigDecimal.valueOf(ispiti[i].getOcjena()).multiply(BigDecimal.TWO)).add(BigDecimal.valueOf(ocjenaPisanjeZavrsnogRada)).add(BigDecimal.valueOf(ocjenaObranaZavrsnogRada))).divide(BigDecimal.valueOf(4)));
        }
        return konacnaOcjena;
    }




}
