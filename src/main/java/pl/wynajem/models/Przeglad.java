package pl.wynajem.models;

import java.time.LocalDate;

public class Przeglad {
    private int id;
    private LocalDate dataPrzegladu;
    private int idSamochodu;

    public Przeglad() {}

    public Przeglad(LocalDate dataPrzegladu, int idSamochodu) {
        this.dataPrzegladu = dataPrzegladu;
        this.idSamochodu = idSamochodu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataPrzegladu() {
        return dataPrzegladu;
    }

    public void setDataPrzegladu(LocalDate dataPrzegladu) {
        this.dataPrzegladu = dataPrzegladu;
    }

    public int getIdSamochodu() {
        return idSamochodu;
    }

    public void setIdSamochodu(int idSamochodu) {
        this.idSamochodu = idSamochodu;
    }

    @Override
    public String toString() {
        return "Przeglad{" +
                "id=" + id +
                ", dataPrzegladu=" + dataPrzegladu +
                ", idSamochodu=" + idSamochodu +
                '}';
    }
}
