package fr.esgi.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Pret {

    private long id;
    private double montantDemande;
    private double montantMensualite;
    private LocalDateTime dateSouscription;
    private LocalDate dateEffet;
    private List<Mensualite> mensualites;
    private static long compteur = 0;
    private Taux taux;

    public Pret(double montantDemande, double tauxAnnuel, LocalDateTime dateSouscription, LocalDate dateEffet, int dureeEnMois) {
        this.id = ++compteur;
        this.montantDemande = montantDemande;
        this.taux = new Taux(tauxAnnuel, dureeEnMois);
        this.dateSouscription = dateSouscription;
        this.dateEffet = dateEffet;
        this.mensualites = new ArrayList<>();
        this.montantMensualite = calculerMensualite();
    }

    // calcul mensualité avec la formule
    public double calculerMensualite() {
        double tauxMensuel = taux.getValeur() / 12 / 100; // convert taux annuel en taux mensuel
        int nombreMensualites = taux.getDureeEnMois();
        return montantDemande * tauxMensuel / (1 - Math.pow(1 + tauxMensuel, -nombreMensualites));
    }

    public void genererMensualites() {
        double capitalRestant = montantDemande;
        double tauxMensuel = taux.getValeur() / 12 / 100;
        int nombreMensualites = taux.getDureeEnMois();
        double montantMensualite = calculerMensualite();

        for (int i = 0; i < nombreMensualites; i++) {
            double interet = capitalRestant * tauxMensuel;
            double capitalRembourse = montantMensualite - interet;
            capitalRestant -= capitalRembourse;

            // Calcul date de prélèvement pour chaque mensualité
            LocalDate dateMensualite = dateEffet.plusMonths(i);
            Mensualite mensualite = new Mensualite(dateMensualite, interet, capitalRembourse);
            mensualites.add(mensualite);
        }
    }

    public List<Mensualite> getMensualites() {
        return mensualites;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Prêt ID : ").append(id).append("\n");
        sb.append("Montant demandé : ").append(montantDemande).append(" €\n");
        sb.append("Mensualité : ").append(String.format("%.2f", montantMensualite)).append(" €\n");
        sb.append("Date de souscription : ").append(dateSouscription.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");
        sb.append("Date d'effet : ").append(dateEffet.format(DateTimeFormatter.ofPattern("MM/yyyy"))).append("\n");
        return sb.toString();
    }
}