
const user = getSessionUserData();




async function setup(){

    let userType = user.userType;

    // setErrUp();
    setupTopNav("forTheTopDiv",userType);
    setupSideNav("forTheSideDiv",userType);

}