function NavigationController($scope, broadCastService) {

    $scope.loggedInUser = "";

    $scope.visibleLoggedInStyle = { visibility: 'hidden' };

    $scope.$on('handleLogin', function() {

        console.log("NavigationController:handleLogin()");
        $scope.visibleLoggedInStyle = {};
        $scope.loggedInUser = broadCastService.user;

    });


    $scope.toggleCreateMode = function()
    {
        console.log("About to toggle create mode")
        if( broadCastService.isCreateMode() )
        {
            console.log("Turning off create mode....");
            broadCastService.createModeCancelled();
        }
        else
        {
            console.log("Turning on create mode....");
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