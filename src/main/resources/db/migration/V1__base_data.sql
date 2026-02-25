-- src/main/resources/db/migration/V1__base_data.sql

BEGIN TRANSACTION;

-- Table marque
INSERT OR IGNORE INTO marque (marque) VALUES ('DeLonghi');
INSERT OR IGNORE INTO marque (marque) VALUES ('Philips');
INSERT OR IGNORE INTO marque (marque) VALUES ('Saeco');
INSERT OR IGNORE INTO marque (marque) VALUES ('Krups');

-- Table machineacafe
INSERT OR IGNORE INTO machineacafe (description, nom_commercial, reference_commerciale, marque_id)
VALUES ('Machine expresso automatique avec broyeur', 'Magnifica S', 'ECAM22.110', 1);

INSERT OR IGNORE INTO machineacafe (description, nom_commercial, reference_commerciale, marque_id)
VALUES ('Machine expresso automatique compacte', 'Series 2200', 'EP2220', 2);

INSERT OR IGNORE INTO machineacafe (description, nom_commercial, reference_commerciale, marque_id)
VALUES ('Machine expresso professionnelle', 'Royal OTC', 'SUP016REU', 3);

INSERT OR IGNORE INTO machineacafe (description, nom_commercial, reference_commerciale, marque_id)
VALUES ('Machine expresso domestique', 'Essential', 'XP3208', 4);

-- Table cafe
INSERT OR IGNORE INTO cafe (description, label_cafe, nom_cafe, type_cafe)
VALUES ('Café doux et aromatique d’Amérique du Sud', 'BIO', 'Colombia Supremo', 'ARABICA');

INSERT OR IGNORE INTO cafe (description, label_cafe, nom_cafe, type_cafe)
VALUES ('Café corsé et puissant', 'FAIR_TRADE', 'Robusta Vietnam', 'ROBUSTA');

INSERT OR IGNORE INTO cafe (description, label_cafe, nom_cafe, type_cafe)
VALUES ('Assemblage équilibré pour expresso', 'BIO_FAIR_TRADE', 'Espresso Italiano', 'BLEND');

INSERT OR IGNORE INTO cafe (description, label_cafe, nom_cafe, type_cafe)
VALUES ('Café intense aux notes boisées', 'RAINFOREST_ALLIANCE', 'Amazonia Forte', 'ARABICA');

-- Table commercant
INSERT OR IGNORE INTO commercant (type_commercant, adresse, email, nom, site_internet, telephone, annee_creation, nom_du_groupe_de_distribution)
VALUES ('Commercant', '12 rue du Café, Paris', 'contact@cafes-paris.fr', 'Cafés de Paris', 'https://www.cafes-paris.fr', '0102030405', NULL, NULL);

INSERT OR IGNORE INTO commercant (type_commercant, adresse, email, nom, site_internet, telephone, annee_creation, nom_du_groupe_de_distribution)
VALUES ('Artisan', '5 place du Marché, Lyon', 'artisan@lyon-cafe.fr', 'Lyon Torréfaction', 'https://www.lyon-cafe.fr', '0405060708', '1998', NULL);

INSERT OR IGNORE INTO commercant (type_commercant, adresse, email, nom, site_internet, telephone, annee_creation, nom_du_groupe_de_distribution)
VALUES ('Distributeur', 'Zone industrielle, Lille', 'contact@distrib-cafe.fr', 'Distrib Café Nord', 'https://www.distrib-cafe.fr', '0304050607', NULL, 'Groupe Café Europe');

INSERT OR IGNORE INTO commercant (type_commercant, adresse, email, nom, site_internet, telephone, annee_creation, nom_du_groupe_de_distribution)
VALUES ('Artisan', '8 avenue des Arômes, Bordeaux', 'bonjour@bordeaux-cafe.fr', 'Bordeaux Arômes', 'https://www.bordeaux-cafe.fr', '0506070809', '2005', NULL);

-- Table consommation
INSERT OR IGNORE INTO consommation (reglage_broyeur, reglage_intensite, cafe_id, machine_a_cafe_id)
VALUES (3, 4, 1, 1);

INSERT OR IGNORE INTO consommation (reglage_broyeur, reglage_intensite, cafe_id, machine_a_cafe_id)
VALUES (2, 5, 2, 2);

INSERT OR IGNORE INTO consommation (reglage_broyeur, reglage_intensite, cafe_id, machine_a_cafe_id)
VALUES (4, 3, 3, 3);

INSERT OR IGNORE INTO consommation (reglage_broyeur, reglage_intensite, cafe_id, machine_a_cafe_id)
VALUES (5, 5, 4, 4);

COMMIT;