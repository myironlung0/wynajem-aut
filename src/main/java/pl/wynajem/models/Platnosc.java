package pl.wynajem.models;

import java.time.LocalDate;

public class Platnosc {
    private int id;
    private int idUzytkownika;
    private String czyZrealizowano;
    private LocalDate dataPlatnosci;

    public Platnosc() {}

    public Platnosc(int idUzytkownika, String czyZrealizowano, LocalDate dataPlatnosci) {
        this.idUzytkownika = idUzytkownika;
        this.czyZrealizowano = czyZrealizowano;
        this.dataPlatnosci = dataPlatnosci;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUzytkownika() {
        return idUzytkownika;
    }

    public void setIdUzytkownika(int idUzytkownika) {
        this.idUzytkownika = idUzytkownika;
    }

    public String getCzyZrealizowano() {
        return czyZrealizowano;
    }

    public void setCzyZrealizowano(String czyZrealizowano) {
        this.czyZrealizowano = czyZrealizowano;
    }

    public LocalDate getDataPlatnosci() {
        return dataPlatnosci;
    }

    public void setDataPlatnosci(LocalDate dataPlatnosci) {
        this.dataPlatnosci = dataPlatnosci;
    }

    @Override
    public String toString() {
        return "Platnosci{" +
                "id=" + id +
                ", idUzytkownika=" + idUzytkownika +
                ", czyZrealizowano='" + czyZrealizowano + '\'' +
                ", dataPlatnosci=" + dataPlatnosci +
                '}';
    }
}
