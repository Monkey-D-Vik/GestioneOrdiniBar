document.addEventListener('deviceready', onDeviceReady, false);

let ordineCorrente = null;

function onDeviceReady() {
    // Inizializza l'applicazione
    initApp();
}

function initApp() {
    // Event listeners
    document.getElementById('formOrdine').addEventListener('submit', creaOrdine);
    document.getElementById('formProdotti').addEventListener('submit', aggiungiProdotto);
    
    // Carica la lista dei prodotti
    caricaProdotti();
}

async function creaOrdine(event) {
    event.preventDefault();
    
    const tavoloId = document.getElementById('tavoloId').value;
    
    const ordineData = {
        tavoloId: {
            tavolo_id: parseInt(tavoloId)
        },
        dataCreazione: new Date().toISOString(),
        stato: "IN_PREPARAZIONE"
    };

    try {
        const response = await CONFIG.fetchWithTimeout(`${CONFIG.API_URL}/ordini`, {
            method: 'POST',
            body: JSON.stringify(ordineData)
        });

        ordineCorrente = await response.json();
        
        document.getElementById('aggiuntaProdotti').style.display = 'block';
        document.getElementById('dettagliOrdine').style.display = 'block';
        
        alert('Ordine creato con successo!');
    } catch (error) {
        console.error('Errore:', error);
        alert('Errore nella creazione dell\'ordine');
    }
}

async function caricaProdotti() {
    try {
        const response = await CONFIG.fetchWithTimeout(`${CONFIG.API_URL}/prodotti`);
        const prodotti = await response.json();
        const select = document.getElementById('prodottoId');
        select.innerHTML = ''; // Pulisci le opzioni esistenti
        
        prodotti.forEach(prodotto => {
            const option = document.createElement('option');
            option.value = prodotto.prodottoId;
            option.textContent = `${prodotto.nome} - €${prodotto.prezzo}`;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Errore nel caricamento dei prodotti:', error);
        alert('Errore nel caricamento dei prodotti. Riprova più tardi.');
    }
}

async function aggiungiProdotto(event) {
    event.preventDefault();
    
    if (!ordineCorrente) {
        alert('Crea prima un ordine');
        return;
    }

    const prodottoId = document.getElementById('prodottoId').value;
    const quantita = document.getElementById('quantita').value;
    const note = document.getElementById('note').value;

    const dettaglioData = {
        ordineId: {
            ordiniId: ordineCorrente.ordiniId
        },
        prodottoId: {
            prodottoId: parseInt(prodottoId)
        },
        quantita: parseInt(quantita),
        note: note
    };

    try {
        const response = await CONFIG.fetchWithTimeout(`${CONFIG.API_URL}/dettagliOrdine`, {
            method: 'POST',
            body: JSON.stringify(dettaglioData)
        });

        await aggiornaListaDettagli();
        alert('Prodotto aggiunto con successo!');
    } catch (error) {
        console.error('Errore:', error);
        alert('Errore nell\'aggiunta del prodotto');
    }
}

async function aggiornaListaDettagli() {
    if (!ordineCorrente) return;

    try {
        const response = await CONFIG.fetchWithTimeout(`${CONFIG.API_URL}/dettagliOrdine/ordine/${ordineCorrente.ordiniId}`);
        const dettagli = await response.json();
        const listaDettagli = document.getElementById('listaDettagli');
        listaDettagli.innerHTML = '';

        dettagli.forEach(dettaglio => {
            const dettaglioElement = document.createElement('div');
            dettaglioElement.className = 'dettaglio-item';
            dettaglioElement.innerHTML = `
                <p>Prodotto: ${dettaglio.prodottoId.nome}</p>
                <p>Quantità: ${dettaglio.quantita}</p>
                <p>Note: ${dettaglio.note}</p>
                <p>Subtotale: €${(dettaglio.prodottoId.prezzo * dettaglio.quantita).toFixed(2)}</p>
                <div class="dettaglio-controls">
                    <input type="number" min="1" value="${dettaglio.quantita}" class="quantita-input" data-dettaglio-id="${dettaglio.dettaglioId}">
                    <button class="update-btn" data-dettaglio-id="${dettaglio.dettaglioId}">Aggiorna Quantità</button>
                    <button class="delete-btn" data-dettaglio-id="${dettaglio.dettaglioId}">Elimina</button>
                </div>
            `;

            const updateBtn = dettaglioElement.querySelector('.update-btn');
            const deleteBtn = dettaglioElement.querySelector('.delete-btn');

            updateBtn.addEventListener('click', () => modificaDettaglio(dettaglio.dettaglioId));
            deleteBtn.addEventListener('click', () => eliminaDettaglio(dettaglio.dettaglioId));

            listaDettagli.appendChild(dettaglioElement);
        });

        await aggiornaTotale();
    } catch (error) {
        console.error('Errore:', error);
        alert('Errore nel caricamento dei dettagli');
    }
}

async function modificaDettaglio(dettaglioId) {
    const quantitaInput = document.querySelector(`.quantita-input[data-dettaglio-id="${dettaglioId}"]`);
    const nuovaQuantita = quantitaInput.value;
    
    if (!nuovaQuantita || nuovaQuantita < 1) {
        alert('Inserisci una quantità valida');
        return;
    }

    try {
        const response = await CONFIG.fetchWithTimeout(`${CONFIG.API_URL}/dettagliOrdine/${dettaglioId}`, {
            method: 'PUT',
            body: JSON.stringify({
                dettaglioId: dettaglioId,
                ordineId: ordineCorrente,
                quantita: parseInt(nuovaQuantita)
            })
        });

        await aggiornaListaDettagli();
        alert('Dettaglio modificato con successo!');
    } catch (error) {
        console.error('Errore:', error);
        alert('Errore nella modifica del dettaglio');
    }
}

async function eliminaDettaglio(dettaglioId) {
    if (!confirm('Sei sicuro di voler eliminare questo prodotto dall\'ordine?')) {
        return;
    }

    try {
        await CONFIG.fetchWithTimeout(`${CONFIG.API_URL}/dettagliOrdine/${dettaglioId}`, {
            method: 'DELETE'
        });

        await aggiornaListaDettagli();
        alert('Prodotto eliminato con successo!');
    } catch (error) {
        console.error('Errore:', error);
        alert('Errore nell\'eliminazione del prodotto');
    }
}

async function aggiornaTotale() {
    if (!ordineCorrente) return;

    try {
        const response = await CONFIG.fetchWithTimeout(`${CONFIG.API_URL}/ordini/${ordineCorrente.ordiniId}/totale`);
        const totale = await response.json();
        document.getElementById('totaleOrdine').textContent = `€${totale.toFixed(2)}`;
    } catch (error) {
        console.error('Errore nel calcolo del totale:', error);
    }
} 