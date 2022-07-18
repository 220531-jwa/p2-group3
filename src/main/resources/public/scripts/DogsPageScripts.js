/**
 * VARIABLES
 */

 
 var alldogs = [];
 var openELDogs = "alldogsTableRow";
 var openTopElDogs = "viewdogsDiv"
 var isEditOpen = "false"
//  var userDogs = "allDogsByUserNameTableRow"
 var userDogs = "allDogsByUserNameTableCol"

 
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

        var openELDogs = "allDogsByIDCol";
     }
 
 
 }
 

 
 
 
 
 function topLvlButtonsHandlerDogs(e){
 
     let elid = e.id;
 
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
     openTopElDogs = elid;
 
 }
 
 
 function testLvlButtonsHandlerDogs(e){
 
     let elid = e.id;
 
     elid = elid.replace("_butt","");
 
 
     
     let alldogsTableCol = document.getElementById("alldogsTableRow");
     let alldogsByUserNameTableRow = document.getElementById("alldogsByUserNameTableRow");
     let alldogsByUserNameTableCol = document.getElementById("getdogByIdRow");
 
    if(openELDogs === elid){
        
    }else{

        if(openELDogs === "alldogsTableRow"){
            alldogsTableCol.classList.toggle("off");
    
        }else if(openELDogs === "alldogsByUserNameTableRow"){
            alldogsByUserNameTableRow.classList.toggle("off");
        }else if(openELDogs ==="getdogByIdRow"){
            alldogsByUserNameTableCol.classList.toggle("off");
        }

        let eltochange = document.getElementById(elid);
        
        eltochange.classList.toggle("off")
        openELDogs = elid;

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
     console.log(alldogsByUserNameTableCol)
     
     // PULL IN ALL dogS
     let allAllDogsByUsername = await getAllDogsByUsername(username, seshToken);
 
     // SETTING MAIN VARIABLE TO THE ARRAY OF dogS
     alldogs = allAllDogsByUsername;
 
     // Passing to create table function to create the table append to appropriate place.
     createTableDataDogs(alldogsByUserNameTableCol);
 
 
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
     for(dogProp in dog){
         let tblHdr = document.createElement("th")
         let dogPropStrng = dogProp.toString();
 
         newHdr = dogPropStrng
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
                 if(r===5){
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
