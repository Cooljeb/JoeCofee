BEGIN TRANSACTION;

-- Table marque
INSERT INTO marque (marque)
SELECT 'DeLonghi' WHERE NOT EXISTS(SELECT 1 FROM marque WHERE marque='DeLonghi');
INSERT INTO marque (marque)
SELECT 'Philips' WHERE NOT EXISTS(SELECT 1 FROM marque WHERE marque='Philips');
INSERT INTO marque (marque)
SELECT 'Saeco' WHERE NOT EXISTS(SELECT 1 FROM marque WHERE marque='Saeco');
INSERT INTO marque (marque)
SELECT 'Krups' WHERE NOT EXISTS(SELECT 1 FROM marque WHERE marque='Krups');

-- Table machineacafe
INSERT INTO machineacafe (description, nom_commercial, reference_commerciale, marque_id)
SELECT 'Machine expresso automatique avec broyeur', 'Magnifica S', 'ECAM22.110', 1
WHERE NOT EXISTS(SELECT 1 FROM machineacafe WHERE nom_commercial='Magnifica S');

INSERT INTO machineacafe (description, nom_commercial, reference_commerciale, marque_id)
SELECT 'Machine expresso automatique compacte', 'Series 2200', 'EP2220', 2
WHERE NOT EXISTS(SELECT 1 FROM machineacafe WHERE nom_commercial='Series 2200');

INSERT INTO machineacafe (description, nom_commercial, reference_commerciale, marque_id)
SELECT 'Machine expresso professionnelle', 'Royal OTC', 'SUP016REU', 3
WHERE NOT EXISTS(SELECT 1 FROM machineacafe WHERE nom_commercial='Royal OTC');

INSERT INTO machineacafe (description, nom_commercial, reference_commerciale, marque_id)
SELECT 'Machine expresso domestique', 'Essential', 'XP3208', 4
WHERE NOT EXISTS(SELECT 1 FROM machineacafe WHERE nom_commercial='Essential');

-- Table cafe
INSERT INTO cafe (description, label_cafe, nom_cafe, type_cafe)
SELECT 'Café doux et aromatique d’Amérique du Sud', 'BIO', 'Colombia Supremo', 'ARABICA'
WHERE NOT EXISTS(SELECT 1 FROM cafe WHERE nom_cafe='Colombia Supremo');

INSERT INTO cafe (description, label_cafe, nom_cafe, type_cafe)
SELECT 'Café corsé et puissant', 'FAIR_TRADE', 'Robusta Vietnam', 'ROBUSTA'
WHERE NOT EXISTS(SELECT 1 FROM cafe WHERE nom_cafe='Robusta Vietnam');

INSERT INTO cafe (description, label_cafe, nom_cafe, type_cafe)
SELECT 'Assemblage équilibré pour expresso', 'BIO_FAIR_TRADE', 'Espresso Italiano', 'BLEND'
WHERE NOT EXISTS(SELECT 1 FROM cafe WHERE nom_cafe='Espresso Italiano');

INSERT INTO cafe (description, label_cafe, nom_cafe, type_cafe)
SELECT 'Café intense aux notes boisées', 'RAINFOREST_ALLIANCE', 'Amazonia Forte', 'ARABICA'
WHERE NOT EXISTS(SELECT 1 FROM cafe WHERE nom_cafe='Amazonia Forte');

-- Table commercant
INSERT INTO commercant (type_commercant, adresse, email, nom, site_internet, telephone, annee_creation, nom_du_groupe_de_distribution)
SELECT 'Commercant', '12 rue du Café, Paris', 'contact@cafes-paris.fr', 'Cafés de Paris', 'https://cafes-paris.fr', '0102030405', NULL, NULL
WHERE NOT EXISTS(SELECT 1 FROM commercant WHERE nom='Cafés de Paris');

INSERT INTO commercant (type_commercant, adresse, email, nom, site_internet, telephone, annee_creation, nom_du_groupe_de_distribution)
SELECT 'Artisan', '5 place du Marché, Lyon', 'artisan@lyon-cafe.fr', 'Lyon Torréfaction', 'https://lyon-cafe.fr', '0405060708', '1998', NULL
WHERE NOT EXISTS(SELECT 1 FROM commercant WHERE nom='Lyon Torréfaction');

INSERT INTO commercant (type_commercant, adresse, email, nom, site_internet, telephone, annee_creation, nom_du_groupe_de_distribution)
SELECT 'Distributeur', 'Zone industrielle, Lille', 'contact@distrib-cafe.fr', 'Distrib Café Nord', 'https://distrib-cafe.fr', '0304050607', NULL, 'Groupe Café Europe'
WHERE NOT EXISTS(SELECT 1 FROM commercant WHERE nom='Distrib Café Nord');

INSERT INTO commercant (type_commercant, adresse, email, nom, site_internet, telephone, annee_creation, nom_du_groupe_de_distribution)
SELECT 'Artisan', '8 avenue des Arômes, Bordeaux', 'bonjour@bordeaux-cafe.fr', 'Bordeaux Arômes', 'https://bordeaux-cafe.fr', '0506070809', '2005', NULL
WHERE NOT EXISTS(SELECT 1 FROM commercant WHERE nom='Bordeaux Arômes');

-- Table consommation
INSERT INTO consommation (reglage_broyeur, reglage_intensite, cafe_id, machine_a_cafe_id)
SELECT 3, 4, 1, 1
WHERE NOT EXISTS(SELECT 1 FROM consommation WHERE cafe_id=1 AND machine_a_cafe_id=1);

INSERT INTO consommation (reglage_broyeur, reglage_intensite, cafe_id, machine_a_cafe_id)
SELECT 2, 5, 2, 2
WHERE NOT EXISTS(SELECT 1 FROM consommation WHERE cafe_id=2 AND machine_a_cafe_id=2);

INSERT INTO consommation (reglage_broyeur, reglage_intensite, cafe_id, machine_a_cafe_id)
SELECT 4, 3, 3, 3
WHERE NOT EXISTS(SELECT 1 FROM consommation WHERE cafe_id=3 AND machine_a_cafe_id=3);

INSERT INTO consommation (reglage_broyeur, reglage_intensite, cafe_id, machine_a_cafe_id)
SELECT 5, 5, 4, 4
WHERE NOT EXISTS(SELECT 1 FROM consommation WHERE cafe_id=4 AND machine_a_cafe_id=4);

COMMIT;
