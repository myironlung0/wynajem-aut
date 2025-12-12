package pl.wynajem.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Ubezpieczenie {
    private int id;
    private LocalDate dataUbezpieczenia;
    private BigDecimal koszt;
    private int idSamochodu;

    public Ubezpieczenie() {}

    public Ubezpieczenie(int idSamochodu, BigDecimal koszt, LocalDate dataUbezpieczenia) {
        this.idSamochodu = idSamochodu;
        this.koszt = koszt;
        this.dataUbezpieczenia = dataUbezpieczenia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataUbezpieczenia() {
        return dataUbezpieczenia;
    }

    public void setDataUbezpieczenia(LocalDate dataUbezpieczenia) {
        this.dataUbezpieczenia = dataUbezpieczenia;
    }

    public BigDecimal getKoszt() {
        return koszt;
    }

    public void setKoszt(BigDecimal koszt) {
        this.koszt = koszt;
    }

    public int getIdSamochodu() {
        return idSamochodu;
    }

    public void setIdSamochodu(int idSamochodu) {
        this.idSamochodu = idSamochodu;
    }

    @Override
    public String toString() {
        return "Ubezpieczenie{" +
                "id=" + id +
                ", dataUbezpieczenia=" + dataUbezpieczenia +
                ", koszt=" + koszt +
                ", idSamochodu=" + idSamochodu +
                '}';
    }
}
