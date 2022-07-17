




/**
 * VARIABLES
 */


 const userRes = getSessionUserData();
 const seshToken = "";
 
 
 
 var alldogs = [];
 var allSessionCustomerResVations = [];
 var openEL = "alldogsTableRow";
 var openTopEl = "viewdogsDiv"
 var isEditOpen = "false"
 
 
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
 
 const indexdogDiv = `<div id="main">

        <div id="topLvlButtsCont" class="container">
            <div class="row">
                <div class="col-6 col-sm-6">
                    <butoon id="viewDogsDiv_butt" type="button" class="btn btn-primary" onclick="topLvlButtonsHandler(event.target)">View All Your Dogs</butoon>
                </div>
                <div class="col-6 col-sm-6">
                    <butoon id="createNewDogDiv_butt" type="button" class="btn btn-primary" onclick="topLvlButtonsHandler(event.target)">Book New Dog</butoon>
                </div>
            </div>

        </div>
        <div id="testLvlButtsCont" class="container" >
            <div class="row">
                <div class="col-4 col-sm-4">
                    <button id="allDogsTableRow_butt" type="button" class="btn btn-primary" onclick="testLvlButtonsHandler(event.target)">test get all Dogs</button>
                </div>
                <div class="col-4 col-sm-4">
                    <button id="allDogsByUserNameTableRow_butt" type="button" class="btn btn-primary" onclick="testLvlButtonsHandler(event.target)">test get Dog by username</button>
                </div>
                <div class="col-4 col-sm-4">
                    <button id="getDogByIdRow_butt" type="button" class="btn btn-primary" onclick="testLvlButtonsHandler(event.target)">test get Dog by id</button>
                </div>
            </div>
        </div>

        <div id="viewDogsDiv" class="container">

            <div id="allDogsTableRow" class="row tableHolder">
                <div class="row">
                    <div class="col-12 col-sm-12" style="margin-bottom: 3vh;">
                        <h4 style="text-align:center; width: 100%;">All Dogs</h4>
                    </div>
                </div>
                
                <div id="allDogsTableCol" class="col-12 col-sm-12">
                </div>
            </div>

            <div id="allDogsByUserNameTableRow" class="row tableHolder off">
                <div class="row">
                    <div class="col-12 col-sm-12" >
                        <h4 style="text-align:center; width: 100%;">All By User Name</h4>

                    </div>
                </div>
                <div id="allDogsByUserNameTableCol" class="col-12 col-sm-12">
                </div>
            </div>

            <div id="getDogByIdRow" class="row tableHolder off">
                <div id="getDogByIdTop" class="col-12 col-sm-12">
                    <div class="row">
                        <div class="col-12 col-sm-12" >
                            <h4 style="text-align:center; width: 100%;">Get Dog By ID</h4>
    
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
                            <button id="getreqbutt" class="btn btn-primary" type="button" style="float:left" onclick="setUpDogById()">Get Request</button>
                        </div>
                        <div class="col-6 col-sm-6" style="padding:4vh">
                            <!-- <button id="getreqbutt" class="btn btn-primary" type="button" style="float:left">Get Request</button> -->
                        </div>

                    </div>
                    <hr>

                    <div class="row">
                        <div id="getDogByIdTableCol" class="col-12 col-sm-12">
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


            <div id="editDogRow" class="row tableHolder off">
                <div id="editDogCol" class="col-12 col-sm-12" style="margin-bottom: 3vh;">

                    <div class="container bg-light">
                        <h1 id="title" class="mb-4">Edit Dog:</h1>
                        <div id="error" style="Color: red"></div>
                        <form id="form">
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
                                <button class="btn btn-primary mb-4" type="button" onclick="openEditDog()">Back</button>
                            </div>
                            <div class="col text-end">
                                <button id="saveBtn" class="btn btn-primary mb-4" type="button" onclick="save()">Save</button>
                            </div>
                        </div>
                        <h1 class="mb-4 bg-dark">#</h1>
                    </div>



                </div>
            </div>


        </div>

        <div id="createNewDogDiv" class="container off">
            <div class="row">
                <div class="col-12 col-sm-12">
                    <h5>Create Dog</h5>
                </div>
            </div>

            
        </div>

        
        
    </div>`
 
  async function setupDogs(seshToken,userType){
 
 
     const indexdogDiv = document.getElementById("updateDogCont");
 
     if(userType === "CUSTOMER"){
         
         indexdogDiv.innerHTML = customerdogsPage;
         setUpAllCustomerdogs(seshToken,userType);
 
 
     }else if(userType==="OWNER"){
         indexdogDiv.innerHTML =ownerdogsPage;
         setUpAlldogs(seshToken,userType);
     }
 
     //setupTopNav("forTheTopDiv",userType);
     //setupSideNav("forTheSideDiv",userType);
 
     // CUSTOMER SIDE
     
     
     // OWNER SIDE
 
 }
 
   
 
 // async function updateIncomingReservation(incomingReservation){
 
 //     Object.keys(incomingReservation).forEach((key, index) => {
 //         reservation[key] = incomingReservation[key];
 //     });
 
 // }
 
 // const indexReservationDiv = document.getElementById("updateResCont");
 
 
 
 
 function topLvlButtonsHandlerDogs(e){
 
     let elid = event.target.id;
 
     elid = elid.replace("_butt","");
     let viewdogsDiv = document.getElementById("viewdogsDiv");
     let createNewdogDiv = document.getElementById("createNewdogDiv");
 
     if(openTopEl=="viewdogsDiv"){
         viewdogsDiv.classList.toggle("off");
         
 
     }else if(openTopEl == "createNewdogDiv"){
         createNewdogDiv.classList.toggle("off");
         
     }
 
     let eltochange = document.getElementById(elid);
     
    
     eltochange.classList.toggle("off")
     openTopEl = elid;
 
 }
 
 
 function testLvlButtonsHandler(e){
 
     let elid = event.target.id;
 
     elid = elid.replace("_butt","");
 
 
     
     let alldogsTableCol = document.getElementById("alldogsTableRow");
     let alldogsByUserNameTableRow = document.getElementById("alldogsByUserNameTableRow");
     let alldogsByUserNameTableCol = document.getElementById("getdogByIdRow");
 
 
     if(openEL == "alldogsTableRow"){
         alldogsTableCol.classList.toggle("off");
 
     }else if(openEL == "alldogsByUserNameTableRow"){
         alldogsByUserNameTableRow.classList.toggle("off");
     }else if(openEL == "getdogByIdRow"){
         alldogsByUserNameTableCol.classList.toggle("off");
     }
 
 
     let eltochange = document.getElementById(elid);
     
     eltochange.classList.toggle("off")
     openEL = elid;
    
 
 }
 
 
 
 async function setUpAlldogs(seshToken){
 
     let alldogsTableCol = document.getElementById("alldogsTableCol");
     
     // let seshToken = userRes.pswrd
     
     
     // let username = userRes.email;
 
     // PULL IN ALL dogS
    //  let dogs = await getAlldogs(seshToken);
 
     alldogs = dogs
 
     // Passing to create table function to create the table append to appropriate place.
     createTableData(alldogsTableCol,"multiple");
 
 
 }
 
 
 async function setUpAllCustomerdogs(seshToken,username){
 
     let alldogsByUserNameTableCol = document.getElementById("alldogsByUserNameTableCol");
     
     // let seshToken = userRes.pswrd;
     // let username = userRes.email;
 
 
     // PULL IN ALL dogS
     let allAllDogsByUsername = await getAllDogsByUsername(username, seshToken);
 
     // SETTING MAIN VARIABLE TO THE ARRAY OF dogS
     alldogs = allAllDogsByUsername
 
     // Passing to create table function to create the table append to appropriate place.
     createTableData(alldogsByUserNameTableCol,"multiple");
 
 
 }
 
 
 
 
 async function setUpdogById(seshToken,username){
 
     let alldogsByUserNameTableCol = document.getElementById("getdogByIdTableCol");
     let res_id = document.getElementById("req_id_box").value
     
     // PULL IN ALL dogS
     let requesteddog = await fetchGetdogById(username,res_id, seshToken);
 
     await updateIncomingdogPage(requesteddog);
     
 
     let tbl = document.createElement("table");
     let tblHead = document.createElement("thead");
     let tblHdrRow = document.createElement("tr");
 
     tbl.className="table";
 
 
     let newHdr = ""
     let r = 0;
 
     //  Appending Table Headers to Table.
     for(reservProp in dog){
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
     alldogsByUserNameTableCol.append(tbl)
 
 }
 
 
 
 
 
 async function createTableData(divToAppendTo, singleOrMultiple){
     
     let tbl = document.createElement("table");
     let tblHead = document.createElement("thead");
     let tblHdrRow = document.createElement("tr");
 
     tbl.className="table";
 
 
     let newHdr = ""
 
     let allResLength = await alldogs.length;
     
     if(allResLength > 0){
 
         let r = 0;
          //  Appending Table Headers to Table.
             for(reservProp in alldogs[0]){
                 
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
 
 
     for(x = 0; x<=alldogs.length - 1; x++){
         
         let reqRow = document.createElement("tr");
         w = 0;
         
         
         await updateIncomingdogPage(alldogs[x]);
 
         // for (key in allSessionCustomerResVations[x]) {
         
         for (key in dog) {
             
             
             let reqTd = document.createElement("td")
               
             reqTd.innerText = dog[key];
             reqRow.append(reqTd);
 
             if(w===5){
 
                 let buttTd = document.createElement("td")
                 let buttn = document.createElement("button");
                 buttn.type="button";
                 buttn.className="btn editButt"
                 buttn.id=dog.id + "_edit"
                 buttn.onclick =(e)=>{
                     let reserv_id = e.target.id ;
                     reserv_id = reserv_id.replace("_edit","")
                     openEditdog()
                     console.log("here is res id " + reserv_id)
                     updateViewExistingdogReTry(reserv_id)
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
 
 
 async function openEditdog(reser){
 
     console.log("i ran to open edit res")
     let editdogRow = document.getElementById('editdogRow');
     let divToShowOrHide = document.getElementById(openEL);
 
     // if(isEditOpen){
         editdogRow.classList.toggle("off");
         divToShowOrHide.classList.toggle("off");
     // }
 
 }
 
 
 
 