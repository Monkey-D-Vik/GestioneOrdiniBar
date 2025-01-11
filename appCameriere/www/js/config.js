const CONFIG = {
    API_URL: 'http://localhost:8080',
    HEADERS: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }
};

// Funzione helper per le chiamate fetch
CONFIG.fetchWithTimeout = async (url, options = {}) => {
    const timeout = 5000; // 5 secondi di timeout
    
    const controller = new AbortController();
    const id = setTimeout(() => controller.abort(), timeout);
    
    try {
        const response = await fetch(url, {
            ...options,
            headers: {
                ...CONFIG.HEADERS,
                ...(options.headers || {})
            },
            signal: controller.signal
        });
        
        clearTimeout(id);
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        return response;
    } catch (error) {
        clearTimeout(id);
        throw error;
    }
}; 