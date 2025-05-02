#include "../Header/Postfix.h"
#include "Windows.h"

#include <string>

using namespace std;

HANDLE CONSOLE_HANDLE = GetStdHandle(STD_OUTPUT_HANDLE);

Postfix::Postfix(const vector<char>& tableau)
{

    // Assignation du vecteur passé en paramètre au vecteur de la classe Postfix
    Tableau = tableau;

    // Validation que la chaine de caractère est valide
    //      (bon nombre de parenthèses et caractères valides)
    while(!Valider() || !Parentheses())
    {
        string operation;
        SetConsoleTextAttribute(CONSOLE_HANDLE, 12);
        cout << "Saisir une équation valide (sans espace): ";
        SetConsoleTextAttribute(CONSOLE_HANDLE, 7);
        cin >> operation;
        Tableau = vector<char> (operation.begin(), operation.end());
    }
}

Postfix::~Postfix()
{
    //TODO: Trouver quoi mettre dans le destructeur.
}


bool Postfix::Valider()
{
    // Itération dans tous les caractères du vecteur
    for(char c: Tableau)
    {
        if(!isdigit(c))
        {
            if(c == '(' || c == ')' || c == '-' || c == '+' || c == '*' || c == '/' || c == '%')
            {
                
            }
            else
            {
                // Retourne faux si le caractère lu n'est pas un caractère valide
                return false;
            }
        }
    }

    // Retourne vrai si on se rend à la fin, les caractères sont donc tous valide
    return true;
}

bool Postfix::Parentheses()
{
    int nbrParenthesesOuvertes = 0;
    int nbrParenthesesFermees = 0;

    // Itération dans tous les caractères du vecteur
    for(char c: Tableau)
    {
        // Si on croise une parenthèse d'un côté, on incrémente le bon compteur
        if(c == '(')
        {
            nbrParenthesesOuvertes++;
        }
        else if(c == ')')
        {
            nbrParenthesesFermees++;
        }
    }

    // Retourne vrai si le nombre de parenthès ouvrantes et fermantes sont pareil
    if(nbrParenthesesOuvertes == nbrParenthesesFermees)
    {
        return true;
    }

    // Retourne faux en d'autre cas
    return false;
}

// Cette fonction transforme les caractères en string pour pouvoir gérer le cas où
// il y a plusieurs digits dans un nombre.
// Le résultat est poussé dans un nouveau vecteur de type string.
void Postfix::TransformerEnNombres()
{
    stack<int> tempPile;
    vector<string> test;
    int nombreAjouter = 0;
    int tempPileSizeStart;

    // Itération dans tous les caractères du vecteur
    for(char c : Tableau)
    {
        // Si le caractère est un nombre, on pile dans une pile temporaire
        if(isdigit(c))
        {
            tempPile.push(stoi(string(1, c)));
        }
        else
        {
            // Si on croise d'autres choses qu'un caractère
            
            tempPileSizeStart = tempPile.size();
            // Tant que la pile temporaire n'est pas vide, on incrémente le nombre à ajouter
            // selon ce qui avait dans la pile en utilisant la méthode pow pour mettre les chiffres au bonne place.
            // Par exemple, si le chiffre est 123, dans la pile il sera stocké comme 3 - 2 - 1.
            // Il faut donc dire 3*10^0 + 2*10^1 + 2*10^2 pour obtenir 123
            while(!tempPile.empty())
            {
                nombreAjouter += tempPile.top() * pow(10, tempPileSizeStart - tempPile.size());
                tempPile.pop();
            }
            // On push le nombre à ajouter créé dans la Pile de nombre.
            Pile.push(nombreAjouter);
            // Si le nombre est plus grand que 0, donc non nul, on le push dans un nouveau vecteur
            if(nombreAjouter > 0)  TableauNombreCree.push_back(to_string(nombreAjouter));
            nombreAjouter = 0;
            // On push le caractère croisé dans le vector
            TableauNombreCree.push_back(string(1, c));
        }
    }

    // Ici on fait la même chose que précédement pour le dernier chiffre, comme une opération terminera toujours pas un chiffre.
    tempPileSizeStart = tempPile.size();
    while(!tempPile.empty())
    {
        nombreAjouter += tempPile.top() * pow(10, tempPileSizeStart - tempPile.size());
        tempPile.pop();
    }
    Pile.push(nombreAjouter);
    TableauNombreCree.push_back(to_string(nombreAjouter));
}

