package fr.esgi;

import fr.esgi.business.Pret;
import fr.esgi.business.Taux;
import fr.esgi.business.Mensualite;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Taux> tauxList = new ArrayList<>();

        // ajout des taux disponibles
        tauxList.add(new Taux(1.0, 12));   // 1% sur 12 mois
        tauxList.add(new Taux(1.5, 24));   // 1.5% sur 24 mois
        tauxList.add(new Taux(2.0, 12));   // 2% sur 12 mois
        tauxList.add(new Taux(2.5, 24));   // 2.5% sur 24 mois

        System.out.println("Bienvenue sur votre outil de calcul de prêt à la consommation !");

        // montant du prêt
        double montantDemande = 0;
        do {
            System.out.print("Veuillez saisir le montant de votre prêt (inférieur ou égal à 20 000€) : ");
            montantDemande = sc.nextDouble();
            if (montantDemande > 20000 || montantDemande <= 0) {
                System.out.println("Le montant doit être compris entre 1 et 20 000€.");
            }
        } while (montantDemande > 20000 || montantDemande <= 0);

        // taux disponibles
        System.out.println("Veuillez choisir le taux annuel qui vous convient :");
        for (int i = 0; i < tauxList.size(); i++) {
            Taux taux = tauxList.get(i);
            System.out.println((i + 1) + ". " + taux.getValeur() + "% sur " + taux.getDureeEnMois() + " mois");
        }

        // choix du taux
        int choixTaux = 0;
        do {
            System.out.print("Veuillez saisir le numéro correspondant au taux souhaité : ");
            choixTaux = sc.nextInt();
            if (choixTaux < 1 || choixTaux > tauxList.size()) {
                System.out.println("Veuillez choisir un taux valide.");
            }
        } while (choixTaux < 1 || choixTaux > tauxList.size());

        Taux tauxChoisi = tauxList.get(choixTaux - 1);

        // date d'effet
        System.out.print("Veuillez saisir la date d'effet au format MM/yyyy : ");
        sc.nextLine();
        String dateEffetStr = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        try {
            LocalDate dateEffet = LocalDate.parse("01/" + dateEffetStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            Pret pret = new Pret(montantDemande, tauxChoisi.getValeur(), LocalDateTime.now(), dateEffet, tauxChoisi.getDureeEnMois());

            // Calcul mensualités
            pret.genererMensualites();

            System.out.println("Voici les détails de votre prêt :");
            System.out.println("Montant emprunté : " + montantDemande);
            System.out.printf("Montant de la mensualité : %.2f €\n", pret.calculerMensualite());
            System.out.println("Mensualités :");

            double totalRembourse = 0;  // cumuler montant total remboursé

            System.out.println("Date       | Mensualité | Part des intérêts | Capital remboursé");
            for (Mensualite mensualite : pret.getMensualites()) {
                totalRembourse += mensualite.getPartCapitalRembourse();  // Ajout du capital remboursé au total
                System.out.printf("%s       %.2f €        %.2f €        %.2f €\n",
                        mensualite.getDatePrelevement().format(DateTimeFormatter.ofPattern("MM/yyyy")),
                        mensualite.getPartCapitalRembourse(),
                        mensualite.getPartInteretsRembourses(),
                        totalRembourse);  // total remboursé chaque mois
            }

            System.out.println("Au revoir et merci d'avoir utilisé notre outil !");
        } catch (Exception e) {
            System.out.println("Erreur lors du traitement de la date d'effet. Veuillez vérifier le format.");
        }
    }
}