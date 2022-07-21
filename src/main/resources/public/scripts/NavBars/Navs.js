window.addEventListener("load", function () {
    // alert("It's loaded!")
});

const mainBody = document.body;
const mainDiv = document.getElementById("main");

const OwnerTopNav = ` <nav class="navbar navbar-expand-lg bg-dark fixed-top">
<div class="container-fluid">
  <a class="navbar-brand" href="#" style="color:seagreen">Welcome</a>
  <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
      <!-- <li class="nav-item">
        <a class="nav-link active" aria-current="page" href="#" style="color:seagreen">Owner Home</a>
      </li> -->
      <li class="nav-item">
        <li><a class="dropdown-item" href="#" style="color:seagreen">option 1</a></li>
      </li>
      <li class="nav-item">
        <li><a class="dropdown-item" href="#" style="color:seagreen">option 2</a></li>
      </li>
    </ul>
    <form class="d-flex" role="login">
    <button class="btn btn-outline-success" type="submit" onclick="logout()">Log Out</button>
    </form>
    </div>
    </div>
    </nav>`;

const CustomerTopNav = ` <nav class="navbar navbar-expand-lg bg-dark fixed-top">
<div class="container-fluid">
  <a class="navbar-brand" href="#" style="color:seagreen">Welcome</a>
  <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
      <li class="nav-item">
        <a class="nav-link active" aria-current="page" href="#" style="color:seagreen">Customer Home</a>
        
      </li>
      <li class="nav-item">
        <a class="nav-link active" aria-current="page" href="#" style="color:seagreen">Option 1</a>
      </li>
      <li class="nav-item">
        <a class="nav-link active" aria-current="page" href="#" style="color:seagreen">Option 2</a>
      </li>
     
     
    </ul>
    <form class="d-flex" role="login">
      <button class="btn btn-outline-success" type="submit" onclick="logout()">Log Out</button>
    </form>
    </div>
    </div>
    </nav>`;

//   <!-- Side navigation -->
const customerSideNavdos = `<div class="sidenav">
      <div class="container">
        <h6 style="color:lightgray">-Customer-</h6>
      </div>
      <div class="container">
        <div  class="row"  >
          
          <button id="userPanel_tag" class="sideOpt" onclick="openPanel(event.target)">Update Profile</button>
          <button id="reservationPanel_tag" class="sideOpt" onclick="openPanel(event.target)">Reservations</button>
          <button id="dogsPanel_tag" class="sideOpt" onclick="openPanel(event.target)">Dogs</button>
        
        </div
        
      </div>
    </div>`;

const ownerSideNavdos = `<div class="sidenav">
    <div class="container">
      <h6 style="color:lightgray">-Owner-</h6>
    </div>
    <div class="container">
      <div  class="row"  >
        
        <button id="userPanel_tag" class="sideOpt" onclick="openPanel(event.target)">Update Profile</button>
        <button id="reservationPanel_tag" class="sideOpt" onclick="openPanel(event.target)">Reservations</button>
        <button id="dogsPanel_tag" class="sideOpt" onclick="openPanel(event.target)">Dogs</button>
      
      </div
      
    </div>
  </div>`;

function setupTopNav(divToAttachTo, userRole) {
    let topDiv = document.getElementById(divToAttachTo);

    let navCont = document.createElement("div");
    navCont.id = "navCont";

    if (userRole == "CUSTOMER") {
        let myElements = CustomerTopNav;
        navCont.innerHTML = myElements;
    } else {
        let myElements = OwnerTopNav;
        navCont.innerHTML = myElements;
    }

    topDiv.append(navCont);
}

function setupSideNav(divToAttachTo, userRole) {
    let topDiv = document.getElementById(divToAttachTo);

    let navCont = document.createElement("div");
    navCont.id = "navCont";

    console.log(userRole);

    if (userRole == "CUSTOMER") {
        let myElements = customerSideNavdos;
        navCont.innerHTML = myElements;
    } else {
        let myElements = ownerSideNavdos;
        navCont.innerHTML = myElements;
    }

    topDiv.append(navCont);
}
