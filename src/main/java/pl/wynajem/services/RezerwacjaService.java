package pl.wynajem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wynajem.models.Rezerwacja;
import pl.wynajem.models.Samochod;
import pl.wynajem.models.Uzytkownik;
import pl.wynajem.repositories.RezerwacjaRepository;
import pl.wynajem.repositories.SamochodRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class RezerwacjaService {
    @Autowired
    private RezerwacjaRepository rezerwacjaRepository;
    @Autowired
    private SamochodRepository samochodRepository;

    public void czyZweryfikowany(Uzytkownik user){
        if(user.getCzyZweryfikowany().equals("N")){
            throw new RuntimeException("Email niezweryfikowany");
        }
    }

    public List<Rezerwacja> getAll() {
        return rezerwacjaRepository.getAll();
    }

    public List<Rezerwacja> findByUzytkownikId(int userId) {
        return rezerwacjaRepository.findByIdUzytkownika(userId);
    }

    public Rezerwacja findById(int id) {
        return rezerwacjaRepository.findById(id);
    }

    // wtworzenie numeru rezerwacji REZ-
    private String generujNrRezerwacji() {
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String losowy = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return "REZ-" + data + "-" + losowy;
    }

    public BigDecimal obliczCeneKoncowa(LocalDateTime dataOd,  LocalDateTime dataDo, BigDecimal cenaZaGodzine){
        if (dataOd.isAfter(dataDo)) {
            throw new RuntimeException("Nieprawidłowy zakres dat");
        }

        long godziny = Duration.between(dataOd, dataDo).toHours();

        if (godziny <= 0) {
            throw new RuntimeException("Rezerwacja musi trwać co najmniej 1 godzinę");
        }

        return cenaZaGodzine.multiply(BigDecimal.valueOf(godziny));
    }

    public void create(Rezerwacja rezerwacja) {
        // walidacja dat
        if (rezerwacja.getDataOd().isAfter(rezerwacja.getDataDo())) {
            throw new RuntimeException("Data rozpoczęcia nie może być późniejsza niż data zakończenia");
        }
        if (rezerwacja.getDataOd().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Nie można rezerwować w przeszłości");
        }

        if (!rezerwacjaRepository.czySamochodWolny(rezerwacja.getIdSamochodu(), rezerwacja.getDataOd(), rezerwacja.getDataDo())) {
            throw new RuntimeException("Samochód jest już zarezerwowany w tym terminie");
        }

        Samochod samochod = samochodRepository.findById(rezerwacja.getIdSamochodu());
        if (samochod == null) {
            throw new RuntimeException("Samochód nie istnieje");
        }
        BigDecimal cenaZaGodzine = samochod.getCena();

        BigDecimal cenaKoncowa = obliczCeneKoncowa(rezerwacja.getDataOd(), rezerwacja.getDataDo(), cenaZaGodzine);

        rezerwacja.setCenaKoncowa(cenaKoncowa);

        // gen numer rezerwacji
        rezerwacja.setNrRezerwacji(generujNrRezerwacji());

        //status ustawiany w modelu, ale tutaj dla pewnosci
        if (rezerwacja.getStatus() == null) {
            rezerwacja.setStatus("potwierdzona");
        }

        rezerwacjaRepository.create(rezerwacja);
    }

    public void delete(int id) {
        rezerwacjaRepository.delete(id);
    }

    public void updateStatus(int rezerwacjaId, String nowyStatus) {
        rezerwacjaRepository.updateStatus(rezerwacjaId, nowyStatus);
    }
}
