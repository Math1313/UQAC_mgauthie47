//
// 0 - Changer de base de données
//

use uqac
show collections

//
// 0 - Trouver le nombre de professeurs dans la base de données
//

db.professeurs.countDocuments()

//
// 1 - Trouver le professeur Étienne Tremblay
//

db.professeurs.find(
    {
        "nom": "Tremblay",
        "prenom": "Etienne"
    },
    {
        "nom": true,
        "prenom": true
    }
)

//
// 2 - Trouvez le professeur Luc Lamontagne
//

db.professeurs.find(
    {
        "Nom": "Lamontagne",
        "Prenom": "Luc"
    }
)

print("Le problème est que les noms de champs dans l'objet de requête ne correspondent pas aux noms de champs dans la base de données. MongoDB est sensible à la casse, donc Nom et Prenom devraient être nom et prenom.")

//
// 3 - Enlevez les cours donnés à la session précédente par Richard Khoury
//

db.professeurs.updateOne(
    {
        "nom": "Khoury",
        "prenom": "Richard"
    },
    {
        $set: {
            "cours_derniere_session": []
        }
    }
)

//
// 4 - Ajouter un nouveau professeur de votre choix. Il devrait au moins avoir un nom, prenom et une date de naissance ?
//

db.professeurs.insertOne(
    {
        "prenom": "Jean",
        "nom": "Dupont",
        "date_de_naissance": "1980-05-15"
    }
)

//
// 5 - Il semble qu'il y ait un professeur qui soit en double. Est-ce possible d'enlever la deuxieme occurence ? 
//

seen = {};
db.professeurs.find().forEach(function (professeur) {
    key = professeur.nom + "_" + professeur.prenom;
    if (seen[key]) {
        db.professeurs.deleteOne({_id: professeur._id});
        print("Doublon supprimé:");
        print(professeur)
    } else {
        seen[key] = true;
    }
})

//
// 6 - Comment trouver tous les professeurs d'un département commençant par DI - (par exemple DIM) ?
//

db.professeurs.find(
    {
        departement: {
            $regex: /^DI/
        }
    },
    {
        nom: true,
        prenom: true,
        departement: true,
        _id: false
    }
)

//
// 7 - Est-ce qu'il serait possible de n'avoir que leur nom et prénom ?
//

db.professeurs.find(
    {
        departement: {
            $regex: /^DI/
        }
    },
    {
        nom: true,
        prenom: true,
        _id: false
    }
)

//
// 8 - Est-ce qu'il serait possible de normaliser les noms dans la base de données pour que les prénoms soient en minuscule et les noms en majuscule? 
//

db.professeurs.find().forEach(function (professeur) {
    db.professeurs.updateOne(
        {
            _id: professeur._id
        },
        {
            $set: {
                prenom: professeur.prenom.toLowerCase(),
                nom: professeur.nom.toUpperCase()
            }
        }
    );
});

db.professeurs.find()

//
// 9 - À quelle date a été ajouté le professeur Richard Khoury dans la base de données ?
//

db.professeurs.findOne(
    {
        nom: "KHOURY",
        prenom: "richard"
    },
    {
        _id: false,
        nom: true,
        prenom: true,
        date_ajout: {
            $function: {
                body: function (id) {
                    return id.getTimestamp()
                },
                args: ["$_id"],
                lang: "js"
            }
        }
    }
)