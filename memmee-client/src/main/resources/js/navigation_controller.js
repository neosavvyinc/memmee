function NavigationController($scope, broadCastService) {

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