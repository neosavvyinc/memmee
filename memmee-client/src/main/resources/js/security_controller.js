function SecurityController($scope, broadCastService, $location, $timeout) {
    $scope.loggedInUser = null;
    $scope.visibleLoggedInStyle = { visibility: 'hidden' };

    $scope.saveLoggedInUser = function( $user ) {
        $scope.loggedInUser = $user;
        localStorage.setItem( "user", JSON.stringify($user) );
        $scope.visibleLoggedInStyle = { visibility: 'visible' };
    }

    $scope.logout = function() {
        $scope.visibleLoggedInStyle = { visibility: 'hidden' };
        broadCastService.logoutUser($scope.loggedInUser);
        localStorage.removeItem( "user");
        $location.path('/home');
    }

    if( localStorage.getItem("user") !== null && localStorage.getItem("user") !== "" )
    {
        var obj = localStorage.getItem( "user" );
        broadCastService.user = JSON.parse(obj);
        $scope.saveLoggedInUser(broadCastService.user);
        console.log("Loading a user from local storage: " + obj);
    }

    if( broadCastService.user !== null )
    {
        $timeout(function() {
            broadCastService.loginUser( broadCastService.user );
        }, 100);
    }
}

SecurityController.$inject = ['$scope', 'memmeeBroadCastService', '$location', '$timeout'];