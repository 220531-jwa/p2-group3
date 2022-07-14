/*
 * === EVENT LISTENERS ===
 */

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