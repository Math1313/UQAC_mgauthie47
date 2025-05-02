// Postfix.h
#ifndef POSTFIX_H
#define POSTFIX_H

#include <iostream>
#include <stack>
#include <vector>

using namespace std;

class Postfix {
private:
    stack<int> Pile;
    vector<string> TableauNombreCree;
    vector<char> Tableau;

public:
    Postfix(const vector<char>& tableau);
    ~Postfix();// pour le bonus
    bool Valider();
    bool Parentheses();
    void TransformerEnNombres(); // pour le bonus
    void TransformerEnPostfix();
    int Precedence(string op); // facultative
    int EvaluerExpression();
    bool OperateurEstPlusImportant(char stackTop, char charater);
    bool isNumber(const string& s);
};

#endif // POSTFIX_H