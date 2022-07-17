/**
 * VARIABLES
 */

var openDiv = "userPanel";

const userPanel = document.getElementById("userPanel");
const reservationPanel = document.getElementById("reservationPane");
const dogsPanel = document.getElementById("dogsPanel");

function openPanel() {
    let elID = event.target.id;
    let finalID = elID.replace("_tag", "");

    console.log(finalID);

    let divToClose = document.getElementById(openDiv);
    divToClose.classList.toggle("off");

    //CHECK WHICH PANEL IS OPEN AND CLOSE IT
    // if(openDiv == "userPanel" && finalID != "userPanel"){
    //   userPanel.classList.toggle("off");
    // }else if(openDiv == "reservationPane" && finalID != "reservationPane"){
    //   reservationPanel.classList.toggle("off");
    // }else if(openDiv == "dogsPanel" && finalID != "reservationPane"){
    //   dogsPanel.classList.toggle("off");
    // }

    // GET THE  DIV TO OPEN
    let panelToOpen = document.getElementById(finalID);

    //TOGGLE THE CLASS SO THE PANEL WILL SHOW
    panelToOpen.classList.toggle("off");

    // SET THE NEW INCOMING ID TO THE OPENDIV VARIABLE
    openDiv = finalID;
}

function openReservationPanel() {}
function openDogsPanel() {}

/* Set the width of the side navigation to 250px and the left margin of the page content to 250px */
function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
}

/* Set the width of the side navigation to 0 and the left margin of the page content to 0 */
function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("main").style.marginLeft = "0";
}