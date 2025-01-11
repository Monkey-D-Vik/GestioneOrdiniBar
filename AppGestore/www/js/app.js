document.addEventListener('deviceready', onDeviceReady, false);

function onDeviceReady() {
    initApp();
}

function initApp() {
    // Event listeners per la navigazione
    document.getElementById('btnOrdini').addEventListener('click', mostraOrdini);
    document.getElementById('btnProdotti').addEventListener('click', mostraProdotti);
    
    // Event listener per il form prodotto
    document.getElementById('prodottoForm').addEventListener('submit', salvaProdotto);
    document.getElementById('aggiungiProdottoBtn').addEventListener('click', () => mostraFormProdotto());
    
    // Event listener per tornare indietro dal dettaglio ordine
    document.querySelector('.back-btn').addEventListener('click', mostraOrdini);
    
    // Aggiungi event listener per il bottone Annulla
    document.getElementById('btnAnnulla').addEventListener('click', chiudiModal);
    
    // Carica inizialmente gli ordini aperti
    caricaOrdiniAperti();
    
    document.getElementById('btnOrdiniChiusi').addEventListener('click', mostraOrdiniChiusi);
    document.querySelector('.back-btn-chiuso').addEventListener('click', mostraOrdiniChiusi);
}

async function caricaOrdiniAperti() {
    try {
        const response = await CONFIG.fetchWithTimeout(`${CONFIG.API_URL}/ordini/aperti`);
        const ordini = await response.json();
        const listaOrdini = document.getElementById('listaOrdini');
        listaOrdini.innerHTML = '';

        ordini.forEach(ordine => {
            const ordineElement = document.createElement('div');
            ordineElement.className = 'ordine-card';
            ordineElement.innerHTML = `
                <div class="header">
                    <h3>Tavolo ${ordine.tavoloId.numero}</h3>
                    <span class="totale">€${ordine.totale}</span>
                </div>
                <div class="button-group">
                    <button class="primary-btn btn-dettagli" data-ordine-id="${ordine.ordiniId}">
                        Vedi Dettagli
                    </button>
                    <button class="secondary-btn btn-chiudi" data-ordine-id="${ordine.ordiniId}">
                        Chiudi Ordine
                    </button>
                </div>
            `;

            // Aggiungi event listeners ai bottoni
            const btnDettagli = ordineElement.querySelector('.btn-dettagli');
            const btnChiudi = ordineElement.querySelector('.btn-chiudi');
            
            btnDettagli.addEventListener('click', () => mostraDettaglioOrdine(ordine.ordiniId));
            btnChiudi.addEventListener('click', () => chiudiOrdine(ordine.ordiniId));
            
            listaOrdini.appendChild(ordineElement);
        });
    } catch (error) {
        console.error('Errore nel caricamento ordini:', error);
        alert('Errore nel caricamento degli ordini');
    }
}

async function mostraDettaglioOrdine(ordineId) {
    try {
        const response = await CONFIG.fetchWithTimeout(`${CONFIG.API_URL}/dettagliOrdine/ordine/${ordineId}`);
        const dettagli = await response.json();
        
        document.getElementById('ordiniSection').style.display = 'none';
        const dettaglioSection = document.getElementById('dettaglioOrdineSection');
        const dettaglioContent = document.getElementById('dettaglioOrdineContent');
        
        dettaglioContent.innerHTML = dettagli.map(dettaglio => `
            <div class="dettaglio-item">
                <div class="prodotto-info">
                    <h4>${dettaglio.prodottoId.nome}</h4>
                    <p>Quantità: ${dettaglio.quantita}</p>
                    <p>Prezzo: €${dettaglio.prodottoId.prezzo}</p>
                    <p>Note: ${dettaglio.note}</p>
                </div>
                <div class="subtotale">
                    Subtotale: €${(dettaglio.quantita * dettaglio.prodottoId.prezzo).toFixed(2)}
                </div>
            </div>
        `).join('');
        
        dettaglioSection.style.display = 'block';
        
        // Aggiorna il listener per il pulsante chiudi ordine
        document.getElementById('chiudiOrdineBtn').onclick = () => chiudiOrdine(ordineId);
    } catch (error) {
        console.error('Errore nel caricamento dettagli:', error);
        alert('Errore nel caricamento dei dettagli');
    }
}

async function chiudiOrdine(ordineId) {
    if (!confirm('Sei sicuro di voler chiudere questo ordine?')) return;
    
    try {
        await CONFIG.fetchWithTimeout(`${CONFIG.API_URL}/ordini/chiudi/${ordineId}`, {
            method: 'PUT'
        });
        
        alert('Ordine chiuso con successo!');
        mostraOrdini();
    } catch (error) {
        console.error('Errore nella chiusura dell\'ordine:', error);
        alert('Errore nella chiusura dell\'ordine');
    }
}

