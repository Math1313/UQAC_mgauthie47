#pragma once
#include <string>

#include "Cours.h"
#include "Etudiant.h"

using namespace std;

struct Professeur
{
    string nom;
    int ancien;
    Cours* listeCours = nullptr;
    Etudiant* listeEtudiants = nullptr;
    Professeur* suivant;
    
};
