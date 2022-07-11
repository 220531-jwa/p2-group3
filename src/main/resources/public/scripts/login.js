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

    // Sending request
    let response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: credentialsJson
    });
    
    // Processing response
    if (response.status === 200) {
        let userData = await response.json();
        sessionStorage.userData = JSON.stringify(userData);
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
    // TODO: Redirect to registration page
    console.log("Registration Button Clicked");
}