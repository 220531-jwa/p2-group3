const user = getSessionUserData();
const token = sessionStorage.getItem("token");


var allReservations = [];
var allSessionCustomerResVations = [];
var openEL = "allReservationsTableRow";


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


    
    let allReservationsTableCol = document.getElementById("allReservationsTableRow");
    let allReservationsByUserNameTableRow = document.getElementById("allReservationsByUserNameTableRow");
    let allReservationsByUserNameTableCol = document.getElementById("getReservationByIdRow");



    console.log(elid)
    console.log(openEL)

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
    console.log(elid)

}

async function setUpAllReservations(){

    let allReservationsTableCol = document.getElementById("allReservationsTableCol");
    
    let seshToken = user.pswrd;
    let username = user.email;

    // PULL IN ALL RESERVATIONS
    let allReservations = await getAllReservations(seshToken);


    // PULL IN ALL RESERVATIONS
    // let allReservations = await getAllRservationsByUsername(username, seshToken);

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
    let tblHdr = document.createElement("th");
    tblHdr.scope = "col"
    tblHdr.innerText = "edit"
    tblHdrRow.append(tblHdr);
    tblHead.append(tblHdrRow)

    let tbleBody = document.createElement("tbody");


    for(x = 0; x<=allReservations.length; x++){
        
        let reqRow = document.createElement("tr");
        w = 0;

        for (key in allReservations[x]) {
            
            
            let reqTd = document.createElement("td")
              
            reqTd.innerText = allReservations[x][key];
            reqRow.append(reqTd);
            
            w++;
        }
        let editbutt = document.createElement("button");
        editbutt.className="btn btn-primary";
        editbutt.id=w;
        w++;
        
        tbleBody.append(reqRow)

    }


    tbl.append(tblHead)
    tbl.append(tbleBody);
    allReservationsTableCol.append(tbl)

}


async function setUpAllCustomerReservations(){

    let allReservationsByUserNameTableCol = document.getElementById("allReservationsByUserNameTableCol");
    
    let seshToken = user.pswrd;
    let username = user.email;

    // PULL IN ALL RESERVATIONS
    // let allReservations = await getAllReservations(seshToken);


    // PULL IN ALL RESERVATIONS
    let allSessionCustomerResVations = await getAllRservationsByUsername(username, seshToken);

    let tbl = document.createElement("table");
    let tblHead = document.createElement("thead");
    let tblHdrRow = document.createElement("tr");

    tbl.className="table";


    let newHdr = ""



    if(allSessionCustomerResVations.length > 0){

         //  Appending Table Headers to Table.
            for(reservProp in allSessionCustomerResVations[0]){
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


    for(x = 0; x<=allSessionCustomerResVations.length; x++){
        
        let reqRow = document.createElement("tr");
        w = 0;

        for (key in allSessionCustomerResVations[x]) {
            
            
            let reqTd = document.createElement("td")
              
            reqTd.innerText = allSessionCustomerResVations[x][key];
            reqRow.append(reqTd);
            
            w++;
        }
        
        tbleBody.append(reqRow)

    }


    tbl.append(tblHead)
    tbl.append(tbleBody);
    allReservationsByUserNameTableCol.append(tbl)

}



