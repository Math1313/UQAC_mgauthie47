#include "Professeur.h"

class DossierProfesseur
{
private:
    Professeur * tete = nullptr;
public:
    DossierProfesseur(char * FP); //Construit la liste chainée à partir du fichier FP
    ~DossierProfesseur(); // Détruit la liste chainée
    void supprimer(char * name); // Supprime tous les professeurs ayant le nom name
    char* afficherLeProfMoinsEtudiant() const; // Affiche le professeur avec le moins d'ancienneté ayant le moins d'élève
    char* afficherCoursMoinsDemande() const; // Affiche le cours le moins demandé, choisir le cours donné par le professeur le moins ancien
    int afficherNombreProfPourCours(char * coursDonne) const; // Afficher le nombre de professeur souhaitant donner le cours coursDonne
    void recopier(char * FP); // Écrit la liste chainée modifiéer dans un nouveau fichier
};