import java.time.LocalDate;
import java.util.*;

public class Chambre {
    class Disponibilite
    {
        private LocalDate dateDebut;
        private LocalDate dateFin;
        int prix;

        public Disponibilite(LocalDate dateDebut, LocalDate dateFin, int prix)
        {
            this.dateDebut = dateDebut;
            this.dateFin = dateFin;
            this.prix = prix;
        }
    }

    private ArrayList<Disponibilite> listeDisponibilite = new ArrayList<>();
    int capacite;
    int numero;

    public Chambre(int capacite, int numero)
    {
        this.capacite = capacite;
        this.numero = numero;
    }

    public void ajouterDisponibilite(LocalDate dateDebut, LocalDate dataFin, int prix)
    {
        Disponibilite nouvelleDisponibilite = new Disponibilite(dateDebut, dataFin, prix);
        this.listeDisponibilite.add(nouvelleDisponibilite);
    }

    public void afficherInfo()
    {
        System.out.println("--------------------------");
        System.out.println("Numéro de la chambre:   " + this.numero);
        System.out.println("Capacité de la chambre: " + this.capacite);
        System.out.println("--------------------------");
        this.afficherDispo();
    }

    public void afficherDispo()
    {
        for(Disponibilite disponibilite: this.listeDisponibilite)
        {
            System.out.println(disponibilite.prix);
            System.out.println(disponibilite.dateDebut);
            System.out.println(disponibilite.dateFin);
        }
    }
}
