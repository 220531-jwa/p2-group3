/*
 * === INITIALIZATION ===
 */

window.onload = initalizePage();
function initalizePage() {
    // Getting query params
    let query = window.location.search;
    const params = new URLSearchParams(query);

    // Checking if 
    // if (params.has('id')) {
    //     // Loading existing user
    //     // Getting params - global
    //     username = params.get('username');
    //     id = params.get('id');
    //     updateViewExistingReservation();
    // }
    // else {
    //     // No reservation to find
    //     notFound404();
    // }
}

var resDtoDataJson = ""
var resDataJson = ""

var editReservation = {
    id:null,
    userEmail:null,
    dogId:null,
    status:null,
    startDateTime:null,
    endDateTime:null
}

// THIS UPDATED THE ABOVE OBJECT LITERAL
async function updateIncomingReservationEditReservation(incomingReservation){

    if(incomingReservation){
        Object.keys(incomingReservation).forEach((key, index) => {
    
            if(key == "startDateTime" ){
                
                
                let incomingDate = incomingReservation[key];
                let newDate = new Date(incomingDate);
                let day = newDate.getDate();
                let month = newDate.getMonth() + 1;
                let year = newDate.getFullYear();
                let hour = newDate.getHours();
                let minutes = newDate.getMinutes().toString();
                let seconds = newDate.getSeconds().toString();

                if(minutes.length==1){
                    minutes = minutes +"0"
                }


                
                if(seconds.length==1){
                    seconds = seconds + "0"
                    
                }
    
                let theDate = year + "-" + month + "-" + day
                let theTime = hour + ":" + minutes + ":" + seconds

                let fullDate = theDate + " " + theTime
    
                
    
                editReservation.startDateTime = fullDate;
    
    
            }else if(key=="endDateTime"){
    
                let incomingDate = incomingReservation[key];
                // let newDate = new Date(incomingDate);
                // let day = newDate.getDate();
                // let month = newDate.getMonth() + 1;
                // let year = newDate.getFullYear();
                // let hour = newDate.getHours();
                // let minutes = newDate.getMinutes().toString();
                // let seconds = newDate.getSeconds().toString();

                // if(minutes.length==1){
                //     minutes = minutes +"0"
                // }


                
                // if(seconds.length==1){
                //     seconds = seconds + "0"
                   
                // }
                let newDate = new Date(incomingDate)
                let newDatestrng = newDate.toLocaleDateString();
                console.log(newDatestrng)

                let theDate = getDateFromTimestamp(incomingDate)
                let theTime = getTimeFromTimestamp(incomingDate)

                let fullDate = theDate + " " + theTime
    
    
                editReservation.endDateTime = newDatestrng;
    
            }else{
                
                editReservation[key] = incomingReservation[key];
            }
        });

    }

}


/*
 * === HTML VIEW UPDATES ===
 */

/**
 * Updates the html page depending on who is editing.
 * If an owner is editing
 * - Email is displayed
 * - Status options are: CheckedIn, CheckedOut, Cancelled
 *      - CheckedIn can only be done after start date
 *      - CheckedIn can only be done if status was registered
 *      - CheckedOut can only be selected if checkedin
 *      - Cancelled can be done at any time
 * If a cusomter is editing
 * - Email is hidden
 * - Status can only be changed to cancelled
 * Note: Cannot change the status of a checkedout or cancelled status
 */
