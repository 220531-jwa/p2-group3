/**
 * Send a login request to the server
 * If successful will create a login session
 * Otherwise will notify the user there is
 */
 async function login() {
    // Init
    const url = "http://localhost:8080/login";

    // Getting credentials
    const credentials = {
        email: document.getElementById("inputUsername").value,
        pswd: document.getElementById("inputPassword").value
    };
    const credentialsJson = JSON.stringify(credentials);

    // Sending response
    let result = await fetchPostRequest(url, credentialsJson, true, true);

    // Processing response
    if (result[0] === 200) {
        sessionStorage.userData = result[1];
        location.href = "../html/index.html";
    }
    else {
        document.getElementById("error").innerHTML = "Invalid Username or Password";
    }
}

/**
 * Redirects user to registration page to create a new user.
 */
function register() {

    location.href = "../html/registration.html";
}

    


