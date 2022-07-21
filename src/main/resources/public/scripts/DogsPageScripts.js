/**
 * VARIABLES
 */

 
 var alldogs = [];
 var openELDogs = "";
 var openTopElDogs = "viewdogsDiv"
 var isEditOpenDogs = "false"
 var userDogs = "allDogsByUserNameTableRow"
//  var userDogs = "allDogsByUserNameTableCol"
var alreadySearched = false



var dog = {
    id:null,
	user_email:null,
	status:null,
	dog_name:null,
	breed:null,
	dog_age:null,
	vaccinated:null

};



// THIS UPDATED THE ABOVE OBJECT LITERAL
async function updateIncomingDogPage(incomingdog) {
    if (incomingdog) {
        Object.keys(incomingdog).forEach((key, index) => {
            
                dog[key] = incomingdog[key];
            
        });
    }
}

 
async function setupDogs(seshToken,userType){
 	let userData = getSessionUserData();
 
    //  const indexdogDiv = document.getElementById("updateDogCont");

     if(userType === "CUSTOMER"){
         
        //  indexdogDiv.innerHTML = await getHTMLPage('http://localhost:8080/html/DogsPage.html');
         setUpAllCustomerdogs(seshToken,userData.email);
         let allDogsButtnCol = document.getElementById("allDogsButtnCol")
         allDogsButtnCol.classList.toggle("'off");

        //  document.getElementById("allDogsTableRow").classList.toggle("off")
         document.getElementById("allDogsByUserNameTableRow").classList.toggle("off")
        document.getElementById("allDogsByuserNameCol").classList.toggle("off")

        openELDogs = "allDogsByUserNameTableRow";
 
 
     }else if(userType==="OWNER"){
        //  indexdogDiv.innerHTML = ownerdogsPage;
         setUpAlldogs(seshToken);
         document.getElementById("allDogsTableRow").classList.toggle("off")
         document.getElementById("allDogsButtnCol").classList.toggle("off")
        //  document.getElementById("allDogsByUserNameTableRow").classList.toggle("off")

        openELDogs = "allDogsTableRow";
     }
 
 
 }
 

 
 
 
 
 function topLvlButtonsHandlerDogs(e){
 
     let eliddog = e.id;
 
     eliddog = eliddog.replace("_butt","");
     let viewdogsDiv = document.getElementById("viewdogsDiv");
     let createNewdogDiv = document.getElementById("createNewdogDiv");
 
     if(openTopEl=="viewdogsDiv"){
         viewdogsDiv.classList.toggle("off");
         
 
     }else if(openTopEl == "createNewdogDiv"){
         createNewdogDiv.classList.toggle("off");
         
     }
 
     let eltochange = document.getElementById(eliddog);
     
    
     eltochange.classList.toggle("off")
     openTopElDogs = eliddog;
 
 }
 
 
 function testLvlButtonsHandlerDogs(e){
 
     let eliddog = e.id;
     eliddog = eliddog.replace("_butt","");
 
 
     
    //  let alldogsTableCol = document.getElementById("alldogsTableRow");
     let alldogsByUserNameTableRow = document.getElementById("alldogsByUserNameTableRow");
     let alldogsByUserNameTableCol = document.getElementById("getdogByIdRow");

     console.log(openELDogs)
     console.log(eliddog)
 
    if(openELDogs == eliddog){
        
    }else{


        // let initDivToClose = document.getElementById(openELDogs)
        // initDivToClose.classList.toggle("off");
        // if(openELDogs == "alldogsTableRow"){
        //     alldogsTableCol.classList.toggle("off");
    
        // }else if(openELDogs == "alldogsByUserNameTableRow"){
        //     alldogsByUserNameTableRow.classList.toggle("off");
        // }else if(openELDogs =="getdogByIdRow"){
        //     alldogsByUserNameTableCol.classList.toggle("off");
        // }
        let initialEltochange = document.getElementById(openELDogs);
        initialEltochange.classList.toggle("off")

        let eltochange = document.getElementById(eliddog);
        
        eltochange.classList.toggle("off")
        openELDogs = eliddog;

    }
 
 
    
 
 }
 
 
 
 async function setUpAlldogs(seshToken){
 
     let alldogsTableCol = document.getElementById("alldogsTableCol");
 
     // PULL IN ALL dogS
    //  let dogs = await getAlldogs(seshToken);
    let allTheDogs = await getAllDogs(seshToken);
    
    //SETTING MAIN VARIABLE TO THE ARRAY OF dogs
     alldogs = allTheDogs;
 
     // Passing to create table function to create the table append to appropriate place.
     createTableDataDogs(alldogsTableCol);
     
     
 }
 
 
 async function setUpAllCustomerdogs(seshToken,username){
 

     let alldogsByUserNameTableCol = document.getElementById(userDogs);
    //  console.log(alldogsByUserNameTableCol)
     
     // PULL IN ALL dogS
     let allAllDogsByUsername = await getAllDogsByUsername(username, seshToken);
 
     // SETTING MAIN VARIABLE TO THE ARRAY OF dogS
     alldogs = allAllDogsByUsername;
 
     // Passing to create table function to create the table append to appropriate place.
     createTableDataDogs(alldogsByUserNameTableCol);
 
 
 }
 
 
 
 
 async function setUpdogByIdAgain(seshToken,username){
    
    console.log("I ran")
    let dogIdtblediv = document.createElement("div")
    let getDogByIdTableCol = document.getElementById("getDogByIdTableCol");
    let dog_id = document.getElementById("dog_req_id_box").value
    
    let userName = user.email
    let token = user.pswd
    // PULL IN ALL dogS
    let requesteddog = await fetchGetDogById(userName,dog_id, token);
    requesteddog = requesteddog[1]
    await updateIncomingDogPage(requesteddog);
    
    if(alreadySearched ==true){
        let removeObj = document.getElementById("dogbyIdTable")
        getDogByIdTableCol.removeChild(removeObj)
    }

    let tbl = document.createElement("table");
    let tblHead = document.createElement("thead");
    let tblHdrRow = document.createElement("tr");

    tbl.className="table";
    dogIdtblediv.id="dogbyIdTable"


    let newHdr = ""
    let r = 0;


    

    //  Appending Table Headers to Table.
    for(dogProp in dog){
        let tblHdr = document.createElement("th")
        let dogPropStrng = dogProp.toString();

        newHdr = dogPropStrng
        tblHdr.scope = "col"
        tblHdr.innerText = newHdr
        tblHdrRow.append(tblHdr);

        

        if(r===8){
            let editHdr = document.createElement("th");
            editHdr.scope="col";
            editHdr.innerText = "Edit";
            tblHdrRow.append(editHdr);
        }

        r++
    
    }

    
    
    tblHead.append(tblHdrRow)


    let tbleBody = document.createElement("tbody");

    // for (x = 0; x <= allReservations.length - 1; x++) {
        let reqRow = document.createElement("tr");
        w = 0;

        // await updateIncomingReservationPage(allReservations[x]);

        // for (key in allSessionCustomerResVations[x]) {

        for (key in dog) {
            let reqTd = document.createElement("td");

            reqTd.innerText = dog[key];
            reqRow.append(reqTd);

            if (w === 8) {
                let buttTd = document.createElement("td");
                let buttn = document.createElement("button");
                buttn.type = "button";
                buttn.className = "btn editButt";
                buttn.id = dog.id + "_edit";
                buttn.onclick = () => {
                    // let reserv_id = e.target.id;
                    let reserve_id = buttn.id;
                    reserve_id = reserve_id.replace("_edit", "");
                    let sesh = user.pswd
                    editButtonHandler(user.email,reserve_id, sesh);
                    // document.getElementById("viewReservationsDivTop").classList.toggle("off")
                };
                buttn.innerText = "Edit";
                buttTd.append(buttn);
                reqRow.append(buttTd);
            }

            w++;
        }

        tbleBody.append(reqRow);
    // }


    //CREATING TABLE BODY
    // let tbleBody = document.createElement("tbody");
    // let reqRow = document.createElement("tr");
    // let reqTdID = document.createElement("td");
    // let reqTduserEmail = document.createElement("td");
    // let reqTddogId = document.createElement("td");
    // let reqTdstatus = document.createElement("td");
    // let reqTdstartDateTime = document.createElement("td");
    // let reqTdendDateTime = document.createElement("td");
    // let buttTd = document.createElement("td");
    // let buttn = document.createElement("button");

    // ASSIGNING ATTRIBUTES TO BUTTON
    // buttn.type = "button";
    // buttn.className = "btn editButt";
    // buttn.innerText = "Edit";
    // buttTd.append(buttn);
       
    

    // reqTdID.innerText = reservation.id;
    // reqTduserEmail.innerText = reservation.userEmail;
    // reqTddogId.innerText = reservation.dogId;
    // reqTdstatus.innerText = reservation.status;
    // reqTdstartDateTime.innerText = reservation.startDateTime;
    // reqTdendDateTime.innerText = reservation.endDateTime;

    // reqRow.append(reqTdID);
    // reqRow.append(reqTduserEmail);
    // reqRow.append(reqTddogId);
    // reqRow.append(reqTdstatus);
    // reqRow.append(reqTdstartDateTime);
    // reqRow.append(reqTdendDateTime);
    // reqRow.append(buttTd);

    // tbleBody.append(reqRow)
    tbl.append(tblHead)
    tbl.append(tbleBody);
    dogIdtblediv.appendChild(tbl)
    getDogByIdTableCol.appendChild(dogIdtblediv)
    alreadySearched = true;

}
 
 
 
 
 
 async function createTableDataDogs(divToAppendTo){
     
     let tbl = document.createElement("table");
     let tblHead = document.createElement("thead");
     let tblHdrRow = document.createElement("tr");
 
     tbl.className="table";
 
 
     let newHdr = ""
 
     let allResLength = alldogs.length;
     
     if(allResLength > 0){
 
         let r = 0;
          //  Appending Table Headers to Table.
             for(dogProp in alldogs[0]){
                 let tblHdr = document.createElement("th")
                 let dogPropStrng = dogProp.toString();
 
                 newHdr = dogPropStrng
                 tblHdr.scope = "col"
                 tblHdr.innerText = newHdr
                 tblHdrRow.append(tblHdr);
                 if(r===6){
                     let editHdr = document.createElement("th");
                     editHdr.scope="col";
                     editHdr.innerText = "Edit";
                     tblHdrRow.append(editHdr);
                 }
 
                 r++
             
             }
    tblHead.append(tblHdrRow)
     }

     let tbleBody = document.createElement("tbody");
 
 	 //Iterate through alldogs array
     for(x = 0; x<=alldogs.length - 1; x++){
         
         let reqRow = document.createElement("tr");
         w = 0;
         dog = alldogs[x];
		 //For each value in dog object
         for (key in Object.values(dog)) {
			 
             
             let reqTd = document.createElement("td")
			 //Equal to value at index "key" of dog
             reqTd.innerText = Object.values(dog)[key];
             reqRow.append(reqTd);
 
             if(w===6){
 
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
 
 
 async function openEditdog(){
 
     console.log("i ran to open edit res")
     let editdogRow = document.getElementById('editdogRow');
     let divToShowOrHide = document.getElementById(openELDogs);
 
     
         editdogRow.classList.toggle("off");
         divToShowOrHide.classList.toggle("off");
    
 
 }
