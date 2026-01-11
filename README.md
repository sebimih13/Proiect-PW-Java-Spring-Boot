# Proiect-PW-Java-Spring-Boot

## Project scoring condition:
- No compilation errors present
- Implement the given requirements
- Clean code & Readability - try to follow DRY principle (DRY = Do not repeat yourself)
- The code follows JAVA coding styles and conventions.
- All tests passed

## I. Define a system of your choice.
1. Define 10 business requirements for the chosen business domain
2. Prepare a document based on the 10 business requirements containing a description of 5 main features your project should contain for the MVP (minimum viable product) phase.
3. Create a repository for your project and commit the document for review.



### 1. 10 Business requirements:

**Domeniul de business:** Sistem de management pentru restaurante

1. **Gestionarea utilizatorilor si rolurilor**: Sistemul trebuie sa permita crearea si autentificarea diferitelor tipuri de utilizatori cu roluri si permisiuni distincte: 
    - admin
    - clienti
    - angajati => bucatari + barmani + manageri

2. **Managementul restaurantelor**: Administratorii trebuie sa poata adauga si gestiona restaurante in sistem, inclusiv detalii precum numele, locatia, numarul de stele si informatii de contact.

3. **Gestionarea angajatilor**: Managerii trebuie sa poata adauga, sterge si atribui angajati la restaurantele lor, precum si sa modifice salariile acestora.

4. **Managementul produselor**: Bucatarii trebuie sa poata adauga, edita si sterge preparate culinare, iar barmanii trebuie sa poata gestiona bauturile disponibile in restaurantele lor.

5. **Sistem de comenzi**: Clientii trebuie sa poata crea comenzi pentru preparate si bauturi din meniurile restaurantelor, sa le editeze inainte de procesare si sa le anuleze daca este necesar.

6. **Procesarea comenzilor**: Managerii trebuie sa poata vizualiza toate comenzile restaurantului lor si sa le marcheze ca finalizate odata ce sunt procesate.

7. **Vizualizarea meniurilor**: Utilizatorii trebuie sa poata consulta meniurile de preparate si bauturi ale fiecarui restaurant fara a fi necesara autentificarea.

8. **Securitate si autorizare**: Sistemul trebuie sa implementeze mecanisme JWT pentru autentificare si sa asigure ca fiecare utilizator poate accesa doar functionalitatile corespunzatoare rolului sau.

9. **Validarea datelor**: Toate datele introduse in sistem (informatii utilizatori, produse, comenzi) trebuie validate pentru a asigura integritatea si consistenta bazei de date.

10. **Documentarea API-ului**: Toate endpoint-urile REST trebuie documentate folosind Swagger/OpenAPI pentru a facilita integrarea si utilizarea sistemului de catre dezvoltatori si clienti.



### 2. 5 Functionalitati principale pentru faza MVP (Minimum Viable Product)

#### Feature 1: Autentificare si Gestionare Utilizatori
**Descriere:** Sistemul ofera un mecanism complet de inregistrare si autentificare pentru diferite tipuri de utilizatori (clienti, angajati, administratori). Utilizatorii se pot inregistra cu informatii personale si credentiale, apoi se pot autentifica pentru a primi un token JWT care le permite accesul la functionalitatile specifice rolului lor.

**Motivatie business:** Aceasta functionalitate este fundamentala pentru orice sistem modern, permitand identificarea unica a utilizatorilor si controlul accesului bazat pe roluri. Fara un sistem robust de autentificare, nu se pot implementa celelalte functionalitati in siguranta.


#### Feature 2: Managementul Restaurantelor si atribuirea managerilor
**Descriere:** Administratorii pot adauga restaurante noi in sistem cu toate detaliile necesare. Dupa adaugarea unui restaurant, administratorii pot atribui manageri care vor avea responsabilitatea gestionarii operatiunilor zilnice ale restaurantului respectiv.

**Motivatie business:** Restaurantele sunt entitatea centrala a sistemului. Aceasta functionalitate permite scalabilitatea sistemului, permitand gestionarea mai multor restaurante intr-o singura platforma, fiecare cu propria echipa de management.


#### Feature 3: Gestionarea meniurilor de produse din fiecare restaurant
**Descriere:** Bucatarii pot adauga, edita si sterge preparate culinare, iar barmanii pot gestiona bauturile. Fiecare produs are detalii precum nume, pret, descriere si categoria. Produsele sunt asociate cu restaurantul in care lucreaza angajatul care le-a creat. Meniurile pot fi vizualizate public de catre clienti.

**Motivatie business:** Meniurile reprezinta oferta comerciala a restaurantelor. Aceasta functionalitate permite personalIzarea si actualizarea dinamica a ofertei.


#### Feature 4: Sistem de comenzi pentru clienti
**Descriere:** Clientii autentificati pot crea comenzi selectand produse (preparate si bauturi) din meniul unui restaurant. Fiecare comanda contine informatii despre statusul acesteia, produsele selectate, cantitati, data si ora comenzii. Clientii pot edita comenzile in curs (adauga/sterge produse) sau le pot anula complet inainte ca acestea sa fie procesate.

**Motivatie business:** Aceasta este functionalitatea principala orientata catre clienti, reprezentand fluxul de venituri al restaurantelor. 


#### Feature 5: Managementul echipei si procesarea comenzilor
**Descriere:** Managerii pot gestiona echipa de angajati a restaurantului lor (atribuire angajati, modificare salarii, eliminare din echipa), pot vizualiza si procesa toate comenzile restaurantului. 

**Motivatie business:** Aceasta functionalitate ofera managerilor instrumentele necesare pentru coordonarea eficienta a operatiunilor restaurantului, de la gestionarea resurselor umane la procesarea comenzilor.



## II. The project should consist of a Spring Boot Application containing:
1. REST endpoints for all the features defined for the MVP. You should define at least 5 endpoints.
2. Beans for defining services (implementing business logic). One service per feature.
3. Beans for defining repositories. One repository per entity.
4. Write unit tests for all REST endpoints and services and make sure all passed.
5. The data within the application should be persisted in a database. Define at least 6 entities that will be persisted in the database database, and at least 4 relations between them.
6. You should validate the POJO classes. You can use the existing validation constraints or create your own annotations if you need a custom constraint.
7. Document the functionalities in the application such that anyone can use it after reading the document. Every API will be documented by Swagger. You can also export the visual documentation and add it to your main document presentation.
8. The functionality of the application will be demonstrated using Postman or GUI (Thymeleaf, Angular, etc).
