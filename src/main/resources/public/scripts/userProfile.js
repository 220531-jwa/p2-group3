/*
 * === INITIALIZATION ===
 */

// window.onload = initalizePage();
// function initalizePage() {
//     // Getting query params
//     let query = window.location.search;
//     const params = new URLSearchParams(query);

//     // Checking if new page
//     if (params.has('username')) {
//         // Loading existing user
//         // Getting params - global
//         username = params.get('username');
//         updateViewExistingUser();
//     }
//     else {
//         // New user
//         updateViewNewUser();
//     }

// }


////TODO:might need to delete below

/**
 * === HTML PAGE ===
 */

 const idexUpdateUserDiv = document.getElementById("updateUserCont");
 const userProfileElements = `
 <div class="container bg-light" style="margin-top:3vh;">
    <div class="row"> 
        <div class="col-12">
            <h1 id="title" class="mb-4">New User:</h1>
        </div>
    </div>

 <div id="error" style="Color: red"></div>
 <form id="form">
     <hr>
     <p><b>User Information</b></p>
     <div class="form-group row mb-4">
         <div class="col">
             <div id="inputEmailError" style="color: red"></div>
             <lable style="display: block" for="inputEmail">Email:</lable>
             <input id="inputEmail" class="input readOnly" type="text">
         </div>
         <div class="col user">
             <div id="inputPasswordError" style="color: red"></div>
             <lable id="inputPasswordLabel" style="display: block" for="inputPassword">Password:</lable>
             <input id="inputPassword" class="input update" type="password">
         </div>
     </div>
     <div class="form-group row mb-4">
         <div class="col">
             <div id="inputFirstNameError" style="color: red"></div>
             <lable style="display: block" for="inputFirstName">First Name:</lable>
             <input id="inputFirstName" class="input update" type="text">
         </div>
         <div class="col">
             <div id="inputLastNameError" style="color: red"></div>
             <lable style="display: block" for="inputLastName">Last Name:</lable>
             <input id="inputLastName" class="input update" type="text">
         </div>
     </div>
     <div class="form-group row mb-4">
         <div class="col">
             <div id="inputPhoneNumberError" style="color: red"></div>
             <lable style="display: block" for="inputFirstName">Phone Number:</lable>
             <input id="inputPhoneNumber" class="input update" type="text">
         </div>
         <div class="col user">
             <div id="inputFundsError" style="color: red"></div>
             <lable style="display: block" for="inputFunds">Funds:</lable>
             <input id="inputFunds" type="number" class="input readOnly" min="0" max="9999.99" step="any" placeholder="$0.00">
         </div>
     </div>
 </form>
 <div class="row">
     <div class="col">
         <button class="btn btn-primary mb-4" type="button" onclick="back()">Back</button>
     </div>
     <div class="col text-end">
         <button id="submitButton" class="btn btn-primary mb-4" type="button">Submit</button>
     </div>
 </div>
 <h1 class="mb-4 bg-dark">#</h1>
 </div>`


 async function setupUserProfile(userName){

    let user= getSessionUserData();
    console.log(user.email)
    idexUpdateUserDiv.innerHTML = userProfileElements
    // username = params.get('username');
    updateViewExistingUserTryTwo(user.email);
    

    // let query = window.location.search;
    // const params = new URLSearchParams(query);

    // // Checking if new page
    // if (params.has('username')) {
    //     // Loading existing user
    //     // Getting params - global
    //     username = params.get('username');
    //     updateViewExistingUser();
    // }
    // else {
    //     // New user
    //     updateViewNewUser();
    // }
    
    

}








