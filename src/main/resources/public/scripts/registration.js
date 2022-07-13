/*
 * === EVENT LISTENERS ===
 */

/**
 * Handles when the back button is clicked.
 * Redirects user back to login page
 */
function back() {
    notInActiveSession();
}

/**
 * Handles when the submit button is clicked
 */
async function submit() {
    // Validating user input
    if (!validateInput()) {
        // Not valid
        return;
    }

    // Getting user input
    const userData = {
        email: document.getElementById("inputEmail").value,
        pswd: document.getElementById("inputPassword").value,
        firstName: document.getElementById("inputFirstName").value,
        lastName: document.getElementById("inputLastName").value,
        phoneNumber: document.getElementById("inputPhoneNumber").value,
        funds: document.getElementById("inputFunds").value,
    }
    const userDataJson = JSON.stringify(userData);

    // Sending response
    let result = await fetchCreateNewUser(userDataJson);

    // Processing response
    if (result[0] === 200) {
        // User creation successful - saving session data and moving to home page
        inActiveSession(result[1]);
    }
    else {
        document.getElementById("error").innerHTML = "Invalid User Input";
    }
}

/*
 * === INPUT VALIDATION ===
 */

/**
 * Does initial input checks to verify that user input is correct.
 * If there are any error will also display them above their respective fields
 * @returns True if user inputs are valid, and false otherwise.
 */
function validateInput() {
    // Init
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

    // Checking if email is valid
    const validEmailRegex = new RegExp("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    const emailElement = document.getElementById('inputEmail');
    if (emailElement.value != "" && !validEmailRegex.test(emailElement.value)) {
        success = false;
        document.getElementById(`${emailElement.id}Error`).innerHTML = "Email Invalid";
    }

    // Checking if password is valid
    const validPasswordRegex = new RegExp("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^!&-+=()])(?=\\S+$).{8,}$");
    const passwordElement = document.getElementById('inputPassword');
    if (passwordElement.value != "" && !validPasswordRegex.test(passwordElement.value)) {
        success = false;
        document.getElementById(`${passwordElement.id}Error`).innerHTML = "Password must contain 8 characters with at least 1 lowercase letter, 1 upper case letter, 1 number, and 1 symbol";
    }

    // Checking if phone number is valid
    const phoneNumberElement = document.getElementById('inputPhoneNumber');
    let a = phoneNumberElement.value;
    if (phoneNumberElement.value != "" && phoneNumberElement.value.replace(/\D/g, '').length !== 10) {
        success = false;
        document.getElementById(`${phoneNumberElement.id}Error`).innerHTML = "Invalid phone number";
    }

    // Checking if funds are valid
    const fundsElement = document.getElementById('inputFunds');
    if (fundsElement.value != "" && fundsElement.value !== "" && fundsElement.value < fundsElement.min || fundsElement.value > fundsElement.max) {
        success = false;
        document.getElementById(`${fundsElement.id}Error`).innerHTML = `Invalid Amount. Must be between $${fundsElement.min} and $${fundsElement.max}`;
    }

    return success;
}