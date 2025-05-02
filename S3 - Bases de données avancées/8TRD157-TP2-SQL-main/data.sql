/*==========================Tache 3 : Insertion des donnees===========================*/

/*Créer départements*/
BEGIN
    AJOUTER_DEPARTEMENT_SANS_DIRECTEUR('Informatique', 1);
	AJOUTER_DEPARTEMENT_SANS_DIRECTEUR('Biologie', 2);
END;
/

/*Optionnellement on peut créer des locaux pour nos enseignants.*/
INSERT INTO LOCAL (CODE, DESCRIPTION)
VALUES ('A1-1001', 'Local A1');

INSERT INTO LOCAL (CODE, DESCRIPTION)
VALUES ('B2-2002', 'Local B2');

INSERT INTO SESSION_ (SESSION_)
VALUES (20243);

INSERT INTO SESSION_ (SESSION_)
VALUES (20251);

INSERT INTO SESSION_ (SESSION_)
VALUES (20252);

INSERT INTO SESSION_ (SESSION_)
VALUES (20253);

BEGIN
    AJOUTER_ENSEIGNANT(new_nom => 'Dupont',
                       new_prenom => 'Jean',
                       new_nas => '123456789',
                       new_date_naissance => TO_DATE('1980-05-12', 'YYYY-MM-DD'),
                       new_courriel_personnel => 'jean.dupont@example.com',
                       new_numero_telephone => '12345678901',
                       new_numero_ligne_direct => '12345678901',
                       new_poste_telephone => '1234',
                       new_id_departement => 2,
                       new_code_local => 'A1-1001',
                       new_code_status_enseignant => 2,
                       new_date_embauche => '2024-08-09');

    AJOUTER_ENSEIGNANT(new_nom => 'Martin',
                       new_prenom => 'Alice',
                       new_nas => '987654321',
                       new_date_naissance => TO_DATE('1985-11-24', 'YYYY-MM-DD'),
                       new_courriel_personnel => 'alice.martin@example.com',
                       new_numero_telephone => '10987654321',
                       new_numero_ligne_direct => '10987654321',
                       new_poste_telephone => '5678',
                       new_id_departement => 2,
                       new_code_local => 'A1-1001',
                       new_code_status_enseignant => 1,
                       new_date_embauche => '2024-04-11');

    AJOUTER_ENSEIGNANT(new_nom => 'Durand',
                       new_prenom => 'Paul',
                       new_nas => '654987321',
                       new_date_naissance => TO_DATE('1990-02-18', 'YYYY-MM-DD'),
                       new_courriel_personnel => 'paul.durand@example.com',
                       new_numero_telephone => '65498732101',
                       new_numero_ligne_direct => '65498732101',
                       new_poste_telephone => '9012',
                       new_id_departement => 1,
                       new_code_local => 'B2-2002',
                       new_code_status_enseignant => 2,
                       new_date_embauche => '2021-12-09');

    AJOUTER_ENSEIGNANT(new_nom => 'Moreau',
                       new_prenom => 'Sophie',
                       new_nas => '321654987',
                       new_date_naissance => TO_DATE('1992-07-10', 'YYYY-MM-DD'),
                       new_courriel_personnel => 'sophie.moreau@example.com',
                       new_numero_telephone => '32165498701',
                       new_numero_ligne_direct => '32165498701',
                       new_poste_telephone => '3456',
                       new_id_departement => 1,
                       new_code_local => 'B2-2002',
                       new_code_status_enseignant => 3,
                       new_date_embauche => '2022-02-25');

    AJOUTER_ENSEIGNANT(new_nom => 'Gagnon',
                       new_prenom => 'Phil',
                       new_nas => '461235666',
                       new_date_naissance => TO_DATE('1990-05-15', 'YYYY-MM-DD'),
                       new_courriel_personnel => 'bonjour@gmail.com',
                       new_numero_telephone => '4185555555',
                       new_numero_ligne_direct => '4186666666',
                       new_poste_telephone => '4321',
                       new_id_departement => 1,
                       new_code_local =>'A1-1001',
                       new_code_status_enseignant => 1,
                       new_date_embauche => '2024-10-11');
END;
/

/*Assigner un professeur en tant que directeur d'un département*/
BEGIN
    ASSIGNER_DIRECTEUR_DEPARTEMENT(
		p_id_departement => 1,
		p_id_directeur => 3
	);
	ASSIGNER_DIRECTEUR_DEPARTEMENT(
		p_id_departement => 2,
		p_id_directeur => 1
	);
END;
/

