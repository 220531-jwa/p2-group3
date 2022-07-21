/**
 * VARIABLES
 */


// const userRes = getSessionUserData();
// const seshToken = "";
window.onload = setupReservations();


var allReservations = [];
var allSessionCustomerResVations = [];
var openEL = "allReservationsTableRow";
var openTopEl = "viewReservationsDivTop";
var isEditOpen = "false";
var allowEdit = false;

var didUpdateStatus = false
var updateStatusVal = ""
var alreadySearchedres=false

var reservation = {
    id: null,
    userEmail: null,
    dogId: null,
    serviceId:null,
    status: null,
    startDateTime: null,
    endDateTime: null,

};



// THIS UPDATED THE ABOVE OBJECT LITERAL
async function updateIncomingReservationPage(incomingReservation) {
    if (incomingReservation) {
        Object.keys(incomingReservation).forEach((key, index) => {
            if (key == "startDateTime") {
                let incomingDate = incomingReservation[key];
                let newDate = new Date(incomingDate);
                let day = newDate.getDate();
                let month = newDate.getMonth() + 1;
                let year = newDate.getFullYear();
                let hour = newDate.getHours();
                let minutes = newDate.getMinutes().toString();
                let seconds = newDate.getSeconds().toString();

                if (minutes.length == 1) {
                    minutes = minutes + "0";
                }

                if (seconds.length == 1) {
                    seconds = seconds + "0";
                }

                let theDate = month + "/" + day + "/" + year;
                let theTime = hour + ":" + minutes + ":" + seconds;

                let fullDate = theDate + " " + theTime;

                reservation.startDateTime = fullDate;
            } else if (key == "endDateTime") {
                let incomingDate = incomingReservation[key];
                let newDate = new Date(incomingDate);
                let day = newDate.getDate();
                let month = newDate.getMonth() + 1;
                let year = newDate.getFullYear();
                let hour = newDate.getHours();
                let minutes = newDate.getMinutes().toString();
                let seconds = newDate.getSeconds().toString();

                if (minutes.length == 1) {
                    minutes = minutes + "0";
                }

                if (seconds.length == 1) {
                    seconds = seconds + "0";
                }

                let theDate = month + "/" + day + "/" + year;
                let theTime = hour + ":" + minutes + ":" + seconds;

                let fullDate = theDate + " " + theTime;

                reservation.endDateTime = fullDate;
            } else {
                reservation[key] = incomingReservation[key];
            }
        });
    }
}


var editReservation = {
    id: null,
    userEmail: null,
    dogId: null,
    serviceId:null,
    status: null,
    startDateTime: null,
    endDateTime: null,

};



// THIS UPDATED THE ABOVE OBJECT LITERAL
async function updateIncomingEditReservationPage(incomingReservation) {
    if (incomingReservation) {
        Object.keys(incomingReservation).forEach((key, index) => {
            if (key == "startDateTime") {
                let incomingDate = incomingReservation[key];
                let newDate = new Date(incomingDate);
                let day = newDate.getDate();
                let month = newDate.getMonth() + 1;
                let year = newDate.getFullYear();
                let hour = newDate.getHours();
                let minutes = newDate.getMinutes().toString();
                let seconds = newDate.getSeconds().toString();

                if (minutes.length == 1) {
                    minutes = minutes + "0";
                }

                if (seconds.length == 1) {
                    seconds = seconds + "0";
                }

                let theDate = month + "/" + day + "/" + year;
                let theTime = hour + ":" + minutes + ":" + seconds;

                let fullDate = theDate + " " + theTime;

                editReservation.startDateTime = fullDate;
            } else if (key == "endDateTime") {
                let incomingDate = incomingReservation[key];
                let newDate = new Date(incomingDate);
                let day = newDate.getDate();
                let month = newDate.getMonth() + 1;
                let year = newDate.getFullYear();
                let hour = newDate.getHours();
                let minutes = newDate.getMinutes().toString();
                let seconds = newDate.getSeconds().toString();

                if (minutes.length == 1) {
                    minutes = minutes + "0";
                }

                if (seconds.length == 1) {
                    seconds = seconds + "0";
                }

                let theDate = month + "/" + day + "/" + year;
                let theTime = hour + ":" + minutes + ":" + seconds;

                let fullDate = theDate + " " + theTime;

                editReservation.endDateTime = fullDate;
            } else {
                editReservation[key] = incomingReservation[key];
            }
        });
    }
}


