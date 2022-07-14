


    var user = getSessionUserData();


function setup(){
    
    let userType = user.userType;

    setupTopNav("forTheTopDiv",userType);
    setupSideNav("forTheSideDiv",userType);

}