/*Ajouter des cours.*/
BEGIN
    -- Programmation 1
    AJOUTER_COURS(p_id_enseignant => 3, 
                  p_id_departement => 1, 
                  p_titre => 'Programmation 1', 
                  p_sigle => '2INF101', 
                  p_description => 'Introduction à la programmation', 
                  p_lien_site_web => 'http://prog1.example.com', 
                  p_credit => 3, 
                  p_nombre_heure_cours => 45, 
                  p_nombre_heure_laboratoire => 15, 
                  p_nombre_heure_travail => 30);
    
    -- Programmation 2
    AJOUTER_COURS(p_id_enseignant => 4, 
                  p_id_departement => 1, 
                  p_titre => 'Programmation 2', 
                  p_sigle => '2INF102', 
                  p_description => 'Suite de Programmation 1', 
                  p_lien_site_web => 'http://prog2.example.com', 
                  p_credit => 3, 
                  p_nombre_heure_cours => 45, 
                  p_nombre_heure_laboratoire => 15, 
                  p_nombre_heure_travail => 30);
    
    -- Structures de données
    AJOUTER_COURS(p_id_enseignant => NULL, 
                  p_id_departement => 1, 
                  p_titre => 'Structures de données', 
                  p_sigle => '2INF201', 
                  p_description => 'Gestion des structures', 
                  p_lien_site_web => 'http://sd.example.com', 
                  p_credit => 4, 
                  p_nombre_heure_cours => 60, 
                  p_nombre_heure_laboratoire => 15, 
                  p_nombre_heure_travail => 45);
    
    -- Bases de données
    AJOUTER_COURS(p_id_enseignant => NULL, 
                  p_id_departement => 1, 
                  p_titre => 'Bases de données', 
                  p_sigle => '2INF202', 
                  p_description => 'Introduction aux bases de données', 
                  p_lien_site_web => 'http://bd.example.com', 
                  p_credit => 3, 
                  p_nombre_heure_cours => 45, 
                  p_nombre_heure_laboratoire => 15, 
                  p_nombre_heure_travail => 30);
    
    -- Systèmes d'exploitation
    AJOUTER_COURS(p_id_enseignant => NULL, 
                  p_id_departement => 1, 
                  p_titre => 'Systèmes d exploitation', 
                  p_sigle => '2INF301', 
                  p_description => 'Concepts des OS', 
                  p_lien_site_web => 'http://so.example.com', 
                  p_credit => 3, 
                  p_nombre_heure_cours => 45, 
                  p_nombre_heure_laboratoire => 15, 
                  p_nombre_heure_travail => 30);
    
    -- Biologie 1
    AJOUTER_COURS(p_id_enseignant => NULL, 
                  p_id_departement => 2, 
                  p_titre => 'Biologie 1', 
                  p_sigle => '1BIO101', 
                  p_description => 'Introduction à la biologie', 
                  p_lien_site_web => 'http://bio1.example.com', 
                  p_credit => 3, 
                  p_nombre_heure_cours => 45, 
                  p_nombre_heure_laboratoire => 15, 
                  p_nombre_heure_travail => 30);
    
    -- Biologie 2
    AJOUTER_COURS(p_id_enseignant => NULL, 
                  p_id_departement => 2, 
                  p_titre => 'Biologie 2', 
                  p_sigle => '1BIO102', 
                  p_description => 'Suite de Biologie 1', 
                  p_lien_site_web => 'http://bio2.example.com', 
                  p_credit => 3, 
                  p_nombre_heure_cours => 45, 
                  p_nombre_heure_laboratoire => 15, 
                  p_nombre_heure_travail => 30);
    
    -- Génétique
    AJOUTER_COURS(p_id_enseignant => NULL, 
                  p_id_departement => 2, 
                  p_titre => 'Génétique', 
                  p_sigle => '1BIO201', 
                  p_description => 'Introduction à la génétique', 
                  p_lien_site_web => 'http://genetique.example.com', 
                  p_credit => 4, 
                  p_nombre_heure_cours => 60, 
                  p_nombre_heure_laboratoire => 15, 
                  p_nombre_heure_travail => 45);
    
    -- Écologie
    AJOUTER_COURS(p_id_enseignant => 2, 
                  p_id_departement => 2, 
                  p_titre => 'Écologie', 
                  p_sigle => '1BIO202', 
                  p_description => 'Concepts écologiques', 
                  p_lien_site_web => 'http://ecologie.example.com', 
                  p_credit => 3, 
                  p_nombre_heure_cours => 45, 
                  p_nombre_heure_laboratoire => 15, 
                  p_nombre_heure_travail => 30);
    
    -- Microbiologie
    AJOUTER_COURS(p_id_enseignant => 1, 
                  p_id_departement => 2, 
                  p_titre => 'Microbiologie', 
                  p_sigle => '1BIO301', 
                  p_description => 'Étude des micro-organismes', 
                  p_lien_site_web => 'http://microbio.example.com', 
                  p_credit => 3, 
                  p_nombre_heure_cours => 45, 
                  p_nombre_heure_laboratoire => 15, 
                  p_nombre_heure_travail => 30);
END;
/

