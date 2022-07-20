//EVENT LISTENERS
window.onload = setUpNewReservationPage();

let allUserDogs = [];

function setUpNewReservationPage() {
    let user = getSessionUserData();
    let dadogs = await getAllDogsByUsername(user.email, user.pswd);
    console.log(dadogs)

    //now get users dogs
    //sending get request to API and waiting for response to be returned and printing response to dropdown list
    
    // async function getAllDogsByUsername(user, token) {
    //     console.log(inputDogName);
    //     let url = `http://localhost:8080/dogs/${user}`;
    //     let res = await fetch(url, {
    //         method: "GET",
    //         headers: {
    //             "Content-Type": "application/json",
    //             Token: token,
    //         },
    //     });

    //     let result = await Response.json();

    //     if (res.status == 200) {
    //         let data = await res.json();
    //         console.log(data);
    //         populateData(data);
    //     } else {
    //         console.log("Dogs are on the loose!");
    //     }
    }
async function setUpNewReservationPage() {
    // Getting user data
    let userData = getSessionUserData();

    // Getting all the dogs
    let dogJson = await getAllDogsByUsername(userData.email, userData.pswd);

    // Checking if dog data was retrieved
    if (dogJson == null) {
        // Failed to get dog information
        notInActiveSession();
    }

    // Populating dog dropdown
    populateData(dogJson);
}

/**
 * Populates the dog drop down with the given dog data
 * @param {JSON} data The data to populate the dog dropdown
 */
function populateData(data) {
    for (d of data) {
        // Status - Adding current status
        let selectElement = document.getElementById("inputDogName");
        let optionElement = document.createElement("option");
        optionElement.value = d.id;
        optionElement.innerHTML = d.dog_name;
        selectElement.append(optionElement);
    }
}

// Handles when the submit button is clicked
async function submitReservation() {
    // Init
    let userData = getSessionUserData();
    const url = `http://localhost:8080/reservations/${userData.email}`;

    // Validating reservation input
    if (!validateInput()) {
        // Not valid
        return;
    }

    // Getting Reservation input
    const resData = {
        userEmail: userData.email,
        dogId: document.getElementById("inputDogName").value,
        startDateTime: document.getElementById("inputStartDateTime").value,
        endDateTime: document.getElementById("inputEndDateTime").value,
        serviceId: document.getElementById("inputServiceId").value,
    };
    const resDataJson = JSON.stringify(resData);

    // Sending request
    let res = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Token: userData.pswd,
        },
        body: resDataJson,
    });

    // Getting response
    if (res.status == 200) {
        let data = await res.json();
        console.log(data);
    } else {
        console.log("Failed to create reservation, please try again");
    }
}

/*
 * Input Validation--
 * Does initial input checks to verify that users reservation input is correct.
 * If there are any error will also display them above their respective fields
 * @returns True if users reservation inputs are valid, and false otherwise.
 */
function validateInput() {
    // Init
    console.log("in validateINput");
    let success = true;

    // Getting inputs to validate
    let inputElements = document.getElementsByClassName("input");

    // Checking if required fields were filled out
    for (elem of inputElements) {
        // Checking if field was filled out
        if (elem.value === "") {
            // Required field was not filled in
            success = false;
            document.getElementById(`${elem.id}Error`).innerHTML = "This field is required";
        } else {
            document.getElementById(`${elem.id}Error`).innerHTML = "";
        }
    }
    return success;
}
