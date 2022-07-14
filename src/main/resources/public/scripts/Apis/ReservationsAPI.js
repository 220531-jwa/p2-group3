
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


async function getReservationById(username,res_id,token){

let response = await fetch(`${baseURL}/${username}/${res_id}`,{
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