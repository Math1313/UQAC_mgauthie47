# Utilise l'image MongoDB
FROM mongo:latest

# Définit le répertoire de travail dans le conteneur
WORKDIR /data/db

# Expose le port par défaut de MongoDB (27017)
EXPOSE 27017

# Importe le script d'importation de données
COPY Data_Loader/import_script.js /data/db/import_script.js

# Install MongoDB client tools
RUN apt-get update && apt-get install -y mongodb-org-tools

# Commande à exécuter au lancement du conteneur
CMD ["mongod"]