async function setupReservations(seshToken, userType) {

    // const indexReservationDiv = document.getElementById("updateResCont");

    try{
        
        let newResCont = document.getElementById("createResCol");
        const newreservationsHTMLDoc = "./newReservation.html"
        const newreserVationResp =  await fetch(newreservationsHTMLDoc);
        const newReservationHTML =  await newreserVationResp.text();
        newResCont.innerHTML = newReservationHTML;
    }catch{
        console.log("Loading the Create Reservation Page")
    }

    if (userType === "CUSTOMER") {
        // indexReservationDiv.innerHTML = customerReservationsPage;
        setUpAllCustomerReservations(seshToken, userType);
        
        // let allReservationsButtnCol = document.getElementById("allReservationsByuserNameBtncol")
       //  let allDogsByIDCol = document.getElementById("allDogsByIDCol")
    //    allReservationsButtnCol.classList.toggle("'off");
        document.getElementById("allReservationsByUserNameTableRow").classList.toggle("off")
        document.getElementById("allReservationsByuserNameBtncol").classList.toggle("off")
    //    document.getElementById("allReservationsByIDCol").classList.toggle("off")

    //    openELDogs = "allReservationsByUserNameTableRow";
    openEL = "allReservationsByUserNameTableRow";


    } else if (userType === "OWNER") {
        // indexReservationDiv.innerHTML = ownerReservationsPage;
        setUpAllReservations(seshToken, userType);

        document.getElementById("allReservationsTableRow").classList.toggle("off")
        document.getElementById("allReservationsButtnCol").classList.toggle("off")

        // document.getElementById("allReservationsButtnCol").classList.toggle("off");
       //  document.getElementById("allDogsByUserNameTableRow").classList.toggle("off")

    //    var openELDogs = "allReservationsTableRow";
       openEL = "allReservationsTableRow";
    }
}


function topLvlButtonsHandler(e){


    // let elid = e.target.id;
    let elid = e.id;

    elid = elid.replace("_butt", "");
    
    // let viewReservationsDiv = document.getElementById("viewReservationsDivTop");
    // let createNewReservationDiv = document.getElementById("createNewReservationDiv");
    // let allCurrentRes = document.getElementById("allCurrentReservationSide");
    // let testLvlButtsCont = document.getElementById("testLvlButtsCont");
    
    // console.log(elid)
    // console.log("open top el = " + openTopEl)

    // if(openTopEl === "createNewReservationDiv"){
    //     testLvlButtsCont.classList.toggle("off")
    // }

    let documentToClose = document.getElementById(openTopEl);
    documentToClose.classList.toggle("off")

    let eltochange = document.getElementById(elid);

    eltochange.classList.toggle("off")
    openTopEl = elid;


    
}

function testLvlButtonsHandler(e) {
    let elid = e.id;
    console.log(elid)
    elid = elid.replace("_butt", "");

    let allReservationsTableCol = document.getElementById("allReservationsTableRow");
    let allReservationsByUserNameTableRow = document.getElementById("allReservationsByUserNameTableRow");
    let allReservationsByUserNameTableCol = document.getElementById("getReservationByIdRow");

    if(openEL === elid){

    }else{

        if (openEL == "allReservationsTableRow") {
            allReservationsTableCol.classList.toggle("off");
        } else if (openEL == "allReservationsByUserNameTableRow") {
            allReservationsByUserNameTableRow.classList.toggle("off");
        } else if (openEL == "getReservationByIdRow") {
            allReservationsByUserNameTableCol.classList.toggle("off");
        }
    
        let eltochange = document.getElementById(elid);
    
        eltochange.classList.toggle("off");
        openEL = elid;
    }
}


//THIS WILL GRAB ALL OF THE RESERVATIONS FROM THE DB AND PASS THEM AND THE APPROPRIATE DIV TO APPEND TABLE TO.
async function setUpAllReservations(seshToken) {

    let allReservationsTableCol = document.getElementById("allReservationsTableCol");

    // PULL IN ALL RESERVATIONS
    let reservations = await getAllReservations(seshToken);

    allReservations = reservations;

    // Passing to create table function to create the table append to appropriate place.
    createTableData(allReservationsTableCol);
}

async function setUpAllCustomerReservations(username,seshToken) {
    let allReservationsByUserNameTableCol = document.getElementById("allReservationsByUserNameTableCol");
    let newusername = user.email
    let thetoken = user.pswd
    console.log(thetoken)
    // PULL IN ALL RESERVATIONS
    let incomingReservations = await getAllRservationsByUsername(newusername, thetoken);
    // SETTING MAIN VARIABLE TO THE ARRAY OF RESERVATIONS
    allReservations = incomingReservations;
    

    // Passing to create table function to create the table append to appropriate place.
    createTableData(allReservationsByUserNameTableCol);
}

