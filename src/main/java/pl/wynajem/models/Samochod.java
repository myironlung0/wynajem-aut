package pl.wynajem.models;

import java.math.BigDecimal;

public class Samochod {
    private int id;
    private String marka;
    private String model;
    private String nrVIN;
    private int przebieg;
    private BigDecimal cena;

    public Samochod() {}

    public Samochod(String marka, String model, String nrVIN, int przebieg, BigDecimal cena) {
        this.marka = marka;
        this.model = model;
        this.nrVIN = nrVIN;
        this.przebieg = przebieg;
        this.cena = cena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNrVIN() {
        return nrVIN;
    }

    public void setNrVIN(String nrVIN) {
        this.nrVIN = nrVIN;
    }

    public int getPrzebieg() {
        return przebieg;
    }

    public void setPrzebieg(int przebieg) {
        this.przebieg = przebieg;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        return "Samochod{" +
                "id=" + id +
                ", marka='" + marka + '\'' +
                ", model='" + model + '\'' +
                ", nrVIN='" + nrVIN + '\'' +
                ", przebieg=" + przebieg +
                ", cena=" + cena +
                '}';
    }
}
