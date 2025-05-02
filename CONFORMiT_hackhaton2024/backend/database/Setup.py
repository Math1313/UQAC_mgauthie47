import os

from peewee import *

# Path de la base de données par défaut
db_host = 'database.db'

# Création de la base de données SQLite
db = SqliteDatabase(db_host)


# Fonction pour initialiser la base de données
def init_db():
    if os.path.exists(db_host):
        print("Database reset")
        os.remove(db_host)

    db.create_tables([User])
    print("Database initialized")


# Classe de base pour les modèles

class BaseModel(Model):
    class Meta:
        database = db


class User(BaseModel):
    DoesNotExist = None
    Id = AutoField(primary_key=True)
    Username = CharField(null=False)
    Password = CharField(null=False)
