# 8TRD157-TP4-Optimisation-SQL

## PURGER LA BIN

PURGE RECYCLEBIN;

## Setup une BD Oracle XE dans un container Docker

1. Ouvrir un terminal et faire les commandes suivantes :
    - docker pull gvenzl/oracle-xe
    - docker run -d -p 1521:1521 -e ORACLE_PASSWORD=user gvenzl/oracle-xe

2. Aller sur la console du conteneur et se connecter en sysdba :
    - sqlplus sys as sysdba
    - Entrer le mot de passe : user
3. Créer un user et lui donner les droits :
    - CREATE USER test_user IDENTIFIED BY test_password;
    - GRANT CONNECT, DBA TO test_user;

4. Se connecter avec le nouvel utilisateur à datagrip :
    - Type de connection : Service Name
    - host: localhost ou 0.0.0.0
    - port: 1521
    - user: test_user
    - password: test_password
    - sid: XE