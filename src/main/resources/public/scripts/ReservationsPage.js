
/**
 * VARIABLES
 */


const userRes = getSessionUserData();
const seshToken = "";



var allReservations = [];
var allSessionCustomerResVations = [];
var openEL = "allReservationsTableRow";
var openTopEl = "viewReservationsDiv"
var isEditOpen = "false"
var allowEdit = false


var reservation = {
    id:null,
    userEmail:null,
    dogId:null,
    status:null,
    startDateTime:null,
    endDateTime:null
}

// THIS UPDATED THE ABOVE OBJECT LITERAL
async function updateIncomingReservationPage(incomingReservation){

    if(incomingReservation){
        Object.keys(incomingReservation).forEach((key, index) => {
    
            if(key == "startDateTime" ){
                
                let incomingDate = incomingReservation[key];
                let newDate = new Date(incomingDate);
                let day = newDate.getDate();
                let month = newDate.getMonth() + 1;
                let year = newDate.getFullYear();
                let hour = newDate.getHours();
                let minutes = newDate.getMinutes().toString();
                let seconds = newDate.getSeconds().toString();

                if(minutes.length==1){
                    minutes = minutes +"0"
                }


                
                if(seconds.length==1){
                    seconds = seconds + "0"
                    
                }
    
                let theDate = month + "/" + day + "/" + year
                let theTime = hour + ":" + minutes + ":" + seconds

                let fullDate = theDate + " " + theTime
    
                
    
                reservation.startDateTime = fullDate;
    
    
            }else if(key=="endDateTime"){
    
                let incomingDate = incomingReservation[key];
                let newDate = new Date(incomingDate);
                let day = newDate.getDate();
                let month = newDate.getMonth() + 1;
                let year = newDate.getFullYear();
                let hour = newDate.getHours();
                let minutes = newDate.getMinutes().toString();
                let seconds = newDate.getSeconds().toString();

                if(minutes.length==1){
                    minutes = minutes +"0"
                }


                
                if(seconds.length==1){
                    seconds = seconds + "0"
                   
                }
    
                let theDate = month + "/" + day + "/" + year
                let theTime = hour + ":" + minutes + ":" + seconds

                let fullDate = theDate + " " + theTime
    
                reservation.endDateTime = fullDate;
    
            }else{
                
                reservation[key] = incomingReservation[key];
            }
        });

    }

}




