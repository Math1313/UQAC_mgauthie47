#include "Noeud.h"

class ABR {
    Noeud * racine;// La racine de l’arbre binaire de recherche.
    public:
        ABR(); // Construit l’arbre dont la racine est à l’adresse racine.2
        ~ABR( ); // Supprime l’espace mémoire occupé par l’arbre dont la racine est à l’adresse racine.
        Noeud *& GetRacine();
        void Inserer(Noeud *& n, int d); // insère le nœud de valeur d dans l’arbre.
        void Supprimer(Noeud *& n, int d); // Supprime le nœud de valeur d de l’arbre.
        void Afficher_Niveau(Noeud *& n, int d, int niveau); //Affiche les éléments de l’arbre qui sont au niveau d.
        int Afficher_hauteur(Noeud *& n); //Affiche la hauteur de l’arbre. 
        void Afficher_Descendant(Noeud *& n, int d); //Affiche les descendants du nœud de valeur d. 
        int Desequilibre (); // affiche le nombre de nœuds dont la valeur absolue de la différence de hauteurs de leurs sous-arbres gauche et droit est supérieure strictement à 1. (bonus 5pts)
        void Afficher_Ascendant(int d);//Affiche les ascendants du nœud de valeur d. (bonus 5pts)

        Noeud * SupprimerMin(Noeud *& n);
        void AideAfficherDescendant(Noeud *& n);

        void AideImprimer(Noeud *& n, int niveau);
    }; 