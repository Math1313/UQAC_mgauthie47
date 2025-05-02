
#include "../Header/DossierProfesseur.h"
#include <fstream>
#include <iostream>
#include <string>


using namespace std;

DossierProfesseur::DossierProfesseur(char * FP)
{
    //Définition des variables
    Cours * coursTail = nullptr;
    Cours * coursTemp = nullptr;
    Etudiant * etudiantTail = nullptr;
    Etudiant * etudiantTemp = nullptr;
    int nombreEtCroise = 0;

    //Ouverture du fichier et tentative de lecture de celui-ci
    ifstream fichier(FP);
    if(!fichier)
        cout << "Impossible d'ouvrir le fichier !";
    else
    {
        string ligne;
        int compteurBoucle = 0;
        Professeur * tailProf = nullptr;
        //Lecture du fichier ligne par ligne
        while (getline(fichier, ligne))
        {
            //Si c'est le nom du professeur
            if(compteurBoucle == 0)
            {
                //Création du professeur qui sera la tête et la fin s'il n'y a pas déjà une tête
                Professeur* newProf = new Professeur{ligne, 0, nullptr, nullptr, nullptr};
                if(!tete)
                {
                    tete = tailProf = new Professeur;
                    tete ->nom = ligne;
                }
                //S'il y a déjà une tête, le nouveau prof sera le noeud suivant de la queue et la queue
                else
                {
                    tailProf->suivant = newProf;
                    tailProf = newProf;
                }
                compteurBoucle++;
            }
            //Si c'est la deuxième ligne
            else if(compteurBoucle == 1)
            {
                //Définir l'ancienneté du professeur tail par l'ancienneté lue dans le fichier
                tailProf -> ancien = stoi(ligne);
                compteurBoucle++;
            }
            //Si nous sommes dans une autre ligne que les deux premières lignes
            else
            {
                //Incrémenter le compteur si on croise le caractère & sur une ligne
                if(ligne == "&")
                {
                    nombreEtCroise++;
                }
                //Si le compteur de caractère & est de 0, on crée des cours pour le professeur en queue de liste
                else if(nombreEtCroise == 0)
                {
                    coursTemp = new Cours{ligne, nullptr};
                    if(!tailProf->listeCours)
                    {
                        tailProf->listeCours = coursTail = coursTemp;
                    }
                    else
                    {
                        coursTail->suivant = coursTemp;
                        coursTail = coursTemp;
                    }
                }
                //Si nous avons croisé un caractère &, on crée des étudiants pour le professeur en queue
                else if(nombreEtCroise == 1)
                {
                    etudiantTemp = new Etudiant{ligne, nullptr};
                    if(!tailProf->listeEtudiants)
                    {
                        tailProf->listeEtudiants = etudiantTail = etudiantTemp;
                    }
                    else
                    {
                        etudiantTail->suivant = etudiantTemp;
                        etudiantTail = etudiantTemp;
                    } 
                }
                //Si on croise le nombre de caractère & est de 2, on remet les compteurs à 0 pour recommencer la boucle.
                if(nombreEtCroise == 2)
                {
                    nombreEtCroise = 0;
                    compteurBoucle = 0;
                }
            }
        }
    }
}

DossierProfesseur::~DossierProfesseur()
{
    
}

void DossierProfesseur::supprimer(char* name)
{
    //Définition des variables
    Professeur * profPrecedent;
    Professeur * profCourant;

    //On met le profCourant et le profPrecedent sur la tête pour ne pas altérer la tête
    profCourant = profPrecedent = tete;
    //Boucler tant que profCourant n'est pas un pointeur vide
    while (profCourant)
    {

        //Si le nom du profCourant est le bon
        if(profCourant->nom == name)
        {
            //Si le professeur à supprimer est à la tête, on défini la tête sur le suivant du professeur
            if(profCourant == tete)
            {
                tete = profCourant->suivant;
            }
            //Si le professeur à supprimer est la queue de la liste, on défini la queue sur le professeur précédent
            if(profCourant -> suivant == nullptr)
            {
                profPrecedent->suivant = nullptr;
            }
            //Changer le professeur suivant du professeur précédent pour ne pas créer de trou dans la liste.
            profPrecedent->suivant = profCourant->suivant;
            //Suppression du profCourant dans la liste
            delete profCourant;
            profCourant = profPrecedent->suivant;
        }
        //Si le professeur courant n'a pas le nom recherché
        else
        {
            profPrecedent = profCourant;
            profCourant = profCourant->suivant;
        }
    }
}

