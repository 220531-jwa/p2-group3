/*
 * === ACTIVE SESSION ===
 */

/**
 * Gets the user data from the current session.
 * If no user data is found, then the user isn't in an active session.
 * @returns The user data for the currently active session
 */
 function getSessionUserData() {
    // Getting session data
    const userData = sessionStorage.userData;

    // Checking if in active session
    if (userData === undefined) {
        // Not in active session
        notInActiveSession();
    }

    // Return session data as json
    return JSON.parse(userData);
}

/**
 * Is called when a user logins in or creates a new user
 * Stores the user session data and moves to home page
 * @param {Object} userData The user data to save
 */
function inActiveSession(userData) {
    // Saving user session
    sessionStorage.userData = JSON.stringify(userData);

    // Moving to home page
    location.href = "../html/index.html";
}

/**
 * Is called when the user isn't in an active session.
 * Clears any left over session data, and redirects user to login page.
 */
 function notInActiveSession() {
    // Clearing storage
    sessionStorage.clear();

    // Moving to login page
    location.href = "../html/login.html";
}

/**
 * Tells the server that user is logging out.
 * Calls notInActiveSession.
 */
 function logout() {
    // Init
    const url = "http://localhost:8080/logout";
    const userData = getSessionUserData();


    // Logging out - Don't care about response
    fetchLogoutUser(userData.password);

    // Moving to inacitve session
    notInActiveSession();
}

/*
 * === FETCH ===
 */

/**
 * Adds a token field to the given HTTP headers object
 * @param {Object} headers The HTTP headers to add the token to
 * @param {String} token The token to add to the headers
 * @returns The headers with the token added
 */
function addTokenHeader(headers, token) {
    let newHeaderrs = {...headers};
    newHeaderrs.Token = token;
    return newHeaderrs;
}

/*
 * === TIMESTAMPS ===
 */

/**
 * Converts a timestamp into a YYYY-MM-DD format
 * @param {Timestamp} timestamp The timestamp to convert
 */
 function getDateFromTimestamp(timestamp) {
    // Init
    const pad = (num) => String(num).padStart(2, '0');
    let dateStamp = new Date(timestamp);

    // Converting to date
    let date = `${dateStamp.getFullYear()}-${pad(dateStamp.getMonth())}-${pad(dateStamp.getDate())}`;

    return date;
}

/**
 * Converts a timestamp into a HH:MM:SS format
 * @param {Timestamp} timestamp The timestamp to convert
 */
function getTimeFromTimestamp(timestamp) {
    // Init
    const pad = (num) => String(num).padStart(2, '0');
    let dateStamp = new Date(timestamp);

    // Converting to date
    let time = `${pad(dateStamp.getHours())}:${pad(dateStamp.getMinutes())}:${pad(dateStamp.getSeconds())}`;

    return time;
}

/**
 * Converts a timestamp into a YYYY-MM-DD HH:MM:SS format
 * @param {Timestamp} timestamp The timestamp to convert
 * @returns 
 */
function getDateTimeFromTimestamp(timestamp) {
    return `${getDateFromTimestamp(timestamp)} ${getTimeFromTimestamp(timestamp)}`
}

/*
 * === Loading Pages ===
 */

async function getHTMLPage(url) {
    // Getting the entire html
    const HTMLText = await (await fetch(url)).text();

    // keeping body
    const bodyRegex = new RegExp('<body>[^]*</body>');
    let HTMLBody = HTMLText.match(bodyRegex)[0];

    // Removing scripts
    HTMLBody = HTMLBody.replace(/<script[^]*script>/g, '');

    return HTMLBody;
}