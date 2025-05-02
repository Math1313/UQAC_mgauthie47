-- Créer l'utilisateur DBA
CREATE USER TEST_USER IDENTIFIED BY "user";

-- Assigner les permissions d'administration au DBA
GRANT ALL PRIVILEGES TO TEST_USER;

-- Créer l'utilisateur read-only
CREATE USER user_readOnly IDENTIFIED BY "user_readOnly";

-- Créer le rôle read-only
CREATE ROLE read_only_role;

-- Attribuer les permissions de lecture au role
GRANT SELECT ON TEST_USER.COURS TO read_only_role;
GRANT SELECT ON TEST_USER.ENSEIGNANT TO read_only_role;
GRANT CONNECT TO read_only_role;

-- Attribuer le role au nouvel utilisateur
GRANT read_only_role TO user_readOnly;