char* DossierProfesseur::afficherLeProfMoinsEtudiant() const
{
    //Définition des variables
    int nombreElevePrecedent = NULL;
    Etudiant * etudiantTemp;
    Professeur * profTemp;
    Professeur * profAvecLeMoinsEleve = new Professeur{"", NULL, nullptr, nullptr, nullptr};

    //Définition du profTemp sur la tête pour ne pas altérer la tête de la liste
    profTemp = tete;
    //Tant que le professeur courant n'est pas un pointeur vide
    while(profTemp)
    {
        int nombreElevePourProfTemp = 0;
        etudiantTemp = profTemp->listeEtudiants;
        //Tant que la liste des étudiants du professeur n'est pas un pointeur vide
        while(etudiantTemp)
        {
            //Incrémenter le compteur du nombre d'étudiants
            nombreElevePourProfTemp++;
            etudiantTemp = etudiantTemp->suivant;
        }
        //Si le nombre d'étudiant présent est plus petit que le nombre d'élève plus petit
        //OU que le nombre d'élève plus petit n'existe pas.
        if(nombreElevePourProfTemp < nombreElevePrecedent || !nombreElevePrecedent)
        {
            //Garder en mémoire un pointeur vers le professeur ayant le moins d'étudiant et changer le compteur plus petit
            profAvecLeMoinsEleve = profTemp;
            nombreElevePrecedent = nombreElevePourProfTemp;
        }
        // Si le nombre d'étudiant présent est égal que le nombre d'élève plus petit 
        else if (nombreElevePourProfTemp == nombreElevePrecedent)
        {
            //On choisi le professeur qui a le moins d'ancienneté entre les deux
            if(profTemp->ancien < profAvecLeMoinsEleve->ancien)
            {
                profAvecLeMoinsEleve = profTemp;
            }
        }
        profTemp = profTemp->suivant;
    }
    char *chaineChar = new char[profAvecLeMoinsEleve->nom.length() + 1];
    strcpy_s(chaineChar, profAvecLeMoinsEleve->nom.length() + 1, profAvecLeMoinsEleve->nom.c_str());
    
    return chaineChar;
}

