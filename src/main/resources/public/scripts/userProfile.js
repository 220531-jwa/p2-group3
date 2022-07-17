/*
 * === INITIALIZATION ===
 */

/**
 * Initalizes the user profile page depending on the given email.
 * If null is passed will assume that a new user is being made
 * Otherwise will fetch and populated all fields with the given users data
 * @param {string} userEmail The user email to load the information of
 */
function initalizeUserProfilePage(userEmail) {
    // Checking if new user
    if (userEmail !== null) {
        // Loading existing user
        // Getting params - global
        username = userEmail;
        updateViewExistingUser();
    } else {
        // New user
        updateViewNewUser();
    }
}

/*
 * === View Updates ===
 */

/**
 * Updates the user profile page to handle an existing user
 * as well as setup html elements correctly
 */
async function updateViewExistingUser() {
    // Getting user data
    const userData = getSessionUserData();

    // Updating title
    document.getElementById('UPtitle').innerHTML = "Edit User:"

    // Checking who is attempting to view user information
    if (userData.email === username) {
        // User is viewing their own information
        // Updating button listener
        let submitBtn = document.getElementById('userProfileSubmitBtn');
        submitBtn.innerHTML = 'Save';
        submitBtn.addEventListener('click', saveUserProfile);
    }
    else if (userData.userType === 'OWNER') {
        // User is owner and viewing user information
        // Hiding save button
        document.getElementById('userProfileSubmitBtn').hidden = true;

        // Hiding user only elements
        let userElements = document.getElementsByClassName('user');
        for (elem of userElements) {
            elem.hidden = true;
        }

        // Disabling update elements
        let updateElements = document.getElementsByClassName('update');
        for (elem of updateElements) {
            elem.disabled = true;
        }
    }
    // else user not allowed -> fetch will cause 404

    // Getting input elements to disable
    let readOnlyElements = document.getElementsByClassName('readOnly');
    for (elem of readOnlyElements) {
        elem.disabled = true;
    }

    // Updating Labels
    document.getElementById('UPinputPasswordLabel').innerHTML = "New Password:"

    // Attempting to get user information associated with username
    const result = await fetchGetUserByUsername(username, userData.pswd);

    // Processing response
    if (result[0] === 200) {
        // Was able to get user information associated with username
        // Storing user information - global
        userDataJson = result[1];

        // Populating user information
        document.getElementById('UPinputEmail').value = userDataJson.email;
        document.getElementById('UPinputFirstName').value = userDataJson.firstName;
        document.getElementById('UPinputLastName').value = userDataJson.lastName;
        document.getElementById('UPinputPhoneNumber').value = formatPhoneNumber(userDataJson.phoneNumber);
        document.getElementById('UPinputFunds').value = userDataJson.funds;
    }
    else if (result[0] === 401) {
        // User not in active session
        notInActiveSession();
    }
    else {
        // User not found / or user isn't authorized to view information
        notFound404();
    }
}

/**
 * Updates the user profile page to handle a new user form
 * as well as setup html elements correctly
 */
async function updateViewNewUser() {
    // Updating button listener
    let submitBtn = document.getElementById('userProfileSubmitBtn');
    submitBtn.addEventListener('click', submitUserProfile);
}

/**
 * Hides or Shows the upper and lower bars
 * @param {boolean} upper Whether to hide the upper bar or not
 * @param {boolean} lower Whether to hide the bottom bar or not
 */
function setUserProfileBarVisibility(upper, lower) {
    document.getElementById('UPUpperBar').hidden = !upper;
    document.getElementById('UPLowerBar').hidden = !lower;
}

/**
 * Hides or shows the back button
 * @param {boolean} visible Whether the back button is visible or not
 */
function setUserProfileBackButtonVisibility(visible) {
    document.getElementById('userProfileBackBtn').hidden = !visible;
}

/*
 * === EVENT LISTENERS ===
 */

/**
 * Adds an event listener to the back button
 * @param {string} event The event type to look for
 * @param {function} callBackFunction The function to call when the event occurs
 */
function addUserProfileBackButtonListener(event, callBackFunction) {
    document.getElementById('userProfileBackBtn').addEventListener(event, callBackFunction);
}

/**
 * Handles when the submit button is clicked
 * Will send user data to server to create a new user
 * Validates user input before call
 */
