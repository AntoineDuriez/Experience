--1 La Contamination
WITH sympt AS(	--Nous décidons d'étudier les symptomes d'un patient donné, ici Adrien
      SELECT * from ressent WHERE nompatient='Adrien'
), possMaladies AS(	--Nous récupérons toutes maladies associées aux symptomes d'Adrien
      SELECT * FROM (sympt natural join estAssocieA)
), diag AS(	--Nous déterminons quelle maladies touchent le patient
      SELECT nomMaladie,nomPatient FROM possMaladies GROUP BY nomMaladie,nomPatient ORDER BY COUNT(nomMaladie) DESC LIMIT 1
)
SELECT * FROM diag;

--2 La Consultation : Nous étudions toujours Adrien
SELECT * FROM ressent WHERE nomPatient='Adrien'; --Nous récupérons tous les symptomes ressentis par Adrien

--3 La Prescription : Nous étudions toujours Adrien
WITH sympt AS(	--Nous récupérons les symptomes ressentis par Adrien
      SELECT * from ressent WHERE nompatient='Adrien'
), possMaladies AS(	--Nous récupérons toutes les maladies associées aux symptomes d'Adrien
      SELECT * FROM (sympt natural join estAssocieA)
), diag AS(	--Nous posons le diagnostic, comme pour 1 La Contamination
      SELECT nomMaladie,nomPatient FROM possMaladies GROUP BY nomMaladie,nomPatient ORDER BY COUNT(nomMaladie) DESC LIMIT 1
), traite AS( --Nous récupérons le traitement afin de guérir la maladie d'Adrien
      SELECT nomPatient,nomMaladie,nomtraitement FROM diag natural join soigne
)
SELECT * FROM traite;

--4 La Prévention : Nous allons effectuer la prévention avec l'exemple du traitement pour la grippe
WITH traitgrippe AS(	--Nous récupérons les traitements servant à soigner la grippe
     SELECT DISTINCT nomTraitement from diagnostic natural join soigne WHERE nomMaladie = 'Grippe'
     ),troisan AS(	--Nous récupérons les noms des patients traités pour la grippe il y a moins de 3 ans
     SELECT nomPatient FROM diagnostic natural join traitgrippe WHERE datation >= '01-01-2017'
     ), prev AS(	--Les noms des patients traités pour la grippe il y a moins de 3 ans ayant plus de 60 ans
     SELECT nom FROM patient join troisan on troisan.nomPatient = patient.nom WHERE age>=60
     )
SELECT * FROM prev;

--5 L'éthique (1)
WITH diag AS(--Nous récupérons les nom des médecins et des traitements ayant servis dans les diagnostics
     SELECT nomMedecin,nomTraitement FROM diagnostic 
),prescri AS(	--Nous ne gardons que les noms de médecins et de traitement ayant fait des prescriptions de médicaments
     SELECT nomMedecin,nomMedicament FROM diag natural join prescription
)--Nous comparons notre résulat avec les éléments de la table estDeveloppePar
SELECT * FROM  estDeveloppePar natural join prescri;

--6 L'étique (2)
WITH diag AS(
     SELECT nomMedecin,nomTraitement FROM diagnostic 
),prescri AS(	--Nous récupérons les mêmes informations que pour la question 5
     SELECT nomMedecin,nomMedicament FROM diag natural join prescription
),jointure AS(	--Nous comparons notre résultat avec les éléments de la table estFabriquePar
	SELECT * FROM prescri natural join estFabriquePar
)
SELECT jointure.nomLabo as fabricant, medecin.nomLabo as lieuTravail from jointure join medecin on jointure.nomMedecin=medecin.nom; 

--8 Combinaison
--Plusieur medicament (sans count ni group by)
WITH pat AS(
     SELECT nomPatient,nomMedicament FROM diagnostic natural join prescription
),t1 AS(
     SELECT * FROM pat
),t2 AS(
     SELECT * FROM pat
)/*
    SELECT t2.nomPatient FROM t1 join t2 on t1.nomPatient = t2.nomPatient WHERE t1.nomMedicament != t2.nomMedicament;
*/