/**
 *  HTML PAGES TURNED INTO JAVASCRIPT
 */

 // THIS IS WHERE WE ARE GOING TO "RENDER" OUR HTML AFTER FIGURING OUT USERTYPE LOGGED IN
 const ownerEditReservation =
 `<div class="container bg-light">
 <h1 id="ERUpperBar" class="mb-4 bg-dark">#</h1>
 <h1 id="title" class="mb-4">Edit Reservation:</h1>
 <div id="editReservationError" style="Color: red"></div>
 <form id="editReservationForm">
     <hr>
     <p><b>User Information</b></p>
     <div class="form-group row mb-4">
         <div id="reserveeField" class="col">
             <lable for="reservee">Reservee:</lable>
             <a id="reservee" href="#"></a>
         </div>
         <div class="col">
             <lable for="dog">Dog:</lable>
             <a id="dog" href="#"></a>
         </div>
     </div>
     <hr>
     <p><b>Registration Information</b></p>
     <div class="form-group row mb-4">
         <div class="col">
             <label style="display: block" for="updateStatus">Status:</label>
             <select id="updateStatus"></select>
         </div>
         <div class="col">
             <label style="display: block" for="service">Service:</label>
             <select id="service" disabled></select>
         </div>
     </div>
     <div class="form-group row mb-4 readOnly">
         <div class="col">
             <label style="display: block" for="startDate">Start Date</label>
             <input id="startDate" type="date" disabled>
         </div>
         <div class="col">
             <label style="display: block" for="startTime">Start Time</label>
             <input id="startTime" type="time" disabled>
         </div>
     </div>
     <div class="form-group row mb-4 readOnly">
         <div class="col">
             <label style="display: block" for="endDate">End Date</label>
             <input id="endDate" type="date" disabled>
         </div>
         <div class="col">
             <label style="display: block" for="endTime">End Time</label>
             <input id="endTime" type="time" disabled>
         </div>
     </div>
 </form>
 <div class="row">
     <div class="col">
         <button id="editReservationBackBtn" class="btn btn-primary mb-4" type="button">Back</button>
     </div>
     <div class="col text-end">
         <button id="editReservationSaveBtn" class="btn btn-primary mb-4" type="button" onclick="save()">Save</button>
     </div>
 </div>
 <h1 id="ERLowerBar" class="mb-4 bg-dark">#</h1>
</div>`;

 const createReservationOwner = `<div class="container bg-light">
 <h1 class="mb-4 bg-dark">#</h1>
 <h1 id="title" class="mb-4">New Reservation:</h1>
 <div id="error" style="Color: red"></div>
 <form id="form">
     <hr>
     <p><b>New Reservation Information</b></p>
     <div class="form-group row mb-4">
         <div class="col">
             <div id="inputDogNameError" style="color: red"></div>
             <label style="display: block" for="inputDogName">Enter your dog's name for the Reservation:</label>
             <select id="inputDogName" type="inputDogName">
                 <option id="dogOptions" selected ="selected" disabled>Select your dog</option>
                 <option id="dog1" value="5">Ribena</option>
                 <option id="dog2" value="6">Riddick</option>
             </select>
         </div>
     </div>
     <div class="form-group row mb-4">
         <div class="col">
             <div id="inputStartDateTimeError" style="color: red"></div>
             <label style="display: block" for="inputStartDateTime">Check-in:</label>
             <input id="inputStartDateTime" class="input" type="datetime-local">
         </div>
         <div class="col">
             <div id="inputEndDateTimeError" style="color: red"></div>
             <label style="display: block" for="inputEndDateTime">Check-out:</label>
             <input id="inputEndDateTime" class="input" type="datetime-local">
         </div>
     </div>
     <div class="form-group row mb-4">
         <div class="dropdown">
             <div id="inputServiceIdError" style="color: red"></div>
             <label style="display: block" for="inputServiceId">Additional Service (optional)</label>
             <select name="inputServiceId" id="inputServiceId">
                 <option value="null">Select an Option</option>>
                 <option id="groomingInput" value="2">Grooming - $25.00</option>
                 <option id="bellyrubInput" value="3">Belly Rub - $0.99</option>
                 <option id="dogwalkInput" value="4">Walk - $15.00</option>
                 <option id="nailtrimInput" value="5">Nail Trimming - $19.99</option>
             </select>
         </div>
     </div>
 </form>
 <div class="row">
     <div class="col text-end">
         <button class="btn btn-primary mb-4" type="button" onclick="submitReservation()">Submit</button>
     </div>
 </div>
 <h1 class="mb-4 bg-dark">#</h1>
</div>`


 const ownerReservationsPage = ` <div id="main">

 <div id="topLvlButtsCont" class="container">
     <div class="row">
         <div class="col-6 col-sm-6">
             <butoon id="viewReservationsDiv_butt" type="button" class="btn btn-primary" onclick="topLvlButtonsHandler(event.target)">View All Your Reservations</butoon>
         </div>
         <div class="col-6 col-sm-6">
             <butoon id="createNewReservationDiv_butt" type="button" class="btn btn-primary" onclick="topLvlButtonsHandler(event.target)">Book New Reservation</butoon>
         </div>
     </div>

 </div>


 <div id="allCurrentReservationSide" class="container">
     
     <div id="testLvlButtsCont" class="container" >
         <div class="row">
             <div class="col-4 col-sm-4">
                 <button id="allReservationsTableRow_butt" type="button" class="btn btn-primary" onclick="testLvlButtonsHandler(event.target)">test get all reservations</button>
             </div>
             <div class="col-4 col-sm-4">
                 <button id="allReservationsByUserNameTableRow_butt" type="button" class="btn btn-primary" onclick="testLvlButtonsHandler(event.target)">test get reservation by username</button>
             </div>
             <div class="col-4 col-sm-4">
                 <button id="getReservationByIdRow_butt" type="button" class="btn btn-primary" onclick="testLvlButtonsHandler(event.target)">test get reservation by id</button>
             </div>
         </div>
     </div>

     <div id="viewReservationsDiv" class="container">

         <div id="allReservationsTableRow" class="row tableHolder">
             <div class="row">
                 <div class="col-12 col-sm-12" style="margin-bottom: 3vh;">
                     <h4 style="text-align:center; width: 100%;">All Reservations</h4>
                 </div>
             </div>
             
             <div id="allReservationsTableCol" class="col-12 col-sm-12">
             </div>
         </div>

         <div id="allReservationsByUserNameTableRow" class="row tableHolder off">
             <div class="row">
                 <div class="col-12 col-sm-12" >
                     <h4 style="text-align:center; width: 100%;">All By User Name</h4>

                 </div>
             </div>
             <div id="allReservationsByUserNameTableCol" class="col-12 col-sm-12">
             </div>
         </div>

         <div id="getReservationByIdRow" class="row tableHolder off">
             <div id="getReservationByIdTop" class="col-12 col-sm-12">
                 <div class="row">
                     <div class="col-12 col-sm-12" >
                         <h4 style="text-align:center; width: 100%;">Get Reservation By ID</h4>
 
                     </div>
                 </div>

                 <div class="row">
                     <div class="col-2 col-sm-2" style="padding:4vh">
                         <label for="req_id_box" style="float:right">Enter the Id of your request</label>
                     </div>
                     <div class="col-2 col-sm-2" style="padding:4vh">
                         <input id="req_id_box" type="number">
                     </div>
                     <div class="col-2 col-sm-2" style="padding:4vh">
                         <button id="getreqbutt" class="btn btn-primary" type="button" style="float:left" onclick="setUpReservationById()">Get Request</button>
                     </div>
                     <div class="col-6 col-sm-6" style="padding:4vh">
                         <!-- <button id="getreqbutt" class="btn btn-primary" type="button" style="float:left">Get Request</button> -->
                     </div>

                 </div>
                 <hr>

                 <div class="row">
                     <div id="getReservationByIdTableCol" class="col-12 col-sm-12">
                         <table class="table">
                             <tr>
                                 <thead>
                                     <th scope="col">Hi there</th>
                                     <th scope="col">yello</th>
                                 </thead>
                             </tr>
                         </table>
                         
                     </div>
                 </div>
             </div>
         </div>


         <div id="editReservationRow" class="row tableHolder off">
             <div id="editReservationCol" class="col-12 col-sm-12" style="margin-bottom: 3vh;">

               
                     ${ownerEditReservation}


             </div>
         </div>


     </div>

 </div>


 <div id="createNewReservationDiv" class="container off">
 <div class="row">
     <div class="col-12 col-sm-12">
         <h5>Create Reservation</h5> -->
         <!--delete this below paragraph before pushing!-->
         <p>
             <a href="../html/newReservation.html">click here to go to new reservation</a>
         </p>
     </div>
 </div>
 <div class="row">
     <div id="createResCol" class="col-12 col-sm-12">
         ${createReservationOwner}
     </div>
 </div>

 
</div>

 
 
</div>`


