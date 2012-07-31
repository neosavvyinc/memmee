function NavigationController($scope, broadCastService, $location) {

    $scope.loggedInUser = "";

    $scope.visibleLoggedInStyle = { visibility: 'hidden' };
    $scope.createModeStyles = "btn btn-primary";

    var resetCreationButton = function() {
        $scope.createModeStyles = "btn btn-primary";
    }

    var resetStyles = function() {
        $scope.visibleLoggedInStyle = {};

        resetCreationButton();
    }

    $scope.$on('handleLogin', function() {

        console.log("NavigationController:handleLogin()");
        resetStyles();
        $scope.loggedInUser = broadCastService.user;

    });

    $scope.$on(CreateModeControllerEvents.get('CREATE_MODE_CANCELLED'), function() {

        resetCreationButton();

    })

    $scope.toggleCreateMode = function()
    {
        if( $location.path() != "/loggedin" )
        {
            $location.path("/loggedin");
            return;
        }

        console.log("About to toggle create mode")
        if( broadCastService.isCreateMode() )
        {
            console.log("Turning off create mode....");
            $scope.createModeStyles = "btn btn-primary";
            broadCastService.createModeCancelledCreateModeController();
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

NavigationController.$inject = ['$scope', 'memmeeBroadCastService', '$location'];