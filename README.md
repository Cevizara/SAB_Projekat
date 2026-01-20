# Sistem za Preporuku Filmova (SAB Projekat 2024/2025)

Projekat iz predmeta **Softverski alati baza podataka** na Elektrotehničkom fakultetu u Beogradu (školska godina 2024/2025).

Aplikacija predstavlja backend sistem za upravljanje bazom filmova, korisnika i ocena, sa naprednim funkcionalnostima kao što su pametne preporuke, blokiranje zlonamernih ocena i automatsko nagrađivanje korisnika.

## 📋 Opis Sistema

Sistem omogućava vođenje evidencije o filmovima, žanrovima, tagovima i korisnicima, uz kompleksnu poslovnu logiku implementiranu kroz **Java** backend i **MS SQL** bazu podataka.

### Ključne Funkcionalnosti

* **Upravljanje entitetima:** Evidencija filmova (naslov, režiser), žanrova, tagova i korisnika.
* **Sistem ocenjivanja:** Korisnici ocenjuju filmove (1-10).
* **Blokiranje ekstremnih ocena (Trigger):** Implementirana zaštita od manipulacije ocenama. Korisnik ne može davati ekstremne ocene (1 ili 10) u određenom žanru ako već ima previše takvih ocena a premalo neutralnih.
* **Sistem preporuke:** Algoritam preporučuje filmove na osnovu "omiljenih žanrova" korisnika (prosek ocena >= 8), uključujući "skrivene bisere" (filmovi sa malo ocena ali visokim prosekom).
* **Nagrađivanje korisnika (Stored Procedure):** Automatska dodela nagrada korisnicima koji ocenjuju slabije popularne filmove unutar svojih omiljenih žanrova.
* **Profilisanje korisnika:** Automatska kategorizacija korisnika ("Radoznao", "Fokusiran", "Nedefinisan") i određivanje tematskih specijalizacija na osnovu tagova.

## 🛠 Tehnologije

* **Jezik:** Java
* **Baza podataka:** MS SQL Server
* **Pristup podacima:** JDBC
* **Alati:** SQL Triggers, Stored Procedures