/*Ajouter des préalables*/
BEGIN
    -- Ajouter les préalables
    AJOUTER_PREALABLE(p_id_cours => 2, p_id_cours_prealable => 1); -- Programmation 2 a pour préalable Programmation 1
    AJOUTER_PREALABLE(p_id_cours => 3, p_id_cours_prealable => 2); -- Structures de données a pour préalable Programmation 2
    AJOUTER_PREALABLE(p_id_cours => 4, p_id_cours_prealable => 3); -- Bases de données a pour préalable Structures de données
    AJOUTER_PREALABLE(p_id_cours => 5, p_id_cours_prealable => 2); -- Systèmes d'exploitation a pour préalable Programmation 2
    AJOUTER_PREALABLE(p_id_cours => 8, p_id_cours_prealable => 10); -- Microbiologie a pour préalable Génétique
END;
/

BEGIN
    AJOUTER_COURS_ENSEIGNE(p_id_enseignant => 3,   -- ID de l'enseignant
                           p_id_session    => 1, -- ID de la session
                           p_id_cours      => 1,  -- ID du cours
                           p_code_local    => 'A1-1001'); -- Code du local

    AJOUTER_COURS_ENSEIGNE(p_id_enseignant => 3,   -- ID de l'enseignant
                           p_id_session    => 1, -- ID de la session
                           p_id_cours      => 2,  -- ID du cours
                           p_code_local    => 'A1-1001'); -- Code du local
						   
    AJOUTER_COURS_ENSEIGNE(p_id_enseignant => 1,   -- ID de l'enseignant
                           p_id_session    => 1, -- ID de la session
                           p_id_cours      => 9,  -- ID du cours
                           p_code_local    => 'A1-1001'); -- Code du local
	
	AJOUTER_COURS_ENSEIGNE(p_id_enseignant => 2,   -- ID de l'enseignant
                           p_id_session    => 1, -- ID de la session
                           p_id_cours      => 10,  -- ID du cours
                           p_code_local    => 'A1-1001'); -- Code du local
END;
/

COMMIT;

/*Tester ajouter étudiant*/

BEGIN
    AJOUTER_ETUDIANT(new_nom => 'Tremblay', 
					 new_prenom => 'Jean', 
					 new_nas => '123456788', 
					 new_date_naissance => TO_DATE('1990-05-15', 'YYYY-MM-DD'), 
					 new_courriel_personnel => 'salut@gmail.com', 
					 new_numero_telephone => '4184444444',
					 new_id_departement => 1, 
					 new_status_etudiant => 'A');
    AJOUTER_ETUDIANT(new_nom => 'Plourde', 
					 new_prenom => 'Paul', 
					 new_nas => '667483921', 
					 new_date_naissance => TO_DATE('1990-05-15', 'YYYY-MM-DD'), 
					 new_courriel_personnel => 'courrielXD@gmail.com', 
					 new_numero_telephone => '4184444432',
					 new_id_departement => 2, 
					 new_status_etudiant => 'A');
end;
/

BEGIN
    -- Inscrire Jean Tremblay à Programmation 1 avec une note fictive de 85
    INSCRIRE_ETUDIANT_COURS_ENSEIGNE(
        p_id_etudiant => 1,        -- ID de Jean Tremblay
        p_id_cours_enseigne => 1,  -- ID du cours Programmation 1
        p_code_statut => 'A',      -- Statut A (ou tout autre statut valide)
        p_note_totale => 85        -- Note fictive
    );

    -- Inscrire Paul Plourde à Biologie 1 avec une note fictive de 78
    INSCRIRE_ETUDIANT_COURS_ENSEIGNE(
        p_id_etudiant => 1,        -- ID de Paul Plourde
        p_id_cours_enseigne => 2,  -- ID du cours Biologie 1
        p_code_statut => 'B',      -- Statut B (ou tout autre statut valide)
        p_note_totale => 78        -- Note fictive
    );
    
    -- Inscrire Jean Tremblay à Systèmes d'exploitation avec une note fictive de 90
    INSCRIRE_ETUDIANT_COURS_ENSEIGNE(
        p_id_etudiant => 2,        -- ID de Jean Tremblay
        p_id_cours_enseigne => 3,  -- ID du cours Systèmes d'exploitation
        p_code_statut => 'A+',     -- Statut A+
        p_note_totale => 90        -- Note fictive
    );
    
    -- Inscrire Paul Plourde à Génétique avec une note fictive de 70
    INSCRIRE_ETUDIANT_COURS_ENSEIGNE(
        p_id_etudiant => 2,        -- ID de Paul Plourde
        p_id_cours_enseigne => 4,  -- ID du cours Génétique
        p_code_statut => 'C+',     -- Statut C+
        p_note_totale => 70        -- Note fictive
    );

    COMMIT;
END;
/


/*Tester ajouter une adresse*/
BEGIN
    AJOUTER_ADRESSE_PERSONNE(1, 'Maison', '567 du soleil', 'Alma', 'G8E 2A3', 'Canada');
END;
/

COMMIT;