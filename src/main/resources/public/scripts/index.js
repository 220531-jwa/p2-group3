

   
     window.addEventListener('load', function () {
        setupIndex()
      })
    
    
    // var user = getSessionUserData();


    var reservation = {
        id:null,
        userEmail:null,
        dogId:null,
        status:null,
        startDateTime:null,
        endDateTime:null
        
        
    }


    var user ={
        email:null,
        pswd:null,
        userType:null,
        firstName:null,
        lastName:null,
        phoneNumber:null,
        funds:null,
    }



    async function updateIncomingUserIndex(incomingUser){

        if(incomingUser){
            Object.keys(incomingUser).forEach((key, index) => {
        
                    user[key] = incomingUser[key];
                
            });
    
        }
    
    }
    
    const ownePage = "../html/newReservation.html"
    var docFrag = new DocumentFragment();
    const reservationsHTML = "../html/ReservationsPage.html"
     


       
    

    async function setupIndex(){

        let userData = getSessionUserData();
        
        await updateIncomingUserIndex(userData);

        let userType = user.userType;
        let seshToken = user.pswd;

        setupTopNav("forTheTopDiv",userType);
        setupSideNav("forTheSideDiv",userType);

        console.log(user.email)
        // SET UP 

        if(userType == "OWNER"){
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

   