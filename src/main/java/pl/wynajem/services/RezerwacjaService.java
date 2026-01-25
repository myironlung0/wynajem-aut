package pl.wynajem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wynajem.models.Rezerwacja;
import pl.wynajem.models.Uzytkownik;
import pl.wynajem.repositories.RezerwacjaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class RezerwacjaService {
    @Autowired
    private RezerwacjaRepository rezerwacjaRepository;

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

    public void create(Rezerwacja rezerwacja) {
        // walidacja dat
        if (rezerwacja.getDataOd().isAfter(rezerwacja.getDataDo())) {
            throw new RuntimeException("Data rozpoczęcia nie może być późniejsza niż data zakończenia");
        }
        if (rezerwacja.getDataOd().isBefore(LocalDate.now())) {
            throw new RuntimeException("Nie można rezerwować w przeszłości");
        }

        if (!rezerwacjaRepository.czySamochodWolny(rezerwacja.getIdSamochodu(), rezerwacja.getDataOd(), rezerwacja.getDataDo())) {
            throw new RuntimeException("Samochód jest już zarezerwowany w tym terminie");
        }

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
