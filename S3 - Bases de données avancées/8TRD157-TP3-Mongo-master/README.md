# MongoDB

## Lancer un conteneur

1. Ouvrir un terminal.
2. Naviguer jusqu'au répertoire contenant le fichier `start.cmd` en utilisant la commande `cd` :
   ```sh
   cd chemin/vers/le/repertoire
3. Exécuter le fichier `start.cmd` dans le terminal:
   ```sh
   .\start.cmd
   ```
4. Une fois le conteneur lancé, le terminal est automatiquement configuré pour utiliser le shell MongoDB (`mongosh`).

5. (Optionel) Ouvrir un autre terminal pour exécuter des commandes MongoDB.
   ```sh
   docker exec -it mongo-container mongosh
   ```

## Lancer un script loader

Les scripts pour importer des données dans la base de données sont copiés dans les fichiers du conteneur selon ce chemin :
`/data/db/import_script.js`. Pour exécuter ce dernier, il suffit de faire la commande `mongosh` suivante :

  ```sh
  load("/data/db/import_script.js")
  ```

# Commandes utiles MongoDB

1. **Créer une base de données / Switcher de base de données** :
   ```sh
   use nom_de_la_base_de_donnees
   ```
2. **Voir la liste des bases de données** :
   ```sh
   show dbs
   ```
3. **Voir les collections** :
   ```sh
   show collections
   ```
