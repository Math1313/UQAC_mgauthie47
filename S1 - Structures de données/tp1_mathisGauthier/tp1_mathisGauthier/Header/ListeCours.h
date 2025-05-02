#include "DossierProfesseur.h"
#include "Professeur.h"

class ListeCours
{
private:
    Cours * tete = nullptr;
public:
    ListeCours(DossierProfesseur * dossierProfesseur);
    ListeCours();
};