async function setUpReservationById(seshToken, username) {

    //ASSIGNING THE VARIABLES FOR WHERE TO APPEND TABLE
    let resIdtblediv = document.createElement("div")
    let allReservationsByUserNameTableCol = document.getElementById("getReservationByIdTableCol");
    let res_id = document.getElementById("req_id_box").value;

    username = user.email
    seshToken = user.pswd

    // PULL IN ALL RESERVATIONS
    let requestedReservation = await fetchGetReservationById(username, res_id, seshToken);
    // UPDATE VARIABLE WITH ALL RESERVATIONS
    await updateIncomingReservationPage(requestedReservation[1]);

    if(alreadySearchedres ==true){
        let removeObj = document.getElementById("resbyIdTable")
        allReservationsByUserNameTableCol.removeChild(removeObj)
    }


    // CREATE TABLE ELEMENTS
    let tbl = document.createElement("table");
    tbl.className = "table";
    resIdtblediv.id="resbyIdTable"

    let tblHead = document.createElement("thead");
    let tblHdrRow = document.createElement("tr");


    let newHdr = "";
    let r = 0;

    //  Appending Table Headers to Table.
    for (reservProp in reservation) {
        let tblHdr = document.createElement("th");
        let reservPropStrng = reservProp.toString();

        newHdr = reservPropStrng;
        tblHdr.scope = "col";
        tblHdr.innerText = newHdr;
        tblHdrRow.append(tblHdr);
        
        if (r === 6) {
            let editHdr = document.createElement("th");
            editHdr.scope = "col";
            editHdr.innerText = "Edit";
            tblHdrRow.append(editHdr);
        }

        r++;
    }

    tblHead.append(tblHdrRow)


    let tbleBody = document.createElement("tbody");

    let reqRow = document.createElement("tr");
    w = 0;


    for (key in reservation) {
        let reqTd = document.createElement("td");

        reqTd.innerText = reservation[key];
        reqRow.append(reqTd);

        if (w === 6) {
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
    
    // tblHead.append(tblHdrRow);

    // //CREATING TABLE BODY
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

    // // ASSIGNING ATTRIBUTES TO BUTTON
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

    // tbleBody.append(reqRow);
    tbl.append(tblHead);
    tbl.append(tbleBody);
    resIdtblediv.append(tbl);
    allReservationsByUserNameTableCol.appendChild(resIdtblediv);
    alreadySearchedres=true
}

async function createTableData(divToAppendTo) {

    
    let tbl = document.createElement("table");
    let tblHead = document.createElement("thead");
    let tblHdrRow = document.createElement("tr");

    tbl.className = "table";

    let newHdr = "";

    let allResLength = allReservations.length;

    if (allResLength > 0) {
        let r = 0;
        //  Appending Table Headers to Table.
        for (reservProp in allReservations[0]) {
            let tblHdr = document.createElement("th");
            let reservPropStrng = reservProp.toString();

            newHdr = reservPropStrng;
            tblHdr.scope = "col";
            tblHdr.innerText = newHdr;
            tblHdrRow.append(tblHdr);
            

            if (r === 6) {
                let editHdr = document.createElement("th");
                editHdr.scope = "col";
                editHdr.innerText = "Edit";
                tblHdrRow.append(editHdr);
            }

            r++;
        }
    }
    tblHead.append(tblHdrRow);

    let tbleBody = document.createElement("tbody");

    for (x = 0; x <= allReservations.length - 1; x++) {
        let reqRow = document.createElement("tr");
        w = 0;

        await updateIncomingReservationPage(allReservations[x]);

        // for (key in allSessionCustomerResVations[x]) {

        for (key in reservation) {
            let reqTd = document.createElement("td");

            reqTd.innerText = reservation[key];
            reqRow.append(reqTd);

            if (w === 6) {
                let buttTd = document.createElement("td");
                let buttn = document.createElement("button");
                buttn.type = "button";
                buttn.className = "btn editButt";
                buttn.id = reservation.id + "_edit";
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
    }

    tbl.append(tblHead);
    tbl.append(tbleBody);
    divToAppendTo.append(tbl);
}

async function openEditReservation(reser) {
    console.log("i ran to open edit res");
    let editReservationRow = document.getElementById("editReservationRow");
    let divToShowOrHide = document.getElementById(openEL);

    // if(isEditOpen){
    // document.getElementById("viewReservationsDivTop").classList.toggle("off")
    
    document.getElementById("testLvlButtsCont").classList.toggle("off")
    editReservationRow.classList.toggle("off");
    divToShowOrHide.classList.toggle("off");
    // }
}

allowEditHandler = () => {
    if (allowEdit) {
        document.getElementById("updateStatus").disabled = true;
        document.getElementById("saveBtn").hidden = true;
        document.getElementById("editBtn").hidden = false;

        allowEdit = false;
    } else {
        document.getElementById("updateStatus").disabled = false;
        document.getElementById("saveBtn").hidden = false;
        document.getElementById("editBtn").hidden = true;

        allowEdit = true;
    }
}


function updateStatusReser(){
    let updateStatus = document.getElementById("updateStatus");
    console.log(updateStatus.options[updateStatus.selectedIndex].value)// var value = select.options[select.selectedIndex].value;
    editReservation.status = updateStatus.selected

    console.log(editReservation)
    

}


async function editButtonHandler(username,reserve_id,sesh){

    let reser = await fetchGetReservationById(username,reserve_id,sesh)
    updateIncomingEditReservationPage(reser);
    openEditReservation();
                    console.log("here is res id " + reserve_id);
                    
    initalizeEditReservationPage(null, reserve_id);
}