/*
 * === INITIAL DATA ===
 */

const baseURL = 'http://localhost:8080';
const baseHeaders = {
    'Content-Type': 'application/json'
};

/*
 * === FETCH CALLS ===
 */

/*
 * === POST ===
 */

/**
 * Attempts to login the user with the given user credentials
 * @param {string} username The username of the user
 * @param {string} password The password of the user
 * @returns OK status with User information containing token, and 400 series status with null otherwise
 */
async function fetchLoginUser(username, password) {
    // Init
    const url = `${baseURL}/login`;

    // Converting credentials into json string
    const credentials = {
        email: username,
        pswd: password
    };
    const credentialsJson = JSON.stringify(credentials);

    // Sending response
    let response = await fetch(url, {
        method: 'POST',
        headers: baseHeaders,
        body: credentialsJson
    });

    // Getting data if status is ok
    let data = null;
    if (response.ok) {
        data = await response.json();
    }

    return [response.status, data];
}

/**
 * Attempts to logout the user with the given token
 * @param {string} token The token associated with the active session to logout of
 * @returns The response status
 */
async function fetchLogoutUser(token) {
    // Init
    const url = `${baseURL}/logout`;

    // Sending reponse
    let response = await fetch(url, {
        method: 'POST',
        headers: addTokenHeader(baseHeaders, token)
    });

    return response.status;
}

/**
 * Attempts to create a new user with the given userData
 * @param {string} userData The user data formatted as a JSON string
 * @returns OK status with User information containing token, and 400 series status with null otherwise
 */
async function fetchCreateNewUser(userData) {
    // Init
    const url = `${baseURL}/users`;

    // Sending response
    let response = await fetch(url, {
        method: 'POST',
        headers: baseHeaders,
        body: userData
    });

    // Getting data if status is ok
    let data = null;
    if (response.ok) {
        data = await response.json();
    }

    return [response.status, data];
}

/*
 * === GET ===
 */

/**
 * Attempts to get the user information associated with the given username
 * Requires a token to access server service
 * @param {string} username The user information to find
 * @param {string} token The token of the current active session
 * @returns OK status with User information, and 400 series status with null otherwise
 */
async function fetchGetUserByUsername(username, token) {
    // Init
    const url = `${baseURL}/users/${username}`;

    // Sending response
    let response = await fetch(url, {
        method: 'GET',
        headers: addTokenHeader(baseHeaders, token),
        body: userData
    });

    // Getting data if status is ok
    let data = null;
    if (response.ok) {
        data = await response.json();
    }

    return [response.status, data];
}

/*
 * === PATCH ===
 */

/**
 * Attempts to update the user associated with the given username with the given userData.
 * Requires a token to access server service
 * @param {string} username The user to update
 * @param {Object} userData The user data with fields to update
 * @param {string} token The token of the current active session
 * @returns OK status with updated user information, and 400 series status with null otherwise
 */
async function fetchUpdateUserByUsername(username, userData, token) {
    // Init
    const url = `${baseURL}/users/${username}`;

    // Sending response
    let response = await fetch(url, {
        method: 'PATCH',
        headers: addTokenHeader(baseHeaders, token),
        body: userData
    });

    // Getting data if status is ok
    let data = null;
    if (response.ok) {
        data = await response.json();
    }

    return [response.status, data];
}