SELECT * FROM t1,t2 where t1.nomPatient=t2.nomPatient AND t1.nomMedicament != t2.nomMedicament;


--9 Diagnostique simple
WITH sympt AS(
      SELECT * from ressent WHERE nompatient='Adrien'
), possMaladies AS(
      SELECT * FROM (sympt natural join estAssocieA)
), diag AS(
      SELECT * FROM possMaladies GROUP BY nomMaladie,nomPatient,descriptionSymptome ORDER BY COUNT(descriptionSymptome)
),diff AS(
      SELECT possMaladies.nomMaladie FROM possMaladies join estAssocieA on possMaladies.nomMaladie = estAssocieA.nomMaladie WHERE possMaladies.descriptionSymptome != estAssocieA.descriptionSymptome
)
SELECT nomMaladie FROM diag EXCEPT SELECT * FROM diff;

--10 Symptome_Sous_Traitement : notre patient exemple est Adrien
WITH sympt AS(	--Nous récupérons les symptomes ressentis par Adrien
     SELECT * FROM ressent WHERE nomPatient = 'Adrien' 
),sec AS(	--Nous gardons seulement les symptomes qui sont des effets secondaires
     SELECT nomPatient,description FROM sympt join effetSecondaire on sympt.descriptionSymptome = effetSecondaire.description
)	--Nous supprimons les effets secondaires pour ne garder que les symptomes
SELECT * FROM sympt EXCEPT SELECT * FROM sec;

--12 Coût traitement : notre traitement exemple est le Traitement contre le corona
WITH med AS(	--nous récupérons les noms de traitements et les prix donnant lieu à des prescriptions
     SELECT nomTraitement,prix FROM prescription join medicament on medicament.nom = prescription.nomMedicament
), reco AS(	--Même récupération avec les recommandations
     SELECT nomTraitement,prix AS prixReco FROM recommandation
), orient AS(	--Même récupération avec les orientations
     SELECT nomTraitement,prix AS prixOrient  FROM orientation
),uni AS(	--Nous unissons les 3 tables précédents pour n'en avoir qu'une
     SELECT * FROM med union SELECT * FROM orient union SELECT * FROM reco
),res AS(	--Nous filtrons pour avoir uniquement les cas de Traitement contre le corona
     SELECT * FROM uni WHERE nomTraitement = 'Traitement contre le corona'
)	--Nous faisons la somme des prix qui restent pour avoir le coût du traitement
     SELECT SUM(prix) FROM res;
--15
--Requête qui renvoie les médicaments communs de tous les traitements qu'un patient donné a eu
WITH trait AS(
     SELECT nomTraitement FROM diagnostic WHERE nomPatient = 'Brenda'
), medic AS(
     SELECT nomTraitement,nomMedicament FROM trait natural join prescription 
), duo AS(
     SELECT * FROM medic
), res AS(
     SELECT medic.nomTraitement,medic.nomMedicament FROM medic,duo WHERE medic.nomMedicament = duo.nomMedicament AND medic.nomTraitement != duo.nomTraitement
)
     SELECT * FROM res;


--Requête qui renvoie tous les medicaments contre des maladies pulmonaires prescrit par un medecin ,sur une année
WITH diag AS(
     SELECT nomTraitement,nomMedecin FROM diagnostic WHERE nomMedecin = 'Toto' AND datation <='2019-01-01' AND datation > '2017-12-31'
), medic AS(
     SELECT nomTraitement,nomMedicament FROM diag natural join prescription 
), mala AS(
     SELECT nomTraitement,nomMedicament,nomMaladie FROM medic natural join soigne
), typ AS(
     SELECT nomTraitement,nomMedicament,nomMaladie,typeMaladie FROM mala join maladie on mala.nomMaladie = maladie.nom
)
SELECT * FROM typ WHERE typeMaladie = 'pulmonaire';
