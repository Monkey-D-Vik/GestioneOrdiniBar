# GestioneOrdiniBar
Questo progetto è un sistema per la gestione degli ordini in un bar. Include:

- **App Cameriere**: per gestire gli ordini direttamente dai tavoli.
- **App Gestore**: per visualizzare e gestire i pagamenti.
- **Microservizio Ordini**: per gestire la logica di backend.

## Come avviare il progetto

1. **Microservizio Ordini**:
   - Avvia con:
     ```bash
     mvn spring-boot:run
     ```
   - Endpoint principali:
     - `http://localhost:8080/ordini`
     - `http://localhost:8080/dettagli-ordine`
     - `http://localhost:8080/prodotti`
     - `http://localhost:8080/tavoli`

2. **App Cameriere**:
   - Apri la cartella `app-cameriere` e avvialo da terminale con cordova run

3. **App Gestore**:
   - Apri la cartella `app-gestore` e avvialo da terminale con cordova run

## Tecnologie utilizzate

- **Spring Boot**: per il backend.
- **Cordova**: per le app mobile.
- **MySQL**: per il database (puoi usare H2 per testing locale).