async function updateViewExistingReservation() {

    // ====================
    // === GETTING DATA ===
    // ====================

    // Getting user, meta, and service data
    const userData = getSessionUserData();
    const metaDataResult = await fetchGetMetaData();
    const metaData = metaDataResult[1];

    console.log('got meta data');;
    console.log(metaData);

    console.log(`username: ${username}`);

    if (username === null) {
        username = userData.email;
    }

    console.log(`username: ${username}`);

    // Attempting to get reservation data
    // const resResult = await fetchGetReservationDTOById(username, id, userData.pswd);
    const resResult = await fetchGetReservationById(userData.userEmail,id, userData.pswd);

    console.log('got resResult');
    console.log(resResult);

    // Processing response
    if (resResult[0] === 401) {
        // User not in active session
        notInActiveSession();
    }
    else if (resResult[0] !== 200) {
        // Reservation not found / or user isn't authorized to see information
        notFound404();
        return;
    }

    // Was able to get reservation information associated with id
    resDataJson = resResult[1];    // global

    // Attempting to get dog related to reservation
    const dogResult = await fetchGetDogById(resDataJson.userEmail, resDataJson.dogId, userData.pswd);

    console.log('got dogResult');
    console.log(dogResult);

    // Processing response
    if (dogResult[0] === 401) {
        // User not in active session
        notInActiveSession();
    }
    else if (dogResult[0] !== 200) {
        // Dog not found / or user isn't authorized to see information
        notFound404();
        return;
    }

    // Was able to get dog information associated with id
    dogDataJson = dogResult[1]; // global

    // =======================
    // === PROCESSING DATA ===
    // =======================

    // Hiding email if not owner
    if (userData.userType !== 'OWNER') {document.getElementById('emailField').hidden = true;}

    // Checking if reservation is finished -> disabling status if finished
    let finished = metaData.resStatuses.FINISHED.includes(resDataJson.status);
    if (finished) {
        document.getElementById('updateStatus').disabled = true;
        // document.getElementById('saveBtn').hidden = true;
    }

    // === Populating fields ===

    let emailElement = document.getElementById('email');
    emailElement.innerHTML = resDataJson.userEmail;
    emailElement.addEventListener('click', moveToEmailPage);
    let dogElement = document.getElementById('dog')
    dogElement.innerHTML = dogDataJson.dog_name;
    dogElement.addEventListener('click', moveToDogPage);

    // Status - Adding current status
    let statusElement = document.getElementById('updateStatus');
    let optionElement = document.createElement('option');
    optionElement.value = resDataJson.status;
    optionElement.innerHTML = resDataJson.status;
    statusElement.append(optionElement);

    // Adding next status to edit to if owner and isn't finished
    if (!finished && userData.userType === 'OWNER') {
        optionElement = document.createElement('option');

        // Finding status to add
        if (resDataJson.status === metaData.resStatuses.ACTIVE[0]) {
            // Current is registered -> adding CHECKEDIN
            optionElement.value = metaData.resStatuses.ACTIVE[1];
            optionElement.innerHTML = metaData.resStatuses.ACTIVE[1];
        }
        else {
            // Currently is checkedin -> adding CHECKEDOUT
            optionElement.value = metaData.resStatuses.FINISHED[0];
            optionElement.innerHTML = metaData.resStatuses.FINISHED[0];
        }
        
        statusElement.append(optionElement);
    }

    // Adding Canceled status if status isn't finished
    if (!finished) {
        optionElement = document.createElement('option');
        optionElement.value = 'CANCELLED'
        optionElement.innerHTML = 'CANCELLED'
        statusElement.append(optionElement);
    }

    let serviceElement = document.getElementById('service');
    optionElement = document.createElement('option');
    optionElement.value = "get this" // resDataJson.service;
    optionElement.innerHTML = "get this" // resDataJson.service;
    serviceElement.append(optionElement);

    document.getElementById('startDate').value = getDateFromTimestamp(resDataJson.startDateTime);
    document.getElementById('startTime').value = getTimeFromTimestamp(resDataJson.startDateTime);
    document.getElementById('endDate').value = getDateFromTimestamp(resDataJson.endDateTime);
    document.getElementById('endTime').value = getTimeFromTimestamp(resDataJson.endDateTime);
}