const customerReservationsPage = `<div id="main">

<div id="topLvlButtsCont" class="container">
    <div class="row">
        <div class="col-6 col-sm-6">
            <butoon id="viewReservationsDiv_butt" type="button" class="btn btn-primary" onclick="topLvlButtonsHandler(event.target)">View All Your Reservations</butoon>
        </div>
        <div class="col-6 col-sm-6">
            <butoon id="createNewReservationDiv_butt" type="button" class="btn btn-primary" onclick="topLvlButtonsHandler(event.target)">Book New Reservation</butoon>
        </div>
    </div>

</div>


<div id="allCurrentReservationSide" class="container">
    
    <div id="testLvlButtsCont" class="container" >
        <div class="row">
            <div class="col-4 col-sm-4">
                <button id="allReservationsTableRow_butt" type="button" class="btn btn-primary" onclick="testLvlButtonsHandler(event.target)">test get all reservations</button>
            </div>
            <div class="col-4 col-sm-4">
                <button id="allReservationsByUserNameTableRow_butt" type="button" class="btn btn-primary" onclick="testLvlButtonsHandler(event.target)">test get reservation by username</button>
            </div>
            <div class="col-4 col-sm-4">
                <button id="getReservationByIdRow_butt" type="button" class="btn btn-primary" onclick="testLvlButtonsHandler(event.target)">test get reservation by id</button>
            </div>
        </div>
    </div>

    <div id="viewReservationsDiv" class="container">

        <div id="allReservationsTableRow" class="row tableHolder">
            <div class="row">
                <div class="col-12 col-sm-12" style="margin-bottom: 3vh;">
                    <h4 style="text-align:center; width: 100%;">All Reservations</h4>
                </div>
            </div>
            
            <div id="allReservationsTableCol" class="col-12 col-sm-12">
            </div>
        </div>

        <div id="allReservationsByUserNameTableRow" class="row tableHolder off">
            <div class="row">
                <div class="col-12 col-sm-12" >
                    <h4 style="text-align:center; width: 100%;">All By User Name</h4>

                </div>
            </div>
            <div id="allReservationsByUserNameTableCol" class="col-12 col-sm-12">
            </div>
        </div>

        <div id="getReservationByIdRow" class="row tableHolder off">
            <div id="getReservationByIdTop" class="col-12 col-sm-12">
                <div class="row">
                    <div class="col-12 col-sm-12" >
                        <h4 style="text-align:center; width: 100%;">Get Reservation By ID</h4>

                    </div>
                </div>

                <div class="row">
                    <div class="col-2 col-sm-2" style="padding:4vh">
                        <label for="req_id_box" style="float:right">Enter the Id of your request</label>
                    </div>
                    <div class="col-2 col-sm-2" style="padding:4vh">
                        <input id="req_id_box" type="number">
                    </div>
                    <div class="col-2 col-sm-2" style="padding:4vh">
                        <button id="getreqbutt" class="btn btn-primary" type="button" style="float:left" onclick="setUpReservationById()">Get Request</button>
                    </div>
                    <div class="col-6 col-sm-6" style="padding:4vh">
                        <!-- <button id="getreqbutt" class="btn btn-primary" type="button" style="float:left">Get Request</button> -->
                    </div>

                </div>
                <hr>

                <div class="row">
                    <div id="getReservationByIdTableCol" class="col-12 col-sm-12">
                        <table class="table">
                            <tr>
                                <thead>
                                    <th scope="col">Hi there</th>
                                    <th scope="col">yello</th>
                                </thead>
                            </tr>
                        </table>
                        
                    </div>
                </div>
            </div>
        </div>


        <div id="editReservationRow" class="row tableHolder off">
            <div id="editReservationCol" class="col-12 col-sm-12" style="margin-bottom: 3vh;">

              
                    <h1 id="title" class="mb-4">Edit Reservation:</h1>
                    <div id="error" style="Color: red"></div>
                    <!-- <form id="form">
                        <hr>
                        <p><b>User Information</b></p>
                        <div class="form-group row mb-4">
                            <div id="emailField" class="col">
                                <lable for="email">Email:</lable>
                                <a id="email" href="#"></a>
                            </div>
                            <div class="col">
                                <lable for="dog">Dog:</lable>
                                <a id="dog" href="#"></a>
                            </div>
                        </div>
                        <hr>
                        <p><b>Registration Information</b></p>
                        <div class="form-group row mb-4">
                            <div class="col">
                                <label style="display: block" for="updateStatus">Status:</label>
                                <select id="updateStatus"></select>
                            </div>
                            <div class="col">
                                <label style="display: block" for="service">Service:</label>
                                <select id="service" disabled></select>
                            </div>
                        </div>
                        <div class="form-group row mb-4 readOnly">
                            <div class="col">
                                <label style="display: block" for="startDate">Start Date</label>
                                <input id="startDate" type="date" disabled>
                            </div>
                            <div class="col">
                                <label style="display: block" for="startTime">Start Time</label>
                                <input id="startTime" type="time" disabled>
                            </div>
                        </div>
                        <div class="form-group row mb-4 readOnly">
                            <div class="col">
                                <label style="display: block" for="endDate">End Date</label>
                                <input id="endDate" type="date" disabled>
                            </div>
                            <div class="col">
                                <label style="display: block" for="endTime">End Time</label>
                                <input id="endTime" type="time" disabled>
                            </div>
                        </div>
                    </form>
                    <div class="row">
                        <div class="col">
                            <button class="btn btn-primary mb-4" type="button" onclick="openEditReservation()">Back</button>
                        </div>
                        <div class="col text-end">
                            <button id="saveBtn" class="btn btn-primary mb-4" type="button" onclick="save()">Save</button>
                        </div>
                    </div>
                    <h1 class="mb-4 bg-dark">#</h1>
                </div> --> -->
                <div class="container bg-light">
                    <h1 class="mb-4 bg-dark">#</h1>
                    <h1 id="title" class="mb-4">Edit Reservation:</h1>
                    <div id="error" style="Color: red"></div>
                    <form id="form">
                        <hr>
                        <p><b>User Information</b></p>
                        <div class="form-group row mb-4">
                            <div id="reserveeField" class="col-6">
                                <lable for="reservee">Reservee:</lable>
                                <a id="reservee" href="#"></a>
                            </div>
                            <div class="col-6">
                                <lable for="dog">Dog:</lable>
                                <a id="dog" href="#"></a>
                            </div>
                        </div>
                        <hr>
                        <p><b>Registration Information</b></p>
                        <div class="form-group row mb-4">
                            <div class="col-6 col-sm-6">
                                <label for="updateStatus">Status:</label>
                                <select id="updateStatus">
                                    <option value="REGISTERED">REGISTERED</option>
                                    <option value="CHECKEDIN">CHECKEDIN</option>
                                    <option value="CHECKEDOUT">CHECKEDOUT</option>
                                    <option value="CANCELLED">CANCELLED</option>
                                </select>
                            </div>
                            <div class="col-6 col-sm-6">
                                <label style="display: block" for="service">Service:</label>
                                <select id="service" disabled></select>
                            </div>
                        </div>
                        <div class="form-group row mb-4 readOnly">
                            <div class="col-6 col-sm-6">
                                <span>
                                    <label style="display: block" for="startDate">Start Date</label>
                                    <input id="startDate" type="date" disabled>
                                </span>
                            </div>
                            <div class="col-6 col-sm-6">
                                <label style="display: block" for="startTime">Start Time</label>
                                <input id="startTime" type="time" disabled>
                            </div>
                        </div>
                        <div class="form-group row mb-4 readOnly">
                            <div class="col-6 col-sm-6">
                                <label style="display: block" for="endDate">End Date</label>
                                <input id="endDate" type="date" disabled>
                            </div>
                            <div class="col-6 col-sm-6">
                                <label for="endTime">End Time</label>
                                <input id="endTime" type="time" disabled>
                            </div>
                        </div>
                    </form>
                    <div class="row">
                        <div class="col-3 col-sm-3">
                            <button class="btn btn-primary mb-4" type="button" onclick="openEditReservation()">Back</button>
                        </div>
                        <div class="col-3 col-sm-3 text-end">
                            <button id="editBtn" class="btn btn-primary mb-4" type="button" onclick="allowEditHandler()">Edit</button>
                        </div>
                        <div class="col-3 col-sm-3 text-end">
                            <button id="saveBtn" class="btn btn-primary mb-4" type="button" onclick="save()">Save</button>
                        </div>
                    </div>
                    <h1 class="mb-4 bg-dark">#</h1>
                </div>


            </div>
        </div>


    </div>

</div>


<div id="createNewReservationDiv" class="container off">
    <div class="row">
        <div class="col-12 col-sm-12">
            <h5>Create Reservation</h5> -->
            <!--delete this below paragraph before pushing!-->
            <p>
                <a href="../html/newReservation.html">click here to go to new reservation</a>
            </p>
        </div>
    </div>

    
</div>



</div>`


 async function setupReservations(seshToken,userType){


    const indexReservationDiv = document.getElementById("updateResCont");

    if(userType === "CUSTOMER"){
        
        indexReservationDiv.innerHTML = customerReservationsPage;
        setUpAllCustomerReservations(seshToken,userType);


    }else if(userType==="OWNER"){
        indexReservationDiv.innerHTML =ownerReservationsPage;
        setUpAllReservations(seshToken,userType);
    }

    // setupTopNav("forTheTopDiv",userType);
    // setupSideNav("forTheSideDiv",userType);

    // CUSTOMER SIDE
    
    
    // OWNER SIDE

}

  

