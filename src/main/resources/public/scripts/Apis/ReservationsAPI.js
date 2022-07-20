const baseURLReservations = "http://localhost:8080/reservations";

var allReservations = [];
var reservation = {
    id: null,
    userEmail: null,
    dogId: null,
    status: null,
    startDateTime: null,
    endDateTime: null,
};

// This loops through incoming object and fills in the object literal above.
async function updateIncomingReservation(incomingReservation) {
    Object.keys(incomingReservation).forEach((key, index) => {
        reservation[key] = incomingReservation[key];
    });
}

async function getAllReservations(token) {
    // token = JSON.stringify(token);
    let newtoken = getSessionUserData().pswd;
    console.log(newtoken);
    let response = await fetch(`${baseURLReservations}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Token: newtoken,
        },
    });

    if (response.status === 200) {
        let data = await response.json();
        return data;
        // let request = data;
        // console.log(request)

        // return request;
    } else {
        console.log("There was no data");
        return null;
        /*
            Handle error
        */
    }
}

async function getAllRservationsByUsername(username, token) {
    
    let newtoken = getSessionUserData().pswd;
    console.log(token);
    let response = await fetch(`${baseURLReservations}/${username}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Token: newtoken,
        },
        // header:{'cors':'no-cors'},
    });

    if (response.status === 200) {
        let data = await response.json();
        let request = data;

        return request;
    } else {
        console.log("There was no data");
        return null;
        /*
            Handle error
        */
    }
}

/**
 * Attempts to get the reservation information associated with the given id
 * Requires a token to access server service
 * @param {string} username The username associated with the reservation
 * @param {string} res_id The id of the request to find
 * @param {string} token The token of the current active session
 * @returns OK status with reservation information, and 400 series status with null otherwise.
 */
async function fetchGetReservationById(username, res_id, token) {
    // Init
    const url = `${baseURLReservations}/${username}/${res_id}`;

    // Sending response
    let response = await fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Token: token,
        },
    });

    // Getting data if status is ok
    let data = null;
    if (response.ok) {
        data = await response.json();
    }

    return [response.status, data];
}

/**
 * Attempts to get the reservation information associated with the given id
 * Requires a token to access server service
 * @param {string} username The username associated with the reservation
 * @param {string} res_id The id of the request to find
 * @param {string} token The token of the current active session
 * @returns OK status with reservation information, and 400 series status with null otherwise.
 */
async function fetchGetReservationDTOById(username, res_id, token) {
    // Init
    const url = `${baseURLReservations}/${username}/${res_id}/dto`;

    // Sending response
    let response = await fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Token: token,
        },
    });

    // Getting data if status is ok
    let data = null;
    if (response.ok) {
        data = await response.json();
    }

    return [response.status, data];
}

/**
 * Attempts to update the reservation information associated with the given id
 * Requires a token to access server service
 * @param {string} res_id The id of the request to find
 * @param {Object} resData The reservation data to update
 * @param {string} token The token of the current active session
 * @returns OK status with updated reservation information, and 400 series status with null otherwise.
 */
async function fetchUpdateReservationById(username, res_id, resData, token) {
    // Init
    const url = `${baseURLReservations}/${username}/${res_id}`;

    // Sending response
    let response = await fetch(url, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            Token: token,
        },
        body: resData,
    });

    // Getting data if status is ok
    let data = null;
    if (response.ok) {
        // data = await response.json();    // Nothing is returned
    }

    return [response.status, data];
}
