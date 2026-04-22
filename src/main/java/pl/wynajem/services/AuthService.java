package pl.wynajem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wynajem.config.SecurityConfig;
import pl.wynajem.models.Logowanie;
import pl.wynajem.models.Uzytkownik;
import pl.wynajem.repositories.LogowanieRepository;
import pl.wynajem.repositories.UzytkownikRepository;

import java.time.LocalDate;

@Service
public class AuthService {
    private final LogowanieRepository logowanieRepository;
    private final UzytkownikRepository uzytkownikRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public AuthService(LogowanieRepository logowanieRepository, UzytkownikRepository uzytkownikRepository, PasswordEncoder passwordEncoder) {
        this.logowanieRepository = logowanieRepository;
        this.uzytkownikRepository = uzytkownikRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Uzytkownik login(String login, String haslo) {

        Logowanie logowanie = logowanieRepository.findByNazwaUzytkownika(login);

        if (logowanie == null) {
            throw new RuntimeException("Nieprawidlowy login");
        }

        // sprawdzanie hasla
        if(!passwordEncoder.matches(haslo, logowanie.getHasloHash())) {
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
        String hashedPassword = passwordEncoder.encode(password);
        log.setHasloHash(hashedPassword);
        logowanieRepository.create(log);

        emailService.wyslijMailPowitalny(uzytkownik);
    }

    public void updateHaslo(int userId, String currentPassword, String newPassword, String confirmNewPassword){
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new RuntimeException("Nowe hasło nie może być puste");
        }

        if (!newPassword.equals(confirmNewPassword)) {
            throw new RuntimeException("Hasła nie są identyczne");
        }
        Logowanie logowanie = logowanieRepository.findByIdUzytkownika(userId);
        if (logowanie == null) {
            throw new RuntimeException("Nie znaleziono danych logowania");
        }

        // czy haslo aktualne zgadza sie z tym w bazie
        if (!passwordEncoder.matches(currentPassword, logowanie.getHasloHash())) {
            throw new RuntimeException("Aktualne hasło jest nieprawidłowe");
        }

        // czy haslo rozni sie od starego
        if (passwordEncoder.matches(newPassword, logowanie.getHasloHash())) {
            throw new RuntimeException("Nowe hasło musi różnić się od aktualnego");
        }

        String hashedNewPassword = passwordEncoder.encode(newPassword);

        logowanieRepository.updateHaslo(userId, hashedNewPassword);
    }
}
