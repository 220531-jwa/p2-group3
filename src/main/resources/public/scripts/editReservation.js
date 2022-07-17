/*
 * === INITIALIZATION ===
 */

/**
 * Initializes the edit reservation
 * If no id is passed page results in a 404
 * If no username is passed will assume the user is looking at their own
 * @param {string} userEmail The user email to load the reservation of
 * @param {string} resId The resevation id to load the reservation of
 */
function initalizeEditReservationPage(userEmail, resId) {
    // Checking if reservation id was given
    if (resId !== null) {
        // Loading existing user
        // Getting params - global
        username = userEmail;
        id = resId;
        updateViewExistingReservation();
    }
    else {
        // No reservation to find
        notFound404();
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

    if (username === null) {
        username = userData.email;
    }

    // Attempting to get reservation data
    const resResult = await fetchGetReservationDTOById(username, id, userData.pswd);

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

    // =======================
    // === PROCESSING DATA ===
    // =======================

    // Hiding email if not owner
    if (userData.userType !== 'OWNER') {document.getElementById('reserveeField').hidden = true;}

    // Checking if reservation is finished -> disabling status if finished
    let finished = metaData.resStatuses.FINISHED.includes(resDataJson.status);
    if (finished) {
        document.getElementById('updateStatus').disabled = true;
        document.getElementById('saveBtn').hidden = true;
    }

    // === Populating fields ===

    let reserveeElement = document.getElementById('reservee');
    reserveeElement.innerHTML = `${resDtoDataJson.userFirstName} ${resDtoDataJson.userLastName}`;
    reserveeElement.addEventListener('click', () => moveToUserPage(resDataJson.userEmail));
    let dogElement = document.getElementById('dog')
    dogElement.innerHTML = resDtoDataJson.dogName;
    dogElement.addEventListener('click', () => moveToDogPage(resDataJson.dogId));

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
    optionElement.value = resDtoDataJson.service;
    optionElement.innerHTML = resDtoDataJson.service;
    serviceElement.append(optionElement);

    document.getElementById('startDate').value = getDateFromTimestamp(resDataJson.startDateTime);
    document.getElementById('startTime').value = getTimeFromTimestamp(resDataJson.startDateTime);
    document.getElementById('endDate').value = getDateFromTimestamp(resDataJson.endDateTime);
    document.getElementById('endTime').value = getTimeFromTimestamp(resDataJson.endDateTime);
}

/**
 * Hides or Shows the upper and lower bars
 * @param {boolean} upper Whether to hide the upper bar or not
 * @param {boolean} lower Whether to hide the bottom bar or not
 */
 function setEditReservationBarVisibility(upper, lower) {
    document.getElementById('ERUpperBar').hidden = !upper;
    document.getElementById('ERLowerBar').hidden = !lower;
}

/**
 * Hides or shows the back button
 * @param {boolean} visible Whether the back button is visible or not
 */
function setEditReservationBackButtonVisibility(visible) {
    document.getElementById('editReservationBackBtn').hidden = !visible;
}

/*
 * === EVENT LISTENERS ===
 */

/**
 * Adds an event listener to the back button
 * @param {string} event The event type to look for
 * @param {function} callBackFunction The function to call when the event occurs
 */
 function addEditReservationBackButtonListener(event, callBackFunction) {
    document.getElementById('editReservationBackBtn').addEventListener(event, callBackFunction);
}

/**
 * Handles when the save button is clicked
 * Checks whether the status was changed.
 * - If it was and wasn't finished already then will save and move back to index page
 * - Otherwise will give an error saying no changes were made
 */
async function save() {
    // Checking if any changes were made
    if (resDataJson.status === document.getElementById('updateStatus').value) {
        // Wasn't changed
        document.getElementById('editReservationError').innerHTML = "Can't save with no changes. Update the status or click back.";
        return;
    }
    else {
        // Was changed
        document.getElementById('editReservationError').innerHTML = '';
    }

    // Getting user (status) updates
    const updatedReservationData = {
        status: document.getElementById('updateStatus').value
    };
    const updatedReservationDataJsonString = JSON.stringify(updatedReservationData);

    // Sending response
    const userData = getSessionUserData();
    const result = await fetchUpdateReservationById('None', id, updatedReservationDataJsonString, userData.pswd);

    // Processing response
    if (result[0] === 200) {
        // Reservation successfully updated
    }
    else {
        document.getElementById('editReservationError').innerHTML = "Failed to update reservation. Try again later.";
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
    document.getElementById('title').innerHTML = "404 : Request Not Found";
    document.getElementById('editReservationForm').innerHTML = '';
    document.getElementById('editReservationSaveBtn').hidden = true;
}