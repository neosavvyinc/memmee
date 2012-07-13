function NavigationController($scope, broadCastService) {

    $scope.loggedInUser = "";

    $scope.visibleLoggedInStyle = { visibility: 'hidden' };
    $scope.createModeStyles = "btn btn-primary";

    var resetStyles = function() {
        $scope.visibleLoggedInStyle = {};
        $scope.createModeStyles = "btn btn-primary";
    }

    $scope.$on('handleLogin', function() {

        console.log("NavigationController:handleLogin()");
        resetStyles();
        $scope.loggedInUser = broadCastService.user;

    });


    $scope.toggleCreateMode = function()
    {
        console.log("About to toggle create mode")
        if( broadCastService.isCreateMode() )
        {
            console.log("Turning off create mode....");
            $scope.createModeStyles = "btn btn-primary";
            broadCastService.createModeCancelled();
        }
        else
        {
            console.log("Turning on create mode....");
            $scope.createModeStyles = "btn btn-primary disabled";
            broadCastService.createModeStarted();
        }
    }

    $scope.signOut = function()
    {
        broadCastService.logoutUser();
        $scope.loggedInUser = "";
        $scope.visibleLoggedInStyle = { visibility: 'hidden' };
    }

}

NavigationController.$inject = ['$scope', 'memmeeBroadCastService'];