/**
 * === HTML UPDATES ===
 */


 async function updateViewExistingUserTryTwo(username) {
    // Getting user data
    const userData = getSessionUserData();
    console.log(username)

    // Updating title
    document.getElementById('title').innerHTML = "Welcome: " + username.toUpperCase();

    // Checking who is attempting to view user information
    if (userData.email === username) {
        // User is viewing their own information
        // Updating button listener
        let submitBtn = document.getElementById('submitButton');
        submitBtn.innerHTML = 'Save';
        submitBtn.addEventListener('click', save);
    }
    else if (userData.userType === 'OWNER') {
        // User is owner and viewing user information
        // Hiding save button
        document.getElementById('submitButton').hidden = true;

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
    document.getElementById('inputPasswordLabel').innerHTML = "New Password:"

    // Attempting to get user information associated with username
    const result = await fetchGetUserByUsername(userData.email, userData.pswd);

    // Processing response
    if (result[0] === 200) {
        // Was able to get user information associated with username
        // Storing user information - global
        userDataJson = result[1];

        // Populating user information
        document.getElementById('inputEmail').value = userDataJson.email;
        document.getElementById('inputFirstName').value = userDataJson.firstName;
        document.getElementById('inputLastName').value = userDataJson.lastName;
        document.getElementById('inputPhoneNumber').value = formatPhoneNumber(userDataJson.phoneNumber);
        document.getElementById('inputFunds').value = userDataJson.funds;
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
async function updateViewExistingUser() {
    // Getting user data
    const userData = getSessionUserData();

    // Updating title
    document.getElementById('title').innerHTML = "View User:"

    // Checking who is attempting to view user information
    if (userData.email === username) {
        // User is viewing their own information
        // Updating button listener
        let submitBtn = document.getElementById('submitButton');
        submitBtn.innerHTML = 'Save';
        submitBtn.addEventListener('click', save);
    }
    else if (userData.userType === 'OWNER') {
        // User is owner and viewing user information
        // Hiding save button
        document.getElementById('submitButton').hidden = true;

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
    document.getElementById('inputPasswordLabel').innerHTML = "New Password:"

    // Attempting to get user information associated with username
    const result = await fetchGetUserByUsername(username, userData.pswd);

    // Processing response
    if (result[0] === 200) {
        // Was able to get user information associated with username
        // Storing user information - global
        userDataJson = result[1];

        // Populating user information
        document.getElementById('inputEmail').value = userDataJson.email;
        document.getElementById('inputFirstName').value = userDataJson.firstName;
        document.getElementById('inputLastName').value = userDataJson.lastName;
        document.getElementById('inputPhoneNumber').value = formatPhoneNumber(userDataJson.phoneNumber);
        document.getElementById('inputFunds').value = userDataJson.funds;
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

async function updateViewNewUser() {
    // Updating button listener
    let submitBtn = document.getElementById('submitButton');
    submitBtn.addEventListener('click', submit);
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
 * Handles when the submit button is clicked
 * Will send user data to server to create a new user
 * Validates user input before call
 */
async function submit() {
    // Validating user input
    if (!validateInput()) {
        // Not valid
        return;
    }

    // Getting user input
    const newUserData = {
        email: document.getElementById("inputEmail").value,
        pswd: document.getElementById("inputPassword").value,
        firstName: document.getElementById("inputFirstName").value,
        lastName: document.getElementById("inputLastName").value,
        phoneNumber: document.getElementById("inputPhoneNumber").value,
        funds: document.getElementById("inputFunds").value,
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
        document.getElementById("error").innerHTML = "Invalid User Input";
    }
}

/**
 * Handles when the save button is clicked
 * Will send user data to server to update the user
 * Validates user input before call
 */
async function save() {
    // Validating user inputs
    if (!validateUpdateInput()) {
        // Not valid
        return;
    }

    // Getting user updates
    let updatedUserData = {};
    let pswd = document.getElementById("inputPassword").value;
    let firstName = document.getElementById("inputFirstName").value;
    let lastName = document.getElementById("inputLastName").value;
    let phoneNumber = document.getElementById("inputPhoneNumber").value;
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
        document.getElementById("error").innerHTML = "Failed to update user. Try again later.";
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
    if (passwordElement.value != "" && !isValidPassword(passwordElement.value)) {
        success = false;
        document.getElementById(`${passwordElement.id}Error`).innerHTML = "Password must contain 8 characters with at least 1 lowercase letter, 1 upper case letter, 1 number, and 1 symbol";
    }

    // Checking if phone number is valid
    const phoneNumberElement = document.getElementById('inputPhoneNumber');
    if (phoneNumberElement.value != "" && !isValidPhoneNumber(phoneNumberElement.value)) {
        success = false;
        document.getElementById(`${phoneNumberElement.id}Error`).innerHTML = "Invalid phone number - Need 10 digits";
    }

    // Checking if funds are valid
    const fundsElement = document.getElementById('inputFunds');
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
    let pswd = document.getElementById("inputPassword").value;
    let firstName = document.getElementById("inputFirstName").value;
    let lastName = document.getElementById("inputLastName").value;
    let phoneNumber = document.getElementById("inputPhoneNumber").value;

    // Checking if password was updated
    if (pswd !== "") {
        // Password Changed
        if (isValidPassword(pswd)) {
            // Password is valid
            successes[0] = true;
            changes[0] = true;
            document.getElementById(`inputPasswordError`).innerHTML = "";
        }
        else {
            // Password is invalid
            successes[0] = false;
            changes[0] = false;
            document.getElementById(`inputPasswordError`).innerHTML = "Password must contain 8 characters with at least 1 lowercase letter, 1 upper case letter, 1 number, and 1 symbol";
        }
    }
    else {
        // No Changes
        successes[0] = true;
        changes[0] = false;
        document.getElementById(`inputPasswordError`).innerHTML = "";}

    // Checking if firstname was updated
    if (firstName !== userDataJson.firstName) {
        // Firstname was changed
        if (firstName !== "") {
            // Valid firstname (not empty)
            successes[1] = true;
            changes[1] = true;
            document.getElementById(`inputFirstNameError`).innerHTML = "";
        }
        else {
            // Invalid firstname (is empty)
            successes[1] = false;
            changes[1] = false;
            document.getElementById(`inputFirstNameError`).innerHTML = "This field is required";
        }
    }
    else {
        // No changes
        successes[1] = true;
        changes[1] = false;
        document.getElementById(`inputFirstNameError`).innerHTML = "";
    }

    // Checking if lastname was updated
    if (lastName !== userDataJson.lastName) {
        // Lastname was changed
        if (lastName !== "") {
            // Valid lastname (not empty)
            successes[2] = true;
            changes[2] = true;
            document.getElementById(`inputLastNameError`).innerHTML = "";
        }
        else {
            // Invalid lastname (is empty)
            successes[2] = false;
            changes[2] = false;
            document.getElementById(`inputLastNameError`).innerHTML = "This field is required";
        }
    }
    else {
        // No changes
        successes[2] = true;
        changes[2] = false;
        document.getElementById(`inputLastNameError`).innerHTML = "";
    }

    // Checking if phoneNumber was updated
    if (formatPhoneNumber(phoneNumber) !== formatPhoneNumber(userDataJson.phoneNumber)) {
        // Lastname was changed
        if (isValidPhoneNumber(phoneNumber)) {
            // Valid phoneNumber (not empty)
            successes[3] = true;
            changes[3] = true;
            document.getElementById(`inputPhoneNumberError`).innerHTML = "";
        }
        else {
            // Invalid phoneNumber (is empty)
            successes[3] = false;
            changes[3] = false;
            document.getElementById(`inputPhoneNumberError`).innerHTML = "Invalid phone number - Need 10 digits";
        }
    }
    else {
        // No changes
        successes[3] = true;
        changes[3] = false;
        document.getElementById(`inputPhoneNumberError`).innerHTML = "";
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
        document.getElementById('error').innerHTML = "Can't save with no changes. Make changes or click back";
    }
    else {
        document.getElementById('error').innerHTML = "";
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
    document.getElementById('title').innerHTML = "404 : Request Not Found";
    document.getElementById('form').innerHTML = '';
    document.getElementById('btnSubmit').hidden = true;
}