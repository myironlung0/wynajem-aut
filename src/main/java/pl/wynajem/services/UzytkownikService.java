package pl.wynajem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wynajem.models.Uzytkownik;
import pl.wynajem.repositories.UzytkownikRepository;

import java.time.LocalDate;

@Service
public class UzytkownikService {
    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    public Uzytkownik findById(int id) {
        return uzytkownikRepository.findById(id);
    }

    public void aktualizujDaneOsobiste(int id, String imie, String nazwisko,
                                       String adres, String miejscowosc,
                                       String tel, String email,
                                       String nrDowodu, LocalDate dataUr) {
        Uzytkownik uzytkownik = uzytkownikRepository.findById(id);
        if (uzytkownik == null) {
            throw new RuntimeException("Użytkownik nie znaleziony");
        }

        if (imie != null && !imie.trim().isEmpty()) {
            uzytkownik.setImie(imie.trim());
        }

        if (nazwisko != null && !nazwisko.trim().isEmpty()) {
            uzytkownik.setNazwisko(nazwisko.trim());
        }

        if (adres != null && !adres.trim().isEmpty()) {
            uzytkownik.setAdres(adres.trim());
        }

        if (miejscowosc != null && !miejscowosc.trim().isEmpty()) {
            uzytkownik.setMiejscowosc(miejscowosc.trim());
        }

        if (tel != null && !tel.trim().isEmpty()) {
            try {
                int nrTel = Integer.parseInt(tel.trim());
                uzytkownik.setNrTel(nrTel);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (email != null && !email.trim().isEmpty()) {
            String nowyEmail = email.trim();
            if (!nowyEmail.equals(uzytkownik.getEmail())) {
                // czy mail juz istnieje
                if (uzytkownikRepository.emailExists(nowyEmail)) {
                    throw new RuntimeException("Email już istnieje w systemie");
                }
                uzytkownik.setEmail(nowyEmail);
            }
        }

        if (nrDowodu != null && !nrDowodu.trim().isEmpty()) {
            uzytkownik.setNrDowodu(nrDowodu.trim());
        }

        if (dataUr != null) {
            uzytkownik.setDataUr(dataUr);
        }

        uzytkownikRepository.update(uzytkownik);
    }

    public Uzytkownik pobierzDaneUzytkownika(int id) {
        return uzytkownikRepository.findById(id);
    }

}
