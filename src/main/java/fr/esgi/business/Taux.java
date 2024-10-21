package fr.esgi.business;

import java.util.Objects;

public class Taux {

    private long id;
    private double valeur;
    private int dureeEnMois;
    private static long compteur = 0; // ompteur pour incrémentation des ID

    public Taux(double valeur, int dureeEnMois) {
        this.id = ++compteur;
        this.valeur = valeur;
        this.dureeEnMois = dureeEnMois;
    }

    public long getId() {
        return id;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public int getDureeEnMois() {
        return dureeEnMois;
    }

    public void setDureeEnMois(int dureeEnMois) {
        this.dureeEnMois = dureeEnMois;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taux taux = (Taux) o;
        return id == taux.id &&
                Double.compare(taux.valeur, valeur) == 0 &&
                dureeEnMois == taux.dureeEnMois;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valeur, dureeEnMois);
    }

    @Override
    public String toString() {
        return "Taux{" +
                "id=" + id +
                ", valeur=" + valeur +
                ", dureeEnMois=" + dureeEnMois +
                '}';
    }
}