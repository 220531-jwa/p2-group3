/*
 * === INITIALIZATION ===
 */

/**
 * Loads the userProfile page into the login.html page
 */
window.onload = initalizeLoginPage();
async function initalizeLoginPage() {
    // Adding userProfile html into login page
    const userProfileHTML = await getHTMLPage("./userProfile.html");
    document.getElementById("userProfileContentPanel").innerHTML = userProfileHTML;

    // initialzing user profile page - with no user (new user)
    initalizeUserProfilePage(null);
    addUserProfileBackButtonListener('click', viewLogin);
}

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

/*
 * === View Updates ===
 */

/**
 * Displays the new user page
 */
function viewUserProfile() {
    // Showing user profile page and hiding login page
    document.getElementById("userProfileContentPanel").classList.remove("off");
    document.getElementById("loginform").classList.add("off");
}

/**
 * Displays the login page
 */
function viewLogin(){
    document.getElementById("loginform").classList.remove("off");
    document.getElementById("userProfileContentPanel").classList.add("off");
}