// async function updateIncomingReservation(incomingReservation){

//     Object.keys(incomingReservation).forEach((key, index) => {
//         reservation[key] = incomingReservation[key];
//     });

// }

// const indexReservationDiv = document.getElementById("updateResCont");




function topLvlButtonsHandler(e){

    let elid = event.target.id;

    elid = elid.replace("_butt","");
    let viewReservationsDiv = document.getElementById("viewReservationsDiv");
    let createNewReservationDiv = document.getElementById("createNewReservationDiv");
    let allCurrentRes = document.getElementById("allCurrentReservationSide")

    if(openTopEl=="viewReservationsDiv" && elid == "viewReservationsDiv"){
        viewReservationsDiv.classList.toggle("off");
       

        

    }else if(openTopEl=="createNewReservationDiv" && elid == "createNewReservationDiv"){

    }else if(openTopEl == "createNewReservationDiv" && elid == "viewReservationsDiv"){
        
        createNewReservationDiv.classList.toggle("off");
        allCurrentRes.classList.toggle("off");

    }else if(openTopEl == "createNewReservationDiv" && elid == "viewReservationsDiv"){
        
        createNewReservationDiv.classList.toggle("off");
        allCurrentRes.classList.toggle("off");

    }else if(openTopEl == "viewReservationsDiv" && elid == "createNewReservationDiv"){
        
        createNewReservationDiv.classList.toggle("off");
        allCurrentRes.classList.toggle("off");
    }
    
    let eltochange = document.getElementById(elid);
    
   
    // eltochange.classList.toggle("off")
    openTopEl = elid;

}


