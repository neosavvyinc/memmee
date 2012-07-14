function SecurityController($scope, broadCastService, $timeout, $location) {

    if( localStorage.getItem("user") !== null && localStorage.getItem("user") !== "" )
    {
        var obj = localStorage.getItem( "user" );
        broadCastService.user = JSON.parse(obj);
        broadCastService.loginUser(broadCastService.user);
        console.log("Loading a user from local storage: " + obj);
    }

    if( broadCastService.user !== null )
    {
        $timeout(function() {
            broadCastService.loginUser( broadCastService.user );
        }, 100);
    }
    else
    {
        $location.path('/home');
    }
}

SecurityController.$inject = ['$scope', 'memmeeBroadCastService', '$timeout', '$location'];