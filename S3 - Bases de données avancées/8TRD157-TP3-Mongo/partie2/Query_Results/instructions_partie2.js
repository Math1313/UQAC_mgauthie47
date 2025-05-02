//
// 0 - Changer de base de données
//

use uqac
show collections

//
// 0 - Quel est le nom du cours 8TRD157 et quel est son enseignant?
//

db.cours.find(
    {
        "cours.sigle": "8TRD157"
    },
    {
        _id: false,
        "cours.nom": true,
        enseignat_actuel: true // Typo dans le nom de la clé (C'est normal)
    }
)

//
// 1 - Corrigez le typo dans le nom de la cle pour l'enseignant actuel du cours 8TRD157
//


db.cours.update(
    {
        "cours.sigle": "8TRD157"
    },
    {
        $set: {
            enseignant_actuel: "Jimmy Girard-Nault"
        },
        $unset: {
            enseignat_actuel: 1
        }
    }
)

db.cours.find(
    {
        "cours.sigle": "8TRD157"
    },
    {
        _id: false,
        "cours.nom": true,
        enseignant_actuel: true
    }
)


//
// 2 - Il faut incrémenter le compteur de 1 pour tous les cours de l'automne
//

db.cours.updateMany(
    {
        "session": {
            $in: ["Automne"]
        }
    },
    {
        $inc: {
            "nombre_de_fois_offert": 1
        }
    }
)

db.cours.find()

//
// 3 - Il semble qu'il manque trois professeurs pour le cours 8INF433. Veuillez ajouter "Michel Louvain", "Rick Ashtley" et "Elon Musk" à la liste.
//

db.cours.updateOne(
    {
        "cours.sigle": "8INF433"
    },
    {
        $push: {
            anciens_enseignants: {
                $each: ["Michel Louvain", "Rick Ashtley", "Elon Musk"]
            }
        }
    }
)

db.cours.find()

//
// 4 - Après avoir parlé au département des ressources humaines, vous décidez d'enlever "Michel Louvain" car ils ne sera plus professeur à l'université.
//

db.cours.updateOne(
    {
        "cours.sigle": "8INF433"
    },
    {
        $pull: {
            anciens_enseignants: {
                $in: ["Michel Louvain"]
            }
        }
    }
)

db.cours.find()

// Pour préparer des réponse aux question fréquente des étudiants, vous décidez de faire une peu d'exploration dans vos données

//
// 5 - Combien de cours on eu moins de 2 enseignants ?
//

db.cours.find(
    {
        $where: "this.anciens_enseignants.length >= 2"
    },
    {
        _id: false,
        "cours.sigle": true,
        "anciens_enseignants": true
    }
).count()

//
// 6 - Quels sont les 2 cours du DIM (débutant par 8) ou du DSA (débutant par 6) ayant été donnés le plus souvent et combien de fois ont-ils ete offerts?
//

db.cours.find(
    {
        $or: [
            {"cours.sigle": /^8/},
            {"cours.sigle": /^6/}
        ]
    },
    {
        _id: false,
        "cours.sigle": true,
        "cours.nom": true,
        "nombre_de_fois_offert": true
    }
).sort({"nombre_de_fois_offert": -1}).limit(2)

//
// 7 - Quels cours sont donnés à l'automne et à l'hiver ?
//

db.cours.find(
    {
        session:
            {
                $all: ["Automne", "Hiver"]
            }
    },
    {
        _id: false,
        "cours.sigle": true,
        "cours.nom": true
    }
)

//
// 8 - Quel est le cours ayant eu son local officiel au P1-6350 13 fois ?
//

db.cours.find(
    {
        local_officiel: {
            $elemMatch: {
                local: "P1-6350",
                nombre_de_cours: 13
            }
        }
    },
    {
        _id: false,
        "cours.sigle": true,
        "cours.nom": true
    }
)

//
// 9 - Quel est le cours le plus constant en terme de nombre d'étudiants? On cherche un cours qui a toujours eu entre 30 et 60 étudiants
//

db.cours.find({
    $where: function() {
        return this.nombre_etudiants?.every(x => x >= 30 && x <= 60)
    }
})