function testLvlButtonsHandler(e){

    let elid = event.target.id;

    elid = elid.replace("_butt","");


    
    let allReservationsTableCol = document.getElementById("allReservationsTableRow");
    let allReservationsByUserNameTableRow = document.getElementById("allReservationsByUserNameTableRow");
    let allReservationsByUserNameTableCol = document.getElementById("getReservationByIdRow");


    if(openEL == "allReservationsTableRow"){
        allReservationsTableCol.classList.toggle("off");

    }else if(openEL == "allReservationsByUserNameTableRow"){
        allReservationsByUserNameTableRow.classList.toggle("off");
    }else if(openEL == "getReservationByIdRow"){
        allReservationsByUserNameTableCol.classList.toggle("off");
    }


    let eltochange = document.getElementById(elid);
    
    eltochange.classList.toggle("off")
    openEL = elid;
   

}



async function setUpAllReservations(seshToken){

    let allReservationsTableCol = document.getElementById("allReservationsTableCol");
    
    // let seshToken = userRes.pswrd
    
    
    // let username = userRes.email;

    // PULL IN ALL RESERVATIONS
    let reservations = await getAllReservations(seshToken);

    allReservations = reservations

    // Passing to create table function to create the table append to appropriate place.
    createTableData(allReservationsTableCol,"multiple");


}


