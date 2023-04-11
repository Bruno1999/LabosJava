package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski{



    public FakultetRacunarstva(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti, Ispit[] ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudente(Ispit[] ispiti, Integer ocjenaPisanjeDiplomskogRada, Integer ocjenaObranaDiplomskogRada) {
        BigDecimal konacnaOcjena = BigDecimal.valueOf(0);
        for (int i = 0;i < ispiti.length;i++){
            konacnaOcjena = (((BigDecimal.valueOf(ispiti[i].getOcjena()).multiply(BigDecimal.valueOf(3))).add(BigDecimal.valueOf(ocjenaPisanjeDiplomskogRada)).add(BigDecimal.valueOf(ocjenaObranaDiplomskogRada))).divide(BigDecimal.valueOf(5)));
        }
        return konacnaOcjena;
    }

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

    @Override
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
    }



}
