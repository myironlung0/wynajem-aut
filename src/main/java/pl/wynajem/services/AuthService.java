package pl.wynajem.services;

import org.springframework.stereotype.Service;
import pl.wynajem.models.Logowanie;
import pl.wynajem.models.Uzytkownik;
import pl.wynajem.repositories.LogowanieRepository;
import pl.wynajem.repositories.UzytkownikRepository;

import java.time.LocalDate;

@Service
public class AuthService {
    private final LogowanieRepository logowanieRepository;
    private final UzytkownikRepository uzytkownikRepository;

    public AuthService(LogowanieRepository logowanieRepository, UzytkownikRepository uzytkownikRepository) {
        this.logowanieRepository = logowanieRepository;
        this.uzytkownikRepository = uzytkownikRepository;
    }

    public Uzytkownik login(String login, String haslo) {

        Logowanie logowanie = logowanieRepository.findByNazwaUzytkownika(login);

        if (logowanie == null) {
            throw new RuntimeException("Nieprawidlowy login");
        }

        // NA RAZIE plain text
        if (!logowanie.getHasloHash().equals(haslo)) {
            throw new RuntimeException("Nieprawidlowe haslo");
        }

        Uzytkownik uzytkownik = uzytkownikRepository.findById(logowanie.getIdUzytkownika());

        if (uzytkownik == null) {
            throw new RuntimeException("Brak uzytkownika");
        }

        return uzytkownik;
    }

    public void register(String login, String email, String password, String name, String surname,
                         int phoneNum, String address, String city, String idNum, LocalDate birthDate, String isVerified) {
        // czy login/email juz istnieje
        if (logowanieRepository.nazwaUzytkownikaExists(login)) {
            throw new RuntimeException("Login już istnieje");
        }
        if (uzytkownikRepository.emailExists(email)) {
            throw new RuntimeException("Email już istnieje");
        }

        // nowyu uzytkwonik
        Uzytkownik uzytkownik = new Uzytkownik();
//        uzytkownik.setImie("Imię"); // placeholder
//
//        uzytkownik.setNazwisko("Nazwisko"); // placeholder
//        uzytkownik.setNrTel(123456789); // placeholder
//        uzytkownik.setAdres("Adres");
//        uzytkownik.setMiejscowosc("Miasto");
//        uzytkownik.setNrDowodu("ABC123456");
//        uzytkownik.setDataUr(java.time.LocalDate.of(2000, 1, 1));
//        uzytkownik.setCzyZweryfikowany("N");
        uzytkownik.setImie(name);
        uzytkownik.setNazwisko(surname);
        uzytkownik.setNrTel(phoneNum);
        uzytkownik.setAdres(address);
        uzytkownik.setMiejscowosc(city);
        uzytkownik.setNrDowodu(idNum);
        uzytkownik.setDataUr(birthDate);
        uzytkownik.setCzyZweryfikowany(isVerified);
        uzytkownik.setEmail(email);

        uzytkownikRepository.create(uzytkownik);

        Logowanie log = new Logowanie();
        log.setIdUzytkownika(uzytkownik.getId());
        log.setNazwaUzytkownika(login);
        log.setHasloHash(password); // na razie plain text
        logowanieRepository.create(log);
    }
}
