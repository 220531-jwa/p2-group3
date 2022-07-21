/*
 * === INITIAL DATA ===
 */

const baseMetaURL = 'http://localhost:8080';
const baseMetaHeaders = {
    'Content-Type': 'application/json'
};

/*
 * === FETCH CALLS ===
 */

/*
 * === GET ===
 */

/**
 * Attempts to get meta data from the server
 * @returns OK status with meta information, and 400 series status with null otherwise
 */
async function fetchGetMetaData() {
    // Init
    const url = `${baseMetaURL}/meta`;

    // Sending response
    let response = await fetch(url, {
        method: 'GET',
        headers: baseMetaHeaders
    });

    // Getting data if status is ok
    let data = null;
    if (response.ok) {
        data = await response.json();
    }

    return [response.status, data];
}

/**
 * Attempts to get service data from the server
 * @returns OK status with service information, and 400 series status with null otherwise
 */
async function fetchGetServiceData() {
    // init 
    const url = `${baseMetaURL}/services`

    // Sending response
    let response = await fetch(url, {
        method: 'GET',
        headers: baseMetaHeaders
    });

    // Getting data if status is ok
    let data = null;
    if (response.ok) {
        data = await response.json();
    }

    return [response.status, data];
}