async function updateViewExistingReservationReTry(res_id) {

    // ====================
    // === GETTING DATA ===
    // ====================

    // Getting user, meta, and service data
    const userData = await getSessionUserData();
    const metaDataResult = await fetchGetMetaData();
    const metaData = metaDataResult[1];

    // console.log('got meta data');;
    // console.log(metaData);

    // Attempting to get reservation data
    // const resResult = await fetchGetReservationById(userData.userEmail, res_id, userData.pswd);
    const resResult = await fetchGetReservationDTOById(userData.userEmail, res_id, userData.pswd);

    console.log('got resResult');
    console.log(resResult);

    // Processing response
    if (resResult[0] === 401) {
        // User not in active session
        notInActiveSession();
    }
    else if (resResult[0] !== 200) {
        // Reservation not found / or user isn't authorized to see information
        notFound404();
        return;
    }

    // Was able to get reservation information associated with id
    resDtoDataJson = resResult[1];  // global
    resDataJson = resDtoDataJson.reservation;

    // console.log(JSON.parse(resDataJson));

    // =======================
    // === PROCESSING DATA ===
    // =======================

    // Hiding email if not owner
    if (userData.userType !== 'OWNER') {document.getElementById('reserveeField').hidden = true;}

    // Checking if reservation is finished -> disabling status if finished
    // let finished = metaData.resStatuses.FINISHED.includes(resDataJson.status);
    let finished = true
    if (finished) {
        document.getElementById('updateStatus').disabled = true;
        document.getElementById('saveBtn').hidden = true;
    }

    // === Populating fields ===

    let reserveeElement = document.getElementById('reservee');
    reserveeElement.innerHTML = `${resDtoDataJson.userFirstName} ${resDtoDataJson.userLastName}`;
    reserveeElement.addEventListener('click', () => moveToUserPage(resDataJson.userEmail));
    let dogElement = document.getElementById('dog')
    // let dgName =  resResult[1].dogName
    dogElement.innerHTML = resDtoDataJson.dogName;
    // dogElement.innerHTML = dgName;
    dogElement.addEventListener('click', () => moveToDogPage(resDataJson.dogId));

    // Status - Adding current status
    let statusElement = document.getElementById('updateStatus');
    // let optionElement = document.createElement('option');
    let stat = resDataJson.status;

    for(i=0; i<statusElement.options.length;i++){
        let val = statusElement.options[i].value;
        if(val === stat){
            statusElement.setAttribute("selected",statusElement.options[i].innerText)
        }
               
    }
    
    
    
    // optionElement.setAttribute("value",stat)
    // optionElement.innerHTML = stat;
    // statusElement.append(optionElement);

    // Adding next status to edit to if owner and isn't finished
    if (!finished && userData.userType === 'OWNER') {
        optionElement = document.createElement('option');

        // Finding status to add
        if (resDataJson.status === metaData.resStatuses.ACTIVE[0]) {
            // Current is registered -> adding CHECKEDIN
            optionElement.value = metaData.resStatuses.ACTIVE[1];
            optionElement.innerHTML = metaData.resStatuses.ACTIVE[1];
        }
        else {
            // Currently is checkedin -> adding CHECKEDOUT
            optionElement.value = metaData.resStatuses.FINISHED[0];
            optionElement.innerHTML = metaData.resStatuses.FINISHED[0];
        }
        
        statusElement.append(optionElement);
    }

    // Adding Canceled status if status isn't finished
    if (!finished) {
        optionElement = document.createElement('option');
        optionElement.value = 'CANCELLED'
        optionElement.innerHTML = 'CANCELLED'
        statusElement.append(optionElement);
    }

    let serviceElement = document.getElementById('service');
    optionElement = document.createElement('option');

    let service = resDtoDataJson.service;
    optionElement.setAttribute("value",service);
    // optionElement.value = resDtoDataJson.service;
    // optionElement.innerHTML = resDtoDataJson.service;
    optionElement.innerHTML = service;
    serviceElement.append(optionElement);

    // let startDate = getDateFromTimestamp(resDataJson.startDateTime)
    let startDate = getDateFromTimestamp(resDataJson.startDateTime)
    let startTime = getTimeFromTimestamp(resDataJson.startDateTime)
    console.log(startDate);

    // let startTime = myTimefunction(resResult[1].startDateTime);
    let endDate = getDateFromTimestamp(resDataJson.endDateTime);
    let endTime = myTimefunction(resDataJson.endDateTime);
    
    // let endDate = getDateFromTimestamp(resResult[1].endDateTime).toString();
    // let endTime = getTimeFromTimestamp(resResult[1].endDateTime).toString();

    
    document.getElementById('startDate').value=startDate
    document.getElementById('startTime').value=startTime



    document.getElementById('endDate').value =endDate
    document.getElementById('endTime').value = endTime

    // document.getElementById('startDate').value = getDateFromTimestamp(startDate);
    // document.getElementById('startTime').value = getTimeFromTimestamp(startTime);
    // document.getElementById('endDate').value = getDateFromTimestamp(endDate);
    // document.getElementById('endTime').value = getTimeFromTimestamp(endTime);
}

