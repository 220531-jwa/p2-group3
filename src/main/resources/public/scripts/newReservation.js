/*
 * === EVENT LISTENERS ===
 */
/**
 * Handles when the submit button is clicked
 */
async function submitReservation() {
    // Init
    console.log("Submit button clicked")
    const url = "http://localhost:8080/index";
    // Validating reservation input
    if (!validateInput()) {
        // Not valid
        return;
    }

    // Getting Reservation input
    const resData = {
        dogId: document.getElementById("inputDogId").value,
        startDateTime: document.getElementById("inputStartDateTime").value,
        endDateTime: document.getElementById("inputEndDateTime").value,
        serviceId: document.getElementById("inputServiceId").value

    }
    const resDataJson = JSON.stringify(resData);

    // Sending response
    let result = await fetchPostRequest(url, resDataJson, true, true);
    console.log('got result');
    console.log(result);

    // Processing response
    if (result[0] === 200) {
        sessionStorage.resData = result[1];
        location.href = "../html/index.html";
    }
    else {
        document.getElementById("error").innerHTML = "Invalid Reservation Input";
    }
}

/*
 * === INPUT VALIDATION ===
 */

/**
 * Does initial input checks to verify that users reservation input is correct.
 * If there are any error will also display them above their respective fields
 * @returns True if users reservation inputs are valid, and false otherwise.
 */
function validateInput() {
    // Init
    console.log('in validateINput');
    let success = true;

    // Getting inputs to validate
    let inputElements = document.getElementsByClassName('input');

    // Checking if required fields were filled out
    for (elem of inputElements) {
        // Checking if field was filled out
        if (elem.value === "") {
            // Required field was not filled in
            success = false;
            document.getElementById(`${elem.id}Error`).innerHTML = "This field is required";
        }
        else {
            document.getElementById(`${elem.id}Error`).innerHTML = "";
        }
    }    
}