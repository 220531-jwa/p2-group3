/*
 * === INITIAL DATA ===
 */

const baseDogURL = "http://localhost:8080/dogs";
const baseDogHeaders = {
    "Content-Type": "application/json",
};

/*
 * === FETCH CALLS ===
 */

/*
 * === GET ===
 */

/**
 * Attempts to get meta data from the server
 * @param {string} id Id of the dog to get
 * @param {string} token Active session token of the current user
 * @returns OK status with meta information, and 400 series status with null otherwise
 */
async function fetchGetDogById(username, id, token) {
    // Init
    const url = `${baseDogURL}/dogs/${username}/${id}`;

    // Sending response
    let response = await fetch(url, {
        method: "GET",
        headers: addTokenHeader(baseDogHeaders, token),
    });

    // Getting data if status is ok
    let data = null;
    if (response.ok) {
        data = await response.json();
    }

    return [response.status, data];
}


async function getAllDogsByUsername(username,token){
	let response = await fetch(`${baseDogURL}/${username}`,{
		method:'GET',
		headers: addTokenHeader(baseDogHeaders, token),
	});
	
	if(response.status === 200) {
		let data = await response.json();
		let request = data;
		
		return request;
	}
	else {
		console.log("There was no data");
        return null;
    }
}

async function getAllDogs(token){
		let response = await fetch(`${baseDogURL}`,{
		method:'GET',
		headers: addTokenHeader(baseDogHeaders, token),
	});
	
	if(response.status === 200) {
		let data = await response.json();
		let request = data;
		
		return request;
	}
	else {
		console.log("There was no data");
        return null;
    }
}
