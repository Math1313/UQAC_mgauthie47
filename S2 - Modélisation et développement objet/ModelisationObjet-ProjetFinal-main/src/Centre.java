import java.time.LocalDate;
import java.util.*;
import Observer.Subject;
import Observer.Observer;

public class Centre implements Subject {

    private ArrayList<Chambre> listeChambres = new ArrayList<>();
    private ArrayList<Observer> listeObservers = new ArrayList<>();

    public Centre()
    {
        for(int i = 0; i < 21; i++)
        {
            Chambre nouvelleChambre = new Chambre(4, i);
            nouvelleChambre.ajouterDisponibilite(LocalDate.now(), LocalDate.now(), 25);
            nouvelleChambre.ajouterDisponibilite(LocalDate.now(), LocalDate.now(), 30);
            nouvelleChambre.ajouterDisponibilite(LocalDate.now(), LocalDate.now(), 40);

            this.listeChambres.add(nouvelleChambre);
        }
    }

    public ArrayList<Chambre> getListeChambre()
    {
        return this.listeChambres;
    }

    public void ajouterDisponibilite(int numeroChambre, LocalDate dateDebut, LocalDate dateFin, int prix)
    {
        this.listeChambres.get(numeroChambre).ajouterDisponibilite(dateDebut, dateFin, prix);
        notifyObservers();
    }

    //OBSERVER DESIGN PATTERN
    @Override
    public void addObserver(Observer observer) {
        listeObservers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : listeObservers)
        {
            observer.update("Nouvelle disponibilité.");
        }
    }
    @Override
    public void removeObserver(Observer observer) {
        listeObservers.remove(observer);
    }
}
