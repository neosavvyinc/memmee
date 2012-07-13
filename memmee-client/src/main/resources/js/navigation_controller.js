function NavigationController($scope, broadCastService) {

    $scope.loggedInUser = "";

    $scope.$on('handleLogin', function() {

        console.log("NavigationController:handleLogin()");

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

}

NavigationController.$inject = ['$scope', 'memmeeBroadCastService'];