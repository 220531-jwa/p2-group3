

/**
 * Variables
 */
var createFormShowing = "fales";




/*
 * === EVENT LISTENERS ===
 */




/**
 * === ELEMENTS ===
 */


const createUserDiv = document.getElementById("createUserDiv");
const loginform = document.getElementById("loginform");


//Create User Profile Element

    var createUserProfileElements = ` <div class="container bg-light">
    <h1 class="mb-4 bg-dark">#</h1>
    <h1 id="title" class="mb-4">New User:</h1>
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
            <button class="btn btn-primary mb-4" type="button" onclick="backtolg()">Back</button>
        </div>
        <div class="col text-end">
            <button id="submitButton" class="btn btn-primary mb-4" type="button">Submit</button>
        </div>
    </div>
    <h1 class="mb-4 bg-dark">#</h1>
</div>`


/**
 * Send a login request to the server
 * If successful will create a login session
 * Otherwise will notify the user there is an error
 */
 async function login() {
    // Getting user input
    const email = document.getElementById("inputUsername").value;
    const pswd = document.getElementById("inputPassword").value;

    // Attempting to login user
    let result = await fetchLoginUser(email, pswd);

    // Processing response
    if (result[0] === 200) {

        // Login successful - saving session data and moving to home page
        sessionStorage.userData = JSON.stringify(result[1]);
        location.href = "../html/index.html";
    }
    else {
        // Login failed - displaying user error
        document.getElementById("error").innerHTML = "Invalid Username or Password";
    }
}

/**
 * Redirects user to registration page to create a new user.
 */
function register() {
    location.href = "../html/userProfile.html";
}

function setuplg(){
    setupTopNav("forTheTopDiv",userType);
    setupSideNav("forTheSideDiv",userType);
}

function newRegister(){
    createUserDiv.innerHTML = createUserProfileElements

    // hide the login form
    loginform.classList.toggle("off");
    // undhide the createUser form
    createUserDiv.classList.toggle("off");
}

function backtolg(){
    // unhide the login form
    loginform.classList.toggle("off");

    // hide the createUser form
    createUserDiv.classList.toggle("off");
}