async function setUpAllCustomerReservations(seshToken,username){

    let allReservationsByUserNameTableCol = document.getElementById("allReservationsByUserNameTableCol");
    
    // let seshToken = userRes.pswrd;
    // let username = userRes.email;


    // PULL IN ALL RESERVATIONS
    let allSessionCustomerResVations = await getAllRservationsByUsername(username, seshToken);

    // SETTING MAIN VARIABLE TO THE ARRAY OF RESERVATIONS
    allReservations = allSessionCustomerResVations

    // Passing to create table function to create the table append to appropriate place.
    createTableData(allReservationsByUserNameTableCol,"multiple");


}




async function setUpReservationById(seshToken,username){

    let allReservationsByUserNameTableCol = document.getElementById("getReservationByIdTableCol");
    let res_id = document.getElementById("req_id_box").value
    
    // PULL IN ALL RESERVATIONS
    let requestedReservation = await fetchGetReservationById(username,res_id, seshToken);

    await updateIncomingReservationPage(requestedReservation);
    

    let tbl = document.createElement("table");
    let tblHead = document.createElement("thead");
    let tblHdrRow = document.createElement("tr");

    tbl.className="table";


    let newHdr = ""
    let r = 0;

    //  Appending Table Headers to Table.
    for(reservProp in reservation){
        let tblHdr = document.createElement("th")
        let reservPropStrng = reservProp.toString();

        newHdr = reservPropStrng
        tblHdr.scope = "col"
        tblHdr.innerText = newHdr
        tblHdrRow.append(tblHdr);

        

        if(r===5){
            let editHdr = document.createElement("th");
            editHdr.scope="col";
            editHdr.innerText = "Edit";
            tblHdrRow.append(editHdr);
        }

        r++
    
    }

    
    
    tblHead.append(tblHdrRow)


    //CREATING TABLE BODY
    let tbleBody = document.createElement("tbody");
    let reqRow = document.createElement("tr");
    let reqTdID = document.createElement("td");
    let reqTduserEmail = document.createElement("td");
    let reqTddogId = document.createElement("td");
    let reqTdstatus = document.createElement("td");
    let reqTdstartDateTime = document.createElement("td");
    let reqTdendDateTime = document.createElement("td");
    let buttTd = document.createElement("td");
    let buttn = document.createElement("button");

    // ASSIGNING ATTRIBUTES TO BUTTON
    buttn.type = "button";
    buttn.className = "btn editButt";
    buttn.innerText = "Edit";
    buttTd.append(buttn);
       
    

    reqTdID.innerText = reservation.id;
    reqTduserEmail.innerText = reservation.userEmail;
    reqTddogId.innerText = reservation.dogId;
    reqTdstatus.innerText = reservation.status;
    reqTdstartDateTime.innerText = reservation.startDateTime;
    reqTdendDateTime.innerText = reservation.endDateTime;

    reqRow.append(reqTdID);
    reqRow.append(reqTduserEmail);
    reqRow.append(reqTddogId);
    reqRow.append(reqTdstatus);
    reqRow.append(reqTdstartDateTime);
    reqRow.append(reqTdendDateTime);
    reqRow.append(buttTd);

    tbleBody.append(reqRow)
    tbl.append(tblHead)
    tbl.append(tbleBody);
    allReservationsByUserNameTableCol.append(tbl)

}





