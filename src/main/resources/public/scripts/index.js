/*
 * === Index State Variables ===
 */

var reservation = {
    id: null,
    userEmail: null,
    dogId: null,
    status: null,
    startDateTime: null,
    endDateTime: null,
  };
  
var user = {
    email: null,
    pswd: null,
    userType: null,
    firstName: null,
    lastName: null,
    phoneNumber: null,
    funds: null,
  };

/*
 * === INITIALIZATION ===
 */

/**
 * Initializes the index page content
 */
   
window.addEventListener('load', initalizeIndexPage);
async function initalizeIndexPage() {
    // Getting user data (who is logged in)
    let userData = getSessionUserData();
     window.addEventListener('load', function () {
        setupIndex()
      })

    
    
    
    // var user = getSessionUserData();

    // Saving data to user state variable
    await updateIncomingUserIndex(userData);

    // Setting up nav bars
    setupTopNav("forTheTopDiv", user.userType);
    setupSideNav("forTheSideDiv", user.userType);

    // === Setting up content panels ===

    // Loading user profile into index page - and initalizing it
    // UserProfile page already handles whether the user is an owner or customer
    const userProfileHTML = await getHTMLPage('./userProfile.html');
    document.getElementById("userProfileContentPanel").innerHTML = userProfileHTML;
    initalizeUserProfilePage(user.email);
    setUserProfileBackButtonVisibility(false);
    setUserProfileBarVisibility(false, true);

    // Loading reservation into index page - and initalizing it
    if (user.userType === 'OWNER') {
        // User is owner
        // setupIndexPageReservations(indexReservationDiv, userType,seshToken)
        // indexReservationDiv.innerHTML = clientReservationsPage
        // const frag = document.createRange().createContextualFragment("../html/userProfile.html");
        // idexUpdateUserDiv.innerHTML = userProfileElements;
        setupReservations(user.pswd, user.userType);
        setupDogs(user.pswd,user.userType);
    }
    else {
        // User is customer
        setupReservations(userData.pswd, user.userType);
        setupDogs(userData.pswd,userData.userType);
    }
}

/**
 * Updates the user state variable (above)
 * @param incomingUser The user data of the current session
 */
async function updateIncomingUserIndex(incomingUser) {
    if (incomingUser) {
        Object.keys(incomingUser).forEach((key, index) => {
            user[key] = incomingUser[key];
        });
    }
}
    
  

function setupIndexPageReservations(elToAppendTo, userType, token) {
    // seshToken = token

    // async function setupIndex(){

        let userData = getSessionUserData();
        
        await updateIncomingUserIndex(userData);

        let userType = user.userType;
        let seshToken = user.pswd;

        // const ownePage = "../html/newReservation.html"
        // var docFrag = new DocumentFragment();
        let updateResCont = document.getElementById("updateResCont");
        const reservationsHTMLDoc = "../html/ReservationsPage.html"
        const reserVationResp =  await fetch(reservationsHTMLDoc);
        const ReservationHTML =  await reserVationResp.text();
        updateResCont.innerHTML = ReservationHTML;
    
        // let updateUserCont = document.getElementById("updateUserCont");
        // const updateuserHTMLAgainTest = "../html/userProfile.html"
        // const userResp =  fetch(updateuserHTMLAgainTest);
        // const userHTML =  userResp.text();
        // updateUserCont.innerHTML = userHTML;
        setupTopNav("forTheTopDiv",userType);
        setupSideNav("forTheSideDiv",userType);

        

        
        // SET UP 

        if(userType == "OWNER"){

            // let indexResSpot = document.getElementById("updateResCont")
            // indexResSpot.innerHTML = ReservationHTML;
            // updateUserCont.innerHTML = userHTML;
            // indexResSpot.innerHTML = await fetch(resHTMLAgainTest).then(await resp.text())


            
            // setupIndexPageReservations(indexReservationDiv, userType,seshToken)
            // indexReservationDiv.innerHTML = clientReservationsPage
            // const frag = document.createRange().createContextualFragment("../html/userProfile.html");
            // idexUpdateUserDiv.innerHTML = userProfileElements;
            
            setupUserProfile(user.email);
            setupReservations(seshToken,userType);
        }else if(userType=="CUSTOMER"){
            setupUserProfile(user.email);
            setupReservations(seshToken,userType);
        }

    if (userType == "OWNER") {
        // const frag = document.createRange().createContextualFragment("../html/ReservationsPage.html");
        // document.getElementById(elToAppendTo).load(reservationsHTML)
        // docFrag.appendChild(reservationsHTML)
        // indexReservationDiv.append(docFrag)
        // elToAppendTo.innerHTML = customerReservationsPage
    }
}

function navigatetoDogs() {
    location.href = "../html/DogsPage.html";
}

    

    function setupIndexPageReservations(elToAppendTo, userType, token){
        // seshToken = token
        
        if(userType=="OWNER"){
            // const frag = document.createRange().createContextualFragment("../html/ReservationsPage.html");
            // document.getElementById(elToAppendTo).load(reservationsHTML)  
            // docFrag.appendChild(reservationsHTML)
            
            // indexReservationDiv.append(docFrag)    
            // elToAppendTo.innerHTML = customerReservationsPage
        }
    }



    function navigatetoDogs(){
        location.href="../html/DogsPage.html"
    }



    
 

   