// Cette fonction réordonne le TableauNombreCree en notation postfixe.
void Postfix::TransformerEnPostfix()
{
    string postfix;
    stack<string> bufferString;
    vector<string> notationPostfixeTemp;

    // Itération pour chaque string dans le vecteur TableauNombreCree
    for(string c: TableauNombreCree)
    {
        // Si le string est un nombre, on le pousse dans un vecteur temporaire qui accueil la notation postfixe
        if (isNumber(c))
        {
            notationPostfixeTemp.push_back(c);
        }
        // Si c'est une parenthèse ouvrante, on push dans une pile qui sert de mémoire
        else if(c == "(")
        {
            bufferString.push(c);   
        }
        // Si on croise une parenthèse fermente
        else if(c == ")")
        {
            // Tant qu'on croise pas une parenthèse ouvrante dans la pile,
            // on push le top de la pile dans le vecteur qui accueil la notation postfixe
            while(bufferString.top() != "(")
            {
                notationPostfixeTemp.push_back(bufferString.top());
                bufferString.pop();
            }
            bufferString.pop();
        }
        else
        {
            // Si le caractère est un opérande, on vérifie sa priorité d'opération et on agit en conséquence
            // pour piler dans le bon ordre.
            while(!bufferString.empty() && Precedence(c) <= Precedence(bufferString.top()))
            {
                notationPostfixeTemp.push_back(bufferString.top());
                bufferString.pop();
            }
            bufferString.push(c);
        }
    }

    // À la fin, on vide lr buffer dans le vecteur qui accueil la notation postfixe
    while(!bufferString.empty())
    {
        notationPostfixeTemp.push_back(bufferString.top());
        bufferString.pop();
    }

    // Vidder le vecteur
    TableauNombreCree.clear();
    SetConsoleTextAttribute(CONSOLE_HANDLE, 10);
    // Mettre le vecteur contenant la notation postfixe dans le vecteur de la classe et créer un string
    // contenant la notation postfixe
    for(string c: notationPostfixeTemp)
    {
        TableauNombreCree.push_back(c);
        postfix += c;
    }
    cout << "Opération en postfixe: " << postfix << endl;
    SetConsoleTextAttribute(CONSOLE_HANDLE, 7);
    
    
}

int Postfix::EvaluerExpression()
{
    // Appelle des fonctions préalables à l'évaluation de l'équation
    TransformerEnNombres();
    TransformerEnPostfix();
    stack<int> bufferNombre;

    // Itération de tous les strings dans le vecteur contenant la notation postfixe.
    for(string c: TableauNombreCree)
    {
        // Si c'est un nombre, on push dans une pile temporaire contenant les nombres
        if(isNumber(c))
        {
            bufferNombre.push(stoi(c));
        }
        else
        {
            // y sera le top de la pile temporaire
            int y = bufferNombre.top();
            bufferNombre.pop();
            // x sera le nouveau top de la pile temporaire
            int x = bufferNombre.top();
            bufferNombre.pop();
            
            // Effectuer l'opération et push le résultat selon l'opérande contenu dans le caractère
            if(c == "+")
            {
                bufferNombre.push(x+y);
            }
            if(c == "-")
            {
                bufferNombre.push(x-y);
            }
            if(c == "*")
            {
                bufferNombre.push(x*y);
            }
            if(c == "/")
            {
                bufferNombre.push(x/y);
            }
            if(c == "%")
            {
                bufferNombre.push(x%y);
            }
        }
    }
    // On retourne le top de la pile, qui se trouve à être le résultat de l'équation
    return bufferNombre.top();
}

int Postfix::Precedence(string op)
{
    if(op == "/" || op == "*" || op == "%")
    {
        return 2;
    }
    if(op == "+" || op == "-")
    {
        return 1;
    }

    return -1;
}

bool Postfix::isNumber(const string& s)
{
    for(char c: s)
    {
        if(isdigit(c) == 0)
        {
            return false;
        }
        return true;
    }
}