// Funzioni per la gestione dei prodotti
async function caricaProdotti() {
    try {
        const response = await CONFIG.fetchWithTimeout(`${CONFIG.API_URL}/prodotti`);
        const prodotti = await response.json();
        const listaProdotti = document.getElementById('listaProdotti');
        listaProdotti.innerHTML = '';

        prodotti.forEach(prodotto => {
            const prodottoElement = document.createElement('div');
            prodottoElement.className = 'prodotto-item';
            prodottoElement.innerHTML = `
                <div class="prodotto-info">
                    <h4>${prodotto.nome}</h4>
                    <p>Categoria: ${prodotto.categoria}</p>
                    <p>Prezzo: €${prodotto.prezzo}</p>
                </div>
                <div class="prodotto-actions">
                    <button class="primary-btn btn-modifica" data-prodotto='${JSON.stringify(prodotto)}'>
                        Modifica
                    </button>
                    <button class="secondary-btn btn-elimina" data-prodotto-id="${prodotto.prodottoId}">
                        Elimina
                    </button>
                </div>
            `;

            // Aggiungi event listeners ai bottoni
            const btnModifica = prodottoElement.querySelector('.btn-modifica');
            const btnElimina = prodottoElement.querySelector('.btn-elimina');
            
            btnModifica.addEventListener('click', (e) => {
                const prodottoData = JSON.parse(e.target.dataset.prodotto);
                mostraFormProdotto(prodottoData);
            });
            btnElimina.addEventListener('click', () => eliminaProdotto(prodotto.prodottoId));
            
            listaProdotti.appendChild(prodottoElement);
        });
    } catch (error) {
        console.error('Errore nel caricamento prodotti:', error);
        alert('Errore nel caricamento dei prodotti');
    }
}

function mostraFormProdotto(prodotto = null) {
    const form = document.getElementById('prodottoForm');
    const modal = document.getElementById('prodottoFormModal');
    
    if (prodotto) {
        form.prodottoId.value = prodotto.prodottoId;
        form.nome.value = prodotto.nome;
        form.categoria.value = prodotto.categoria;
        form.prezzo.value = prodotto.prezzo;
    } else {
        form.reset();
        form.prodottoId.value = '';
    }
    
    modal.style.display = 'block';
}

function chiudiModal() {
    document.getElementById('prodottoFormModal').style.display = 'none';
}

async function salvaProdotto(event) {
    event.preventDefault();
    
    const prodottoData = {
        nome: document.getElementById('nome').value,
        categoria: document.getElementById('categoria').value,
        prezzo: parseFloat(document.getElementById('prezzo').value)
    };
    
    const prodottoId = document.getElementById('prodottoId').value;
    const method = prodottoId ? 'PUT' : 'POST';
    const url = prodottoId ? 
        `${CONFIG.API_URL}/prodotti/${prodottoId}` : 
        `${CONFIG.API_URL}/prodotti`;

    try {
        await CONFIG.fetchWithTimeout(url, {
            method,
            body: JSON.stringify(prodottoData)
        });
        
        alert(`Prodotto ${prodottoId ? 'modificato' : 'aggiunto'} con successo!`);
        chiudiModal();
        caricaProdotti();
    } catch (error) {
        console.error('Errore nel salvataggio del prodotto:', error);
        alert('Errore nel salvataggio del prodotto');
    }
}

async function eliminaProdotto(prodottoId) {
    if (!confirm('Sei sicuro di voler eliminare questo prodotto?')) return;
    
    try {
        await CONFIG.fetchWithTimeout(`${CONFIG.API_URL}/prodotti/${prodottoId}`, {
            method: 'DELETE'
        });
        
        alert('Prodotto eliminato con successo!');
        caricaProdotti();
    } catch (error) {
        console.error('Errore nell\'eliminazione del prodotto:', error);
        alert('Errore nell\'eliminazione del prodotto');
    }
}

function mostraOrdini() {
    document.getElementById('ordiniSection').style.display = 'block';
    document.getElementById('dettaglioOrdineSection').style.display = 'none';
    document.getElementById('dettaglioOrdineChiusoSection').style.display = 'none';
    document.getElementById('ordiniChiusiSection').style.display = 'none';
    document.getElementById('prodottiSection').style.display = 'none';
    
    document.getElementById('btnOrdini').classList.add('active');
    document.getElementById('btnOrdiniChiusi').classList.remove('active');
    document.getElementById('btnProdotti').classList.remove('active');
    
    caricaOrdiniAperti();
}

