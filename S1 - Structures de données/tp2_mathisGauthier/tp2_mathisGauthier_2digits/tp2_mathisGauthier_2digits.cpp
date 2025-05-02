#include "Windows.h"
#include "Header/Postfix.h"

void main(int argc, char* argv[])
{
    
    SetConsoleOutputCP(65001);   
    string operation;
    // Saisie de la chaine de caractères
    cout << "Saisir une équation (sans espace): ";
    cin >> operation;
    // Convertion de la chaine de caractères en vecteur de char
    vector<char> test(operation.begin(), operation.end());
    Postfix post(test);

    // Appelle de la fonction pour calculer le résultat de l'opération
    cout << "Estimation Postfixe:" << post.EvaluerExpression();
}
