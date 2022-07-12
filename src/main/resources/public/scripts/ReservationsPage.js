const user = getSessionUserData();
const token = sessionStorage.getItem("token");


var allReservations = [];
var allSessionCustomerResVations = [];
var openEL = "allReservationsTableRow";
var openTopEl = "viewReservationsDiv"


var reservation = {
    id:null,
    userEmail:null,
    dogId:null,
    status:null,
    startDateTime:null,
    endDateTime:null
}

async function updateIncomingReservationPage(incomingReservation){

    if(incomingReservation){
        Object.keys(incomingReservation).forEach((key, index) => {
    
            if(key == "startDateTime" ){
                console.log("here")
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


                console.log(seconds.length)
                if(seconds.length==1){
                    seconds = seconds + "0"
                    console.log(seconds)
                }
    
                let theDate = month + "/" + day + "/" + year
                let theTime = hour + ":" + minutes + ":" + seconds

                let fullDate = theDate + " " + theTime
    
                console.log(theDate);
    
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


                console.log(seconds.length)
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


// async function updateIncomingReservation(incomingReservation){

//     Object.keys(incomingReservation).forEach((key, index) => {
//         reservation[key] = incomingReservation[key];
//     });

// }

async function setup(){

    let userType = user.userType;


    setupTopNav("forTheTopDiv",userType);
    setupSideNav("forTheSideDiv",userType);

    // CUSTOMER SIDE
    setUpAllReservations();
    setUpAllCustomerReservations();


    // OWNER SIDE

}


function topLvlButtonsHandler(e){

    let elid = event.target.id;

    elid = elid.replace("_butt","");
    let viewReservationsDiv = document.getElementById("viewReservationsDiv");
    let createNewReservationDiv = document.getElementById("createNewReservationDiv");

    if(openTopEl=="viewReservationsDiv"){
        viewReservationsDiv.classList.toggle("off");
        

    }else if(openTopEl == "createNewReservationDiv"){
        createNewReservationDiv.classList.toggle("off");
        
    }

    let eltochange = document.getElementById(elid);
    
   
    eltochange.classList.toggle("off")
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



async function setUpAllReservations(){


    let allReservationsTableCol = document.getElementById("allReservationsTableCol");
    
    let seshToken = user.pswrd;
    let username = user.email;

    // PULL IN ALL RESERVATIONS
    // let allReservations = await getAllReservations(seshToken);


    // PULL IN ALL RESERVATIONS
    let reservations = await getAllReservations(seshToken);

    allReservations = reservations

    // Passing to create table function to create the table append to appropriate place.
    createTableData(allReservationsTableCol,"multiple");







    // let allReservationsTableCol = document.getElementById("allReservationsTableCol");
    
    // let seshToken = user.pswrd;
    // let username = user.email;

    // PULL IN ALL RESERVATIONS
    // let allReservations = await getAllReservations(seshToken);


    // PULL IN ALL RESERVATIONS
    // let allReservations = await getAllRservationsByUsername(username, seshToken);

    // let tbl = document.createElement("table");
    // let tblHead = document.createElement("thead");
    // let tblHdrRow = document.createElement("tr");

    // tbl.className="table";


    // let newHdr = ""



    // if(allReservations.length > 0){

    //      //  Appending Table Headers to Table.
    //         for(reservProp in allReservations[0]){
    //             let tblHdr = document.createElement("th")
    //             let reservPropStrng = reservProp.toString();

    //             newHdr = reservPropStrng
    //             tblHdr.scope = "col"
    //             tblHdr.innerText = newHdr
    //             tblHdrRow.append(tblHdr);
            
    //         }
    // }
    // let tblHdr = document.createElement("th");
    // tblHdr.scope = "col"
    // tblHdr.innerText = "edit"
    // tblHdrRow.append(tblHdr);
    // tblHead.append(tblHdrRow)

    // let tbleBody = document.createElement("tbody");


    // for(x = 0; x<=allReservations.length -1; x++){
        
    //     let reqRow = document.createElement("tr");
    //     w = 0;
    //     await updateIncomingReservationPage(allReservations[x]);

    //     // for (key in allSessionCustomerResVations[x]) {

    //     if(reservation){


    //         for (key in reservation) {
    
    //         // for (key in allReservations[x]) {
                
                
    //             let reqTd = document.createElement("td")
                  
    //             reqTd.innerText = allReservations[x][key];
    //             reqRow.append(reqTd);
                
    //             w++;
    //         }
    //         let editbutt = document.createElement("button");
    //         editbutt.className="btn btn-primary";
    //         editbutt.id=w;
    //         w++;
            
    //         tbleBody.append(reqRow)

    //     }

    // }


    // tbl.append(tblHead)
    // tbl.append(tbleBody);
    // allReservationsTableCol.append(tbl)

}


async function setUpAllCustomerReservations(){

    let allReservationsByUserNameTableCol = document.getElementById("allReservationsByUserNameTableCol");
    
    let seshToken = user.pswrd;
    let username = user.email;

    // PULL IN ALL RESERVATIONS
    // let allReservations = await getAllReservations(seshToken);


    // PULL IN ALL RESERVATIONS
    let allSessionCustomerResVations = await getAllRservationsByUsername(username, seshToken);

    allReservations = allSessionCustomerResVations

    // Passing to create table function to create the table append to appropriate place.
    createTableData(allReservationsByUserNameTableCol,"multiple");







    // let tbl = document.createElement("table");
    // let tblHead = document.createElement("thead");
    // let tblHdrRow = document.createElement("tr");

    // tbl.className="table";


    // let newHdr = ""



    // if(allSessionCustomerResVations.length > 0){

    //      //  Appending Table Headers to Table.
    //         for(reservProp in allSessionCustomerResVations[0]){
    //             let tblHdr = document.createElement("th")
    //             let reservPropStrng = reservProp.toString();

    //             newHdr = reservPropStrng
    //             tblHdr.scope = "col"
    //             tblHdr.innerText = newHdr
    //             tblHdrRow.append(tblHdr);
            
    //         }
    // }
    // tblHead.append(tblHdrRow)

    // let tbleBody = document.createElement("tbody");


    // for(x = 0; x<=allSessionCustomerResVations.length - 1; x++){
        
    //     let reqRow = document.createElement("tr");
    //     w = 0;
    //     await updateIncomingReservationPage(allSessionCustomerResVations[x]);

    //     // for (key in allSessionCustomerResVations[x]) {
        
    //     for (key in reservation) {
            
            
    //         let reqTd = document.createElement("td")
              
    //         reqTd.innerText = allSessionCustomerResVations[x][key];
    //         reqRow.append(reqTd);
            
    //         w++;
    //     }
        
    //     tbleBody.append(reqRow)

    // }


    // tbl.append(tblHead)
    // tbl.append(tbleBody);
    // allReservationsByUserNameTableCol.append(tbl)

}




async function setUpReservationById(){

    let allReservationsByUserNameTableCol = document.getElementById("getReservationByIdTableCol");
    let res_id = document.getElementById("req_id_box").value
    
    let seshToken = user.pswrd;
    let username = user.email;

    // PULL IN ALL RESERVATIONS
    // let allReservations = await getAllReservations(seshToken);


    // PULL IN ALL RESERVATIONS
    let requestedReservation = await getReservationById(username,res_id, seshToken);

    await updateIncomingReservationPage(requestedReservation);
    console.log(reservation)

    let tbl = document.createElement("table");
    let tblHead = document.createElement("thead");
    let tblHdrRow = document.createElement("tr");

    tbl.className="table";


    let newHdr = ""


    //  Appending Table Headers to Table.
    for(reservProp in reservation){
        let tblHdr = document.createElement("th")
        let reservPropStrng = reservProp.toString();

        newHdr = reservPropStrng
        tblHdr.scope = "col"
        tblHdr.innerText = newHdr
        tblHdrRow.append(tblHdr);
    
    }
    
    tblHead.append(tblHdrRow)

    let tbleBody = document.createElement("tbody");

    
    
    let reqRow = document.createElement("tr");

    let reqTdID = document.createElement("td")
    let reqTduserEmail = document.createElement("td")
    let reqTddogId= document.createElement("td")
    let reqTdstatus = document.createElement("td")

    let reqTdstartDateTime = document.createElement("td")
    let reqTdendDateTime = document.createElement("td")

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

        // w = 0;

        // for (key in reservation) {
            
            
        //     let reqTd = document.createElement("td")
              
        //     reqTd.innerText = reservation[key];
        //     reqRow.append(reqTd);
            
        //     w++;
        // }
        
    tbleBody.append(reqRow)
    tbl.append(tblHead)
    tbl.append(tbleBody);
    allReservationsByUserNameTableCol.append(tbl)

}





function createTableData(divToAppendTo, singleOrMultiple){
    
    let tbl = document.createElement("table");
    let tblHead = document.createElement("thead");
    let tblHdrRow = document.createElement("tr");

    tbl.className="table";


    let newHdr = ""



    if(allReservations.length > 0){

         //  Appending Table Headers to Table.
            for(reservProp in allReservations[0]){
                let tblHdr = document.createElement("th")
                let reservPropStrng = reservProp.toString();

                newHdr = reservPropStrng
                tblHdr.scope = "col"
                tblHdr.innerText = newHdr
                tblHdrRow.append(tblHdr);
            
            }
    }
    tblHead.append(tblHdrRow)

    let tbleBody = document.createElement("tbody");


    for(x = 0; x<=allReservations.length - 1; x++){
        
        let reqRow = document.createElement("tr");
        w = 0;
        
        
        updateIncomingReservationPage(allReservations[x]);

        // for (key in allSessionCustomerResVations[x]) {
        
        for (key in reservation) {
            
            
            let reqTd = document.createElement("td")
              
            reqTd.innerText = reservation[key];
            reqRow.append(reqTd);
            
            w++;
        }
        
        tbleBody.append(reqRow)

    }


    tbl.append(tblHead)
    tbl.append(tbleBody);
    divToAppendTo.append(tbl)
}