function mostraProdotti() {
    document.getElementById('ordiniSection').style.display = 'none';
    document.getElementById('dettaglioOrdineSection').style.display = 'none';
    document.getElementById('prodottiSection').style.display = 'block';
    document.getElementById('btnOrdini').classList.remove('active');
    document.getElementById('btnProdotti').classList.add('active');
    caricaProdotti();
}

async function caricaOrdiniChiusi() {
    try {
        const response = await CONFIG.fetchWithTimeout(`${CONFIG.API_URL}/ordini/chiusi`);
        const ordini = await response.json();
        const listaOrdini = document.getElementById('listaOrdiniChiusi');
        listaOrdini.innerHTML = '';

        ordini.forEach(ordine => {
            const dataOrdine = new Date(ordine.dataCreazione).toLocaleString();
            const ordineElement = document.createElement('div');
            ordineElement.className = 'ordine-chiuso-card';
            ordineElement.innerHTML = `
                <div class="header">
                    <h3>Tavolo ${ordine.tavoloId.numero}</h3>
                    <span class="totale">€${ordine.totale}</span>
                </div>
                <div class="info">
                    <span>Data: ${dataOrdine}</span>
                </div>
                <div class="button-group">
                    <button class="primary-btn btn-dettagli-chiuso" data-ordine-id="${ordine.ordiniId}">
                        Vedi Dettagli
                    </button>
                </div>
            `;

            const btnDettagli = ordineElement.querySelector('.btn-dettagli-chiuso');
            btnDettagli.addEventListener('click', () => mostraDettaglioOrdineChiuso(ordine.ordiniId));
            
            listaOrdini.appendChild(ordineElement);
        });
    } catch (error) {
        console.error('Errore nel caricamento ordini chiusi:', error);
        alert('Errore nel caricamento degli ordini chiusi');
    }
}

async function mostraDettaglioOrdineChiuso(ordineId) {
    try {
        const response = await CONFIG.fetchWithTimeout(`${CONFIG.API_URL}/dettagliOrdine/ordine/${ordineId}`);
        const dettagli = await response.json();
        
        document.getElementById('ordiniChiusiSection').style.display = 'none';
        const dettaglioSection = document.getElementById('dettaglioOrdineChiusoSection');
        const dettaglioContent = document.getElementById('dettaglioOrdineChiusoContent');
        
        dettaglioContent.innerHTML = dettagli.map(dettaglio => `
            <div class="dettaglio-item">
                <div class="prodotto-info">
                    <h4>${dettaglio.prodottoId.nome}</h4>
                    <p>Quantità: ${dettaglio.quantita}</p>
                    <p>Prezzo: €${dettaglio.prodottoId.prezzo}</p>
                </div>
                <div class="subtotale">
                    Subtotale: €${(dettaglio.quantita * dettaglio.prodottoId.prezzo).toFixed(2)}
                </div>
            </div>
        `).join('');
        
        dettaglioSection.style.display = 'block';
        
        // Aggiorna il listener per il pulsante elimina ordine
        document.getElementById('eliminaOrdineBtn').onclick = () => eliminaOrdine(ordineId);
    } catch (error) {
        console.error('Errore nel caricamento dettagli:', error);
        alert('Errore nel caricamento dei dettagli');
    }
}

async function eliminaOrdine(ordineId) {
    if (!confirm('Sei sicuro di voler eliminare questo ordine?')) return;
    
    try {
        await CONFIG.fetchWithTimeout(`${CONFIG.API_URL}/ordini/${ordineId}`, {
            method: 'DELETE'
        });
        
        alert('Ordine eliminato con successo!');
        mostraOrdiniChiusi();
    } catch (error) {
        console.error('Errore nell\'eliminazione dell\'ordine:', error);
        alert('Errore nell\'eliminazione dell\'ordine');
    }
}

function mostraOrdiniChiusi() {
    document.getElementById('ordiniSection').style.display = 'none';
    document.getElementById('dettaglioOrdineSection').style.display = 'none';
    document.getElementById('dettaglioOrdineChiusoSection').style.display = 'none';
    document.getElementById('prodottiSection').style.display = 'none';
    document.getElementById('ordiniChiusiSection').style.display = 'block';
    
    // Aggiorna i pulsanti attivi
    document.getElementById('btnOrdini').classList.remove('active');
    document.getElementById('btnOrdiniChiusi').classList.add('active');
    document.getElementById('btnProdotti').classList.remove('active');
    
    caricaOrdiniChiusi();
} 