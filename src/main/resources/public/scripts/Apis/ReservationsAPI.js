
const baseURL = "http://localhost:8080/reservations";


var allReservations = []
var reservation = {
    id:null,
    userEmail:null,
    dogId:null,
    status:null,
    startDateTime:null,
    endDateTime:null
}

async function updateIncomingReservation(incomingReservation){

    Object.keys(incomingReservation).forEach((key, index) => {
        reservation[key] = incomingReservation[key];
    });

}

async function getAllReservations(token){


    let response = await fetch(`${baseURL}`,{
        method:'GET',
        header:{
            'Content-Type': 'application/json',
            // 'Token':token
        },
        // header:{'cors':'no-cors'},
        
    });



    if (response.status === 200) {
       
        let data = await response.json();
        return data
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

async function getAllRservationsByUsername(username,token){


    let response = await fetch(`${baseURL}/${username}`,{
        method:'GET',
        header:{
            'Content-Type': 'application/json',
            'Token':token
        },
        // header:{'cors':'no-cors'},
        
    });

    if (response.status === 200) {
    
        let data = await await response.json();
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
 * @param {string} res_id The id of the request to find
 * @param {string} token The token of the current active session
 * @returns OK status with reservation information, and 400 series status with null otherwise.
 */
async function fetchGetReservationById(res_id, token) {
    // Init
    const url = `${baseURL}/NULL/${res_id}`

    // Sending response
    let response = await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Token': token
        }
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
async function fetchUpdateReservationById(res_id, resData, token) {
        // Init
        const url = `${baseURL}/NULL/${res_id}`
    
        // Sending response
        let response = await fetch(url, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'Token': token
            },
            body: resData
        });
    
        // Getting data if status is ok
        let data = null;
        if (response.ok) {
            // data = await response.json();    // Nothing is returned
        }
    
        return [response.status, data];
}