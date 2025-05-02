#include "../Header/ABR.h"

#include <assert.h>
#include <iostream>
#include <vcruntime.h>
#include <Windows.h>

ABR::ABR()
{
    
}

ABR::~ABR()
{
    
}

Noeud *& ABR::GetRacine()
{
    return racine;
}


void ABR::Inserer(Noeud *&n, int d)
{
    if(n == nullptr)
    {
        n = new Noeud{d, nullptr, nullptr};
    }
    else
    {
        if(d > n->valeur)
        {
            Inserer(n->droit, d);
        }
        else
        {
            Inserer(n->gauche, d);
        }
    }
}

void ABR::Supprimer(Noeud *& n, int d)
{
    if(n == nullptr)
    {
        std::cout << d << " n'est pas dans l'arbre." << std::endl;
    }
    else
    {
        if(d > n->valeur)
        {
            Supprimer(n->droit, d);
        }
        else
        {
            if( d < n->valeur)
            {
                Supprimer(n->gauche, d);
            }
            else
            {
                Noeud * temp = n;
                if(n->gauche == nullptr)
                {
                    n = n->droit;
                }
                else
                {
                    if(n->droit == nullptr)
                    {
                        n = n->gauche;
                    }
                    else
                    {
                        temp = SupprimerMin(n->droit);
                        n->valeur = temp->valeur;
                    }
                }
                delete temp;
            }
        }
    }
}

void ABR::Afficher_Niveau(Noeud *& n, int d, int niveau)
{
    if(n == nullptr)
    {
        return;
    }
    
    if(niveau == d)
        std::cout << n->valeur << " - ";
        
    Afficher_Niveau(n->gauche, d, niveau + 1);
    Afficher_Niveau(n->droit, d, niveau + 1);
    
}

int ABR::Afficher_hauteur(Noeud *& n)
{
    if(n == nullptr)
    {
        return 0;
    }
    
    return 1 + max(Afficher_hauteur(n->gauche), Afficher_hauteur(n->droit));
    
}
void ABR::Afficher_Descendant(Noeud *& n, int d)
{
    // Afficher les descendants du noeud de valeur d
    if(n == nullptr)
        return;

    if(n->valeur == d)
    {
        AideAfficherDescendant(n);
    }
    else
    {
        Afficher_Descendant(n->gauche, d);
        Afficher_Descendant(n->droit, d);
    }

    
}

int ABR::Desequilibre()
{
    //BONUS
    return 0;
}

void ABR::Afficher_Ascendant(int d)
{
    //BONUS
}

Noeud* ABR::SupprimerMin(Noeud*& n)
{
    assert(n != nullptr);
    if(n->gauche != nullptr)
    {
        return SupprimerMin(n->gauche);
    }
    Noeud * temp = n;
    n = n->droit;
    return temp;
}

void ABR::AideAfficherDescendant(Noeud*& n)
{
    if(n == nullptr)
    {
        return;
    }

    AideAfficherDescendant(n->gauche);
    AideAfficherDescendant(n->droit);
    
    std::cout << n->valeur << " - ";
}


void ABR::AideImprimer(Noeud*& n, int niveau)
{
    if(n == nullptr)
    {
        return;
    }
    AideImprimer(n->gauche, niveau + 1);

    for(int i = 0; i < niveau; i++)
    {
        std::cout << " ";
    }
    std::cout << n->valeur;

    AideImprimer(n->droit, niveau+1);
}










