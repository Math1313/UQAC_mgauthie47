#include "Header/Postfix.h"
#include "Windows.h"

using namespace std;

void main()
{
    SetConsoleOutputCP(65001);   
    string operation;
    cout << "Saisir une équation (sans espace): ";
    cin >> operation;
    vector<char> test(operation.begin(), operation.end());
    Postfix post(test);
    cout << "Estimation Postfixe:" << post.EvaluerExpression();
}