async function submitUserProfile() {
    // Validating user input
    if (!validateInput()) {
        // Not valid
        return;
    }

    // Getting user input
    const newUserData = {
        email: document.getElementById("UPinputEmail").value,
        pswd: document.getElementById("UPinputPassword").value,
        firstName: document.getElementById("UPinputFirstName").value,
        lastName: document.getElementById("UPinputLastName").value,
        phoneNumber: document.getElementById("UPinputPhoneNumber").value,
        funds: document.getElementById("UPinputFunds").value,
    }
    const newUserDataJsonString = JSON.stringify(newUserData);

    // Sending response
    const result = await fetchCreateNewUser(newUserDataJsonString);

    // Processing response
    if (result[0] === 200) {
        // User creation successful - saving session data and moving to home page
        inActiveSession(result[1]);
    }
    else {
        document.getElementById("userProfileError").innerHTML = "Invalid User Input";
    }
}

/**
 * Handles when the save button is clicked
 * Will send user data to server to update the user
 * Validates user input before call
 */
async function saveUserProfile() {
    // Validating user inputs
    if (!validateUpdateInput()) {
        // Not valid
        return;
    }

    // Getting user updates
    let updatedUserData = {};
    let pswd = document.getElementById("UPinputPassword").value;
    let firstName = document.getElementById("UPinputFirstName").value;
    let lastName = document.getElementById("UPinputLastName").value;
    let phoneNumber = document.getElementById("UPinputPhoneNumber").value;
    if (pswd !== "") {updatedUserData.pswd = pswd;}
    if (firstName !== userDataJson.firstName) {updatedUserData.firstName = firstName;}
    if (lastName !== userDataJson.lastName) {updatedUserData.lastName = lastName;}
    if (phoneNumber !== userDataJson.phoneNumber) {updatedUserData.phoneNumber = phoneNumber;}
    const updatedUserDataJsonString = JSON.stringify(updatedUserData);

    // Sending response
    const userData = getSessionUserData();
    const result = await fetchUpdateUserByUsername(userDataJson.email, updatedUserDataJsonString, userData.pswd);

    // Processing response
    if (result[0] === 200) {
        // User update successful - Saving updated session data
        result[1].pswd = userData.pswd;
        inActiveSession(result[1]);
    }
    else {
        document.getElementById("userProfileError").innerHTML = "Failed to update user. Try again later.";
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
    const emailElement = document.getElementById('UPinputEmail');
    if (emailElement.value != "" && !validEmailRegex.test(emailElement.value)) {
        success = false;
        document.getElementById(`${emailElement.id}Error`).innerHTML = "Email Invalid";
    }

    // Checking if password is valid
    const passwordElement = document.getElementById('UPinputPassword');
    if (passwordElement.value != "" && !isValidPassword(passwordElement.value)) {
        success = false;
        document.getElementById(`${passwordElement.id}Error`).innerHTML = "Password must contain 8 characters with at least 1 lowercase letter, 1 upper case letter, 1 number, and 1 symbol";
    }

    // Checking if phone number is valid
    const phoneNumberElement = document.getElementById('UPinputPhoneNumber');
    if (phoneNumberElement.value != "" && !isValidPhoneNumber(phoneNumberElement.value)) {
        success = false;
        document.getElementById(`${phoneNumberElement.id}Error`).innerHTML = "Invalid phone number - Need 10 digits";
    }

    // Checking if funds are valid
    const fundsElement = document.getElementById('UPinputFunds');
    if (fundsElement.value != "" && fundsElement.value !== "" && fundsElement.value < fundsElement.min || fundsElement.value > fundsElement.max) {
        success = false;
        document.getElementById(`${fundsElement.id}Error`).innerHTML = `Invalid Amount. Must be between $${fundsElement.min} and $${fundsElement.max}`;
    }

    return success;
}

/**
 * Validates the user input for updating user information
 * At least one field needs to have been changed for true.
 * @returns True if user updates are valid and false otherwise
 */
function validateUpdateInput() {
    // Init
    let successes = [true, true, true, true];
    let changes = [false, false, false, false];

    // Getting updated inputs to validate
    let pswd = document.getElementById("UPinputPassword").value;
    let firstName = document.getElementById("UPinputFirstName").value;
    let lastName = document.getElementById("UPinputLastName").value;
    let phoneNumber = document.getElementById("UPinputPhoneNumber").value;

    // Checking if password was updated
    if (pswd !== "") {
        // Password Changed
        if (isValidPassword(pswd)) {
            // Password is valid
            successes[0] = true;
            changes[0] = true;
            document.getElementById(`UPinputPasswordError`).innerHTML = "";
        }
        else {
            // Password is invalid
            successes[0] = false;
            changes[0] = false;
            document.getElementById(`UPinputPasswordError`).innerHTML = "Password must contain 8 characters with at least 1 lowercase letter, 1 upper case letter, 1 number, and 1 symbol";
        }
    }
    else {
        // No Changes
        successes[0] = true;
        changes[0] = false;
        document.getElementById(`UPinputPasswordError`).innerHTML = "";}

    // Checking if firstname was updated
    if (firstName !== userDataJson.firstName) {
        // Firstname was changed
        if (firstName !== "") {
            // Valid firstname (not empty)
            successes[1] = true;
            changes[1] = true;
            document.getElementById(`UPinputFirstNameError`).innerHTML = "";
        }
        else {
            // Invalid firstname (is empty)
            successes[1] = false;
            changes[1] = false;
            document.getElementById(`UPinputFirstNameError`).innerHTML = "This field is required";
        }
    }
    else {
        // No changes
        successes[1] = true;
        changes[1] = false;
        document.getElementById(`UPinputFirstNameError`).innerHTML = "";
    }

    // Checking if lastname was updated
    if (lastName !== userDataJson.lastName) {
        // Lastname was changed
        if (lastName !== "") {
            // Valid lastname (not empty)
            successes[2] = true;
            changes[2] = true;
            document.getElementById(`UPinputLastNameError`).innerHTML = "";
        }
        else {
            // Invalid lastname (is empty)
            successes[2] = false;
            changes[2] = false;
            document.getElementById(`UPinputLastNameError`).innerHTML = "This field is required";
        }
    }
    else {
        // No changes
        successes[2] = true;
        changes[2] = false;
        document.getElementById(`UPinputLastNameError`).innerHTML = "";
    }

    // Checking if phoneNumber was updated
    if (formatPhoneNumber(phoneNumber) !== formatPhoneNumber(userDataJson.phoneNumber)) {
        // Lastname was changed
        if (isValidPhoneNumber(phoneNumber)) {
            // Valid phoneNumber (not empty)
            successes[3] = true;
            changes[3] = true;
            document.getElementById(`UPinputPhoneNumberError`).innerHTML = "";
        }
        else {
            // Invalid phoneNumber (is empty)
            successes[3] = false;
            changes[3] = false;
            document.getElementById(`UPinputPhoneNumberError`).innerHTML = "Invalid phone number - Need 10 digits";
        }
    }
    else {
        // No changes
        successes[3] = true;
        changes[3] = false;
        document.getElementById(`UPinputPhoneNumberError`).innerHTML = "";
    }

    // Checking if there were any changes
    let changed = false;
    for (c of changes) {
        if (c) {changed = true;}
    }

    // Checking if all inputs are valid
    let success = true;
    for (s of successes) {
        if (!s) {success = false;}
    }

    // Checking if there were any changes
    if (!changed && success) {
        document.getElementById('userProfileError').innerHTML = "Can't save with no changes. Make changes or click back";
    }
    else {
        document.getElementById('userProfileError').innerHTML = "";
    }

    return changed && success;
}

/**
 * Checks if the password is valid
 * @param {string} password The password to check
 * @returns True if the given password is correct and false otherwise
 */
function isValidPassword(password) {
    const validPasswordRegex = new RegExp("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^!&-+=()])(?=\\S+$).{8,}$");
    return validPasswordRegex.test(password);
}

/**
 * Checks if the phone number is valid
 * @param {string} phoneNumber The phone number to check
 * @returns True if the given phone number is correct and false otherwise
 */
function isValidPhoneNumber(phoneNumber) {
    return phoneNumber.replace(/\D/g, '').length === 10;
}

/*
 * === UTILITY ===
 */

/**
 * Returns a formatted version of the phone number in a xxx-xxx-xxxx format
 * @param {string} phoneNumber The phonenumber to format
 * @returns The formatted phone number, or itself if the phone number isn't valid
 */
function formatPhoneNumber(phoneNumber) {
    if (!isValidPhoneNumber(phoneNumber)) {return phoneNumber}
    let temp = phoneNumber.replace(/\D/g, '');
    return `${temp.substring(0,3)}-${temp.substring(3,6)}-${temp.substring(6,11)}`;
}

/**
 * Updates the user profile page for when a user wasn't found or was invalid.
 */
 function notFound404() {
    document.getElementById('UPtitle').innerHTML = "404 : Request Not Found";
    document.getElementById('userProfileForm').innerHTML = '';
    document.getElementById('userProfileSubmitBtn').hidden = true;
}