char* DossierProfesseur::afficherCoursMoinsDemande() const
{
    //Déclaration des variables
    Professeur * profTemp = tete;
    Professeur * profTemp2 = tete;
    Cours * coursTemp = nullptr;
    Cours * coursTemp2 = nullptr;
    int compteur;
    int compteurPlusPetit = 0;
    int ancienneteProf = 0;
    string nomCoursMoinsDemande = "";

    //Tant que le profTemp n'est pas égale à un pointeur vide
    while (profTemp)
    {
        coursTemp = profTemp->listeCours;
        //Tant que coursTemp n'est pas égale à un pointeur vide
        while (coursTemp)
        {
            compteur = 0;

            //Pour chaque cours dans chaque professeur, on va reboucler dans chaque professeur et dans chaque cours,
            //pour vérifier si le cours actuelle est présent dans d'autres professeurs
            while (profTemp2)
            {
                coursTemp2 = profTemp2->listeCours;
                while (coursTemp2)
                {
                    if(coursTemp->sigle == coursTemp2->sigle)
                    {
                        //Si le nom du cours que l'on cherche est trouvé dans un autre professeur, on incrémente le compteur
                        compteur++;
                    }
                    coursTemp2 = coursTemp2->suivant;
                }
                profTemp2 = profTemp2->suivant;
            }
            //Si le compteur est plus petit que le compteur qui compte le plus petit ou que c'est le premier cours vérifier
            if(compteur < compteurPlusPetit || compteurPlusPetit == 0)
            {
                //Si le compteur n'est pas égale à 0
                if(compteur != 0)
                {
                    
                    compteurPlusPetit = compteur;
                    nomCoursMoinsDemande = coursTemp->sigle;
                    ancienneteProf = profTemp->ancien;
                }
            }
            //Si le compteur est égale à un nombre de cours qui est présentement le plus petit
            else if(compteur == compteurPlusPetit)
            {
                //On valide l'ancienneté du professeur pour garder le compteur du professeur le moins ancien
                if((profTemp->ancien < ancienneteProf || ancienneteProf == 0) && compteur != 0)
                {
                    compteurPlusPetit = compteur;
                    nomCoursMoinsDemande = coursTemp->sigle;
                    ancienneteProf = profTemp->ancien;
                }
            }
            coursTemp = coursTemp->suivant;
            profTemp2 = tete;
        }
        
        profTemp = profTemp->suivant;
    }

    char *chaineChar = new char[nomCoursMoinsDemande.length() + 1];
    strcpy_s(chaineChar, nomCoursMoinsDemande.length() + 1, nomCoursMoinsDemande.c_str());
    
    return chaineChar;
}

int DossierProfesseur::afficherNombreProfPourCours(char* coursDonne) const
{
    //Déclaration des variables
    int compteurProf = 0;
    Professeur * profTemp;
    Cours * coursTemp;
    
    //Définition du profTemp sur la tête pour ne pas altérer la tête de la liste
    profTemp = tete;

    //Tant que le professeur courant n'est pas un pointeur vide
    while(profTemp)
    {
        coursTemp = profTemp->listeCours;
        //Tant que la liste des cours n'est pas un pointeur vide
        while(coursTemp)
        {
            //Si le nom du cours est le même que celui recherché
            if(coursTemp->sigle == coursDonne)
            {
                //Augmenter le compteur qui compte le nombre de professeur
                compteurProf++;
            }
            coursTemp = coursTemp ->suivant;
        }
        profTemp = profTemp->suivant;
    }

    
    return compteurProf;
}

void DossierProfesseur::recopier(char* FP)
{
    //Déclaration des variables
    Professeur * profTemp = tete;
    Cours * coursTemp;
    Etudiant * etudiantTemp;

    //Ouverture du fichier
    ofstream fichier(FP);
    //Tentative de lecture du fichier pour valider s'il fonctionne
    if(!fichier)
    {
        cout << "Le fichier n'a pas pu être ouvert.";
    }
    else
    {
        //Tant que le profTemp n'est pas égale à un pointeur vide.
        while (profTemp)
        {
            //Écrire les informations du professeur sur les premières lignes
            fichier << profTemp->nom << '\n';
            fichier << profTemp->ancien << '\n';
            coursTemp = profTemp->listeCours;
            //Pour chaque cours dans la listeCours, on écrit une nouvelle ligne dans le fichier
            while (coursTemp)
            {
                fichier << coursTemp->sigle << '\n';
                coursTemp = coursTemp->suivant;
            }
            //Séparer les cours et les étudiants par un caractère &
            fichier << '&' << '\n';
            etudiantTemp = profTemp->listeEtudiants;
            //Pour chaque étudiant dans la listeÉtudiant, on écrit une nouvelle ligne dans le fichier
            while (etudiantTemp)
            {
                fichier << etudiantTemp->nom << '\n';
                etudiantTemp = etudiantTemp->suivant;
            }
            //Séparer les cours du professeur courant et le futur professeur qui sera écrit
            fichier << '&' << '\n';
            profTemp = profTemp->suivant;
        }
        fichier.close();
    }
}





