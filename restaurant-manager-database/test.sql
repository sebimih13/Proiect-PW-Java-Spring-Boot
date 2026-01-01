-- ------
-- TEST
-- ------

SELECT a.id_angajat, a.id_manager, a.id_restaurant, a.username, a.password, a.nume, a.prenume, a.salariu, a.nr_telefon
FROM angajat a JOIN ospatar sb ON (a.id_angajat = sb.id_angajat);

SELECT *
FROM restaurant;

UPDATE angajat
SET salariu = 4000
WHERE id_angajat = 2;

SELECT p.id_produs, p.nume, p.descriere, p.pret, pr.grame, sb.id_angajat
FROM produs p JOIN preparat pr ON (p.id_produs = pr.id_produs)
              JOIN gateste g ON (p.id_produs = g.id_produs)
              JOIN sef_bucatar sb ON (g.id_angajat = sb.id_angajat);

SELECT p.id_produs, p.nume, p.descriere, p.pret, b.ml, bar.id_angajat
FROM produs p JOIN bautura b ON (p.id_produs = b.id_produs)
              JOIN prepara pre ON (p.id_produs = pre.id_produs)
              JOIN barman bar ON (pre.id_angajat = bar.id_angajat);

SELECT c.id_comanda, c.id_client, r.id_restaurant, c.status, c.data, c.ora
FROM comanda c JOIN restaurant r ON (c.id_restaurant = r.id_restaurant)
               JOIN client cl ON (c.id_client = cl.id_client);

SELECT c.id_comanda, p.id_produs, con.cantitate
FROM comanda c JOIN contine con ON (c.id_comanda = con.id_comanda)
               JOIN produs p ON (con.id_produs = p.id_produs);

SELECT c.id_client, c.username, c.password, c.nume, c.prenume, c.nr_telefon, c.email
FROM client c;
