import java.time.LocalDate;

public class Main
{
    public static void main(String[] args)
    {
        Centre testCentre = new Centre();
//        for(Chambre chambre: testCentre.getListeChambre())
//        {
//            chambre.afficherInfo();
//        }
        Organisme organisme = new Organisme();
        testCentre.addObserver(organisme);
        testCentre.ajouterDisponibilite(3, LocalDate.now(), LocalDate.now(), 24);

        testCentre.ajouterDisponibilite(8, LocalDate.now(), LocalDate.now(), 56);
    }
}