/*
 * === EVENT LISTENERS ===
 */

/**
 * Handles when the back button is clicked.
 */
function back() {
    // TODO: Be useful for index.html
    // Currently just 'checks' if user is in an active session or not
    // If they are, moved to index page
    getSessionUserData(); // Delete this later when finished being useful
    location.href = "../html/index.html";
}

/**
 * Handles when the save button is clicked
 * Checks whether the status was changed.
 * - If it was and wasn't finished already then will save and move back to index page
 * - Otherwise will give an error saying no changes were made
 */
async function save() {

    console.log("i am here")
    // Checking if any changes were made
    if (resDataJson.status === document.getElementById('updateStatus').value) {
        // Wasn't changed
        document.getElementById('error').innerHTML = "Can't save with no changes. Update the status or click back.";
        return;
    }
    else {
        // Was changed
        document.getElementById('error').innerHTML = '';
    }

    // Getting user (status) updates
    const updatedReservationData = {
        status: document.getElementById('updateStatus').value
    };

    let newReservation = {
            id: resDataJson.id,
            userEmail:resDataJson.userEmail,
            dogId:resDataJson.dogId,
            serviceId: resDataJson.serviceId,
            status:resDataJson.status,
            startDateTime:resDataJson.startDateTime,
            endDateTime:resDataJson.endDateTime
        }
        updateIncomingReservationEditReservation(newReservation)
        console.log(editReservation)
        
    // const updatedReservationDataJsonString = JSON.stringify(updatedReservationData);
    const updatedReservationDataJsonString = JSON.stringify(editReservation);

    // Sending response
    const userData = getSessionUserData();
    let res_id = resDtoDataJson.reservation.id
    console.log(updatedReservationDataJsonString)
    const result = await fetchUpdateReservationById(userData.email, res_id, updatedReservationDataJsonString, userData.pswd);

    // Processing response
    if (result[0] === 200) {
        // Reservation successfully updated
        // console.log(result[0])
        
    }
    else {
        document.getElementById('error').innerHTML = "Failed to update reservation. Try again later.";
    }



}

/**
 * Handles when the dogs name is clicked
 * Redirects the user to the dogs information
 * @param {string} id The id of the dog
 */
function moveToDogPage(id) {
    console.log(`dog link was clicked wtih ${id}`);
}

/**
 * Handles when the email associated with the reservation is clicked
 * Redirects the usr to the users page (should only be clicked by the owner)
 * @param {string} email The email of the user
 */
function moveToUserPage(email) {
    console.log(`User link was clicked with ${email}`);
}

/*
 * === UTILITY ===
 */

/**
 * Updates the user profile page for when a user wasn't found or was invalid.
 */
 function notFound404() {
    // document.getElementById('title').innerHTML = "404 : Request Not Found";
    // document.getElementById('form').innerHTML = '';
    // document.getElementById('btnSubmit').hidden = true;
}




function mydatefunction(datenumber){
    
                
        
        let newDate = new Date(datenumber);
        let day = newDate.getDate();
        let month = newDate.getMonth() + 1;
        let year = newDate.getFullYear();
        let hour = newDate.getHours();
        let minutes = newDate.getMinutes().toString();
        let seconds = newDate.getSeconds().toString();

        if(minutes.length==1){
            minutes = minutes +"0"
        }


        
        if(seconds.length==1){
            seconds = seconds + "0"
            
        }

        let theDate = month + "/" + day + "/" + year
        let theTime = hour + ":" + minutes + ":" + seconds

        let fullDate = theDate + " " + theTime

        return theDate;

    


}
function myTimefunction(datenumber){
    
                
    
    let newDate = new Date(datenumber);
    let day = newDate.getDate();
    let month = newDate.getMonth() + 1;
    let year = newDate.getFullYear();
    let hour = newDate.getHours();
    let minutes = newDate.getMinutes().toString();
    let seconds = newDate.getSeconds().toString();

    if(minutes.length==1){
        minutes = minutes +"0"
    }


    
    if(seconds.length==1){
        seconds = seconds + "0"
        
    }

    let theDate = month + "/" + day + "/" + year
    let theTime = hour + ":" + minutes + ":" + seconds

    let fullDate = theDate + " " + theTime

    console.log(theTime)

    return theTime;

    


}