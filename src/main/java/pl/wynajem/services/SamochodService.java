package pl.wynajem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wynajem.repositories.SamochodRepository;
import pl.wynajem.models.Samochod;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SamochodService {
    //@Autowired
    private final SamochodRepository samochodRepository;

    public SamochodService(SamochodRepository samochodRepository) {
        this.samochodRepository = samochodRepository;
    }

    public List<Samochod> findAll() {
        return samochodRepository.findAll();
    }

    public Samochod findById(int id) {
        return samochodRepository.findById(id);
    }

    public List<Samochod> findByMarka(String marka) {
        return samochodRepository.findByMarka(marka);
    }

    public List<Samochod> findByMarkaModel(String marka, String model) {
        return samochodRepository.findByMarkaModel(marka, model);
    }

    public List<Samochod> findCheaperThan(BigDecimal max) {
        return samochodRepository.findCheaperThan(max);
    }

    public List<Samochod> findCenaInRange(BigDecimal min, BigDecimal max) {
        return samochodRepository.findCenaInRange(min, max);
    }

    public Samochod create(Samochod samochod) {
        samochodRepository.create(samochod);
        return samochod;
    }

    public void delete(int id) {
        samochodRepository.delete(id);
    }

    public void update(Samochod samochod) {
        samochodRepository.update(samochod);
    }
}
