
#include "./Header/ABR.h"
#include <iostream>
#include <windows.h>
#include "./Header/Noeud.h"

using namespace std;
int main() {
    SetConsoleOutputCP(65001);
    
    ABR arbre;
    char commande;
    int valeur = 0;

    cout << "Entrez les commandes (I, S, N, H, A, D, G) suivi de la valeur si nécessaire:" << endl;

    while (cin >> commande) {
        switch (commande) {
        case 'I': // Insertion
            cin >> valeur;
            arbre.Inserer(arbre.GetRacine(),valeur);
            break;
        case 'S': // Suppression
            cin >> valeur;
            arbre.Supprimer(arbre.GetRacine(), valeur);
            break;
        case 'N': // Afficher niveau
            cin >> valeur;
            arbre.Afficher_Niveau(arbre.GetRacine(), valeur, 0);
            cout << endl;
            break;
        case 'H': // Afficher hauteur
            cout << "Hauteur: " << arbre.Afficher_hauteur(arbre.GetRacine()) << endl;
            break;
        case 'A': // Afficher descendants (n�cessite impl�mentation)
            cin >> valeur;
            arbre.Afficher_Descendant(arbre.GetRacine(), valeur);
            cout << endl;
            break;
        case 'D': // Afficher d�s�quilibre (n�cessite impl�mentation)
            cout << "D�s�quilibre: " << arbre.Desequilibre() << endl;
            break;
        case 'G': // Afficher ascendants (n�cessite impl�mentation)
            cin >> valeur;
            arbre.Afficher_Ascendant(valeur);
            break;
        default:
            cout << "Commande inconnue." << endl;
        }
        arbre.AideImprimer(arbre.GetRacine(), 0);
        cout << "\nEntrez une nouvelle commande:" << endl;
    }

    return 0;
}