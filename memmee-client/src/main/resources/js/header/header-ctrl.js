function HeaderController($scope, broadCastService, $location) {

    //Super/Inherited Methods
    DefaultController($scope,
        /* load/tearDown */ function () {
            $scope.loggedInUser = "";

            $scope.visibleLoggedInStyle = 'isHidden';
        });



//    var resetStyles = function() {
//        $scope.visibleLoggedInStyle = 'isHidden';
//
//        $scope.createModeStyles = "btn btn-primary disabled";
//    }

    $scope.$on(LoginControllerEvents.get('LOGIN'), function() {

        console.log("NavigationController:handleLogin()");

        $scope.visibleLoggedInStyle = 'isVisible';
        $scope.loggedInUser = broadCastService.user;

    });

    $scope.toggleCreateMode = function()
    {
        console.log("About to toggle create mode")
        if( broadCastService.isCreateMode() )
        {
            console.log("Turning off create mode....");
            broadCastService.createModeCancelledCreateModeController();
            $location.path("view");
        }
        else
        {
            console.log("Turning on create mode....");
            broadCastService.createModeStartedCreateModeController();
            $location.path("create");
        }
    }

    $scope.userSettingsDropdownStyle = "isHidden";
    $scope.toggleSettingsDropdown = function () {
        console.log("mouse over happening.")
        if ($scope.userSettingsDropdownStyle == "isHidden") {
            $scope.userSettingsDropdownStyle = "isVisible";
        }
        else {
            $scope.userSettingsDropdownStyle = "isHidden";
        }
    }

    $scope.signOut = function()
    {
        broadCastService.logoutUser();
        $scope.loggedInUser = "";
        $scope.visibleLoggedInStyle = "isHidden";
    }

}

HeaderController.$inject = ['$scope', 'memmeeBroadCastService', '$location'];