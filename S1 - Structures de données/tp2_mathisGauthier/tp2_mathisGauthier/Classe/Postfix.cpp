#include "../Header/Postfix.h"
#include "Windows.h"

#include <string>

using namespace std;

HANDLE CONSOLE_HANDLE = GetStdHandle(STD_OUTPUT_HANDLE);

Postfix::Postfix(const vector<char>& tableau)
{
    
    Tableau = tableau;
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
    
}


bool Postfix::Valider()
{
    bool estValide = false;

    for(char c: Tableau)
    {
        if(!isdigit(c))
        {
            if(c == '(' || c == ')' || c == '-' || c == '+' || c == '*' || c == '/' || c == '%')
            {
                estValide = true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            estValide = true;
        }
    }
    
    return estValide;
}

bool Postfix::Parentheses()
{
    int nbrParenthesesOuvertes = 0;
    int nbrParenthesesFermees = 0;

    for(char c: Tableau)
    {
        if(c == '(')
        {
            nbrParenthesesOuvertes++;
        }
        else if(c == ')')
        {
            nbrParenthesesFermees++;
        }
    }

    if(nbrParenthesesOuvertes == nbrParenthesesFermees)
    {
        return true;
    }
    
    return false;
}

void Postfix::TransformerEnNombres()
{
    for(char c : Tableau)
    {
        if(isdigit(c))
        {
            Pile.push(stoi(string(1, c)));
        }
    }
}

void Postfix::TransformerEnPostfix()
{
    string postfix;
    stack<char> temp;
    for(char c: Tableau)
    {
        if(isdigit(c))
        {
            postfix += c;
        }
        else if(c == '(')
        {
            temp.push(c);   
        }
        else if(c == ')')
        {
            while(temp.top() != '(')
            {
                postfix += temp.top();
                temp.pop();
            }
            temp.pop();
        }
        else
        {
            while(!temp.empty() && Precedence(c) <= Precedence(temp.top()))
            {
                postfix += temp.top();
                temp.pop();
            }
            temp.push(c);
        }
    }

    while(!temp.empty())
    {
        postfix += temp.top();
        temp.pop();
    }

    Tableau.clear();
    for(char c: postfix)
    {
        Tableau.push_back(c);
    }
    
    SetConsoleTextAttribute(CONSOLE_HANDLE, 10);
    cout << "Opération en postfixe:" << postfix << endl;
    
    SetConsoleTextAttribute(CONSOLE_HANDLE, 7);
}

int Postfix::EvaluerExpression()
{
    TransformerEnPostfix();
    while(!Pile.empty())
    {
        Pile.pop();
    }
    for(char c: Tableau)
    {
        if(isdigit(c))
        {
            Pile.push(stoi(string(1, c)));
        }
        else
        {
            int y = Pile.top();
            Pile.pop();
            int x = Pile.top();
            Pile.pop();
            if(c == '+')
            {
                Pile.push(x+y);
            }
            if(c == '-')
            {
                Pile.push(x-y);
            }
            if(c == '*')
            {
                Pile.push(x*y);
            }
            if(c == '/')
            {
                Pile.push(x/y);
            }
            if(c == '%')
            {
                Pile.push(x%y);
            }
        }
    }
    return Pile.top();
}

int Postfix::Precedence(char op)
{
    if(op == '/' || op == '*' || op == '%')
    {
        return 2;
    }
    if(op == '+' || op == '-')
    {
        return 1;
    }

    return -1;
}