async function createTableData(divToAppendTo, singleOrMultiple){
    
    let tbl = document.createElement("table");
    let tblHead = document.createElement("thead");
    let tblHdrRow = document.createElement("tr");

    tbl.className="table";


    let newHdr = ""

    let allResLength = await allReservations.length;
    
    if(allResLength > 0){

        let r = 0;
         //  Appending Table Headers to Table.
            for(reservProp in allReservations[0]){
                
                let tblHdr = document.createElement("th")
                let reservPropStrng = reservProp.toString();

                newHdr = reservPropStrng
                tblHdr.scope = "col"
                tblHdr.innerText = newHdr
                tblHdrRow.append(tblHdr);

                if(r===5){
                    let editHdr = document.createElement("th");
                    editHdr.scope="col";
                    editHdr.innerText = "Edit";
                    tblHdrRow.append(editHdr);
                }

                r++
            
            }
    }
    tblHead.append(tblHdrRow)

    let tbleBody = document.createElement("tbody");


    for(x = 0; x<=allReservations.length - 1; x++){
        
        let reqRow = document.createElement("tr");
        w = 0;
        
        
        await updateIncomingReservationPage(allReservations[x]);

        // for (key in allSessionCustomerResVations[x]) {
        
        for (key in reservation) {
            
            
            let reqTd = document.createElement("td")
              
            reqTd.innerText = reservation[key];
            reqRow.append(reqTd);

            if(w===5){

                let buttTd = document.createElement("td")
                let buttn = document.createElement("button");
                buttn.type="button";
                buttn.className="btn editButt"
                buttn.id=reservation.id + "_edit"
                buttn.onclick =(e)=>{
                    let reserv_id = e.target.id ;
                    reserv_id = reserv_id.replace("_edit","")
                    openEditReservation()
                    console.log("here is res id " + reserv_id)
                    initalizeEditReservationPage(null, reserv_id)
                }
                
                // buttnTD.className="btn btn-primary editButt"
                buttn.innerText="Edit"
                buttTd.append(buttn);
                reqRow.append(buttTd)
            }
            
            w++;
        }
        
        tbleBody.append(reqRow)

    }


    tbl.append(tblHead)
    tbl.append(tbleBody);
    divToAppendTo.append(tbl)
}


async function openEditReservation(reser){

    console.log("i ran to open edit res")
    let editReservationRow = document.getElementById('editReservationRow');
    let divToShowOrHide = document.getElementById(openEL);

    // if(isEditOpen){
        editReservationRow.classList.toggle("off");
        divToShowOrHide.classList.toggle("off");
    // }

}

allowEditHandler =()=>{

    if(allowEdit){
        document.getElementById('updateStatus').disabled = true;
        document.getElementById('saveBtn').hidden = true;
        document.getElementById('editBtn').hidden = false;

        allowEdit=false
    }else{
        
        document.getElementById('updateStatus').disabled = false;
        document.getElementById('saveBtn').hidden = false;
        document.getElementById('editBtn').hidden = true;
        
        allowEdit=true

    }
    
    
}



