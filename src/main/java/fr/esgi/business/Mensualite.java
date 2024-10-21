package fr.esgi.business;

import java.time.LocalDate;
import java.util.Objects;

public class Mensualite {
    private long id;
    private LocalDate datePrelevement;
    private double partInteretsRembourses;
    private double partCapitalRembourse;
    private static long compteur = 0;

    public Mensualite(LocalDate datePrelevement, double partInteretsRembourses, double partCapitalRembourse) {
        this.id = ++compteur;
        this.datePrelevement = datePrelevement;
        this.partInteretsRembourses = partInteretsRembourses;
        this.partCapitalRembourse = partCapitalRembourse;
    }

    public long getId() {
        return id;
    }

    public LocalDate getDatePrelevement() {
        return datePrelevement;
    }

    public void setDatePrelevement(LocalDate datePrelevement) {
        this.datePrelevement = datePrelevement;
    }

    public double getPartInteretsRembourses() {
        return partInteretsRembourses;
    }

    public void setPartInteretsRembourses(double partInteretsRembourses) {
        this.partInteretsRembourses = partInteretsRembourses;
    }

    public double getPartCapitalRembourse() {
        return partCapitalRembourse;
    }

    public void setPartCapitalRembourse(double partCapitalRembourse) {
        this.partCapitalRembourse = partCapitalRembourse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensualite that = (Mensualite) o;
        return id == that.id &&
                Double.compare(that.partInteretsRembourses, partInteretsRembourses) == 0 &&
                Double.compare(that.partCapitalRembourse, partCapitalRembourse) == 0 &&
                Objects.equals(datePrelevement, that.datePrelevement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, datePrelevement, partInteretsRembourses, partCapitalRembourse);
    }

    @Override
    public String toString() {
        return "Mensualite{" +
                "id=" + id +
                ", datePrelevement=" + datePrelevement +
                ", partInteretsRembourses=" + partInteretsRembourses +
                ", partCapitalRembourse=" + partCapitalRembourse +
                '}';
    }
}