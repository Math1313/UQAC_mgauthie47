#include <iostream>
#include "Header/DossierProfesseur.h"
#include <fstream>
#include "Windows.h"

using namespace std;

char fileNameLinkList[] = "./Fichier/FP.txt";
DossierProfesseur maliste(fileNameLinkList);

void op(string typeOperation);

void main(int argc, char* argv[])
{
    
    SetConsoleOutputCP(65001);
    char fileNameOperation[] = "./Fichier/FT.txt";
    string ligne;
    ifstream fichierOperation(fileNameOperation);

    if(!fichierOperation)
    {
        cout << "Le fichier n'a pas pu être ouvert" << endl;
    }
    else
    {
        while(getline(fichierOperation, ligne))
        {
            op(ligne);
        }
    }
}

void op(string typeOperation) // Fonction de gestion des opérations possibles
{
    if(typeOperation[0] == '-') // Supprimer un professseur
    {
        string nomProf = typeOperation.substr(2, typeOperation.length() - 2);
        char * nomProfCharArray = new char[nomProf.length() + 1];
        strcpy_s(nomProfCharArray,nomProf.length() + 1, nomProf.c_str());
        maliste.supprimer(nomProfCharArray);
    }
    else if(typeOperation[0] == '#') // Afficher le professeur avec le moins d'étudiant
    {
        cout << "Professeur avec le moins d'étudiant: " << maliste.afficherLeProfMoinsEtudiant() << endl;
    }
    else if(typeOperation[0] == '*') // Afficher le cours le moins demandé.
    {
        cout << "Cours le moins demandé par les professeurs: " << maliste.afficherCoursMoinsDemande() << endl;
    }
    else if(typeOperation[0] == '%') // Afficher le nombre de professeurs pour un cours donné
    {
        string nomCours = typeOperation.substr(2, typeOperation.length() - 2);
        char * nomCoursCharArray = new char[nomCours.length() + 1];
        strcpy_s(nomCoursCharArray,nomCours.length() + 1, nomCours.c_str());
        cout << "Nombre de professeurs souhaitant enseigner le cours " << nomCours
         << ": " << maliste.afficherNombreProfPourCours(nomCoursCharArray) << endl;
    }
    else if(typeOperation[0] == '$') // Recopier la nouvelle liste chainée dans un fichier
    {
        maliste.recopier(fileNameLinkList);
    }
}