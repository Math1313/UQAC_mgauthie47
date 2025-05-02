# Hackaton 2024
Avec:
Mathis Audusseau
Olivier Bouchard
Jean-Philippe Tremblay
Mathis Gauthier

# Setup AWS Credentials 🔐
Connectez vous aux service AWS : https://boto3.amazonaws.com/v1/documentation/api/latest/guide/credentials.html


# Pour le Backend 🖥️
```
cd ./backend
```

## Etape 1 🐍
Installer python 3.11. Débrouillez-vous. 


## Etape 2 🌱
Créer un environnement virtuel dédié pour le projet
```
python -m venv .venv
```

## Etape 3 ▶️
Activer l'environnement virtuel
```
.venv\Scripts\activate
```

## Etape 4 📥
Installer les requirements
```
pip install -r requirements.txt
```

## Etape 5 🔑
Génerer un secret token
```
chainlit create-secret
```
Créez un fichier `.env` à partir du fichier `.env.example` et renseignez-y votre token.

## Etape 6 🚀
C'est parti ! :
```
uvicorn app:app --host 0.0.0.0 --port 80
```

# Frontend 🖼️
```
cd ./frontend
```

## Etape 1 📦
Installez nodeJS. Débrouillez-vous.

## Etape 2 🔧
Installez les modules node.
```
npm i
```

## Etape 3 🚀
C'est parti ! :
```
npm run dev
```


# Custom frontend with Chainlit! 🔗

The Chainlit websocket client is available in the [@chainlit/react-client](https://www.npmjs.com/package/@chainlit/react-client) npm package. 📦

https://github.com/Chainlit/cookbook/assets/13104895/5cc20490-2150-44da-b016-7e0e2e12dd52