function HeaderController($scope, broadCastService, $location, $timeout) {

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

    $scope.$on(LoginControllerEvents.get('LOGIN'), function () {

        console.log("NavigationController:handleLogin()");

        $scope.visibleLoggedInStyle = 'isVisible';
        $scope.loggedInUser = broadCastService.user;

    });

    $scope.createModeClicked = function () {
        broadCastService.createModeStartedCreateModeController();
        $location.path("create");
    }

    $scope.viewModeClicked = function () {
        broadCastService.createModeCancelledCreateModeController();
        $location.path("view");
    }

    $scope.isCreateMode = function () {
        return broadCastService.isCreateMode();
    }

//    $scope.toggleCreateMode = function()
//    {
//        console.log("About to toggle create mode")
//        if( broadCastService.isCreateMode() )
//        {
//            console.log("Turning off create mode....");
//            broadCastService.createModeCancelledCreateModeController();
//            $location.path("view");
//        }
//        else
//        {
//            console.log("Turning on create mode....");
//            broadCastService.createModeStartedCreateModeController();
//            $location.path("create");
//        }
//    }

    var closedropdownTimer;

    $scope.userSettingsDropdownStyle = "isHidden";
    $scope.toggleSettingsDropdown = function () {
        console.log("mouse over happening.")
        if ($scope.userSettingsDropdownStyle == "isHidden") {
            $scope.userSettingsDropdownStyle = "isVisible";
        }
        else {
            $scope.userSettingsDropdownStyle = "isHidden";
        }

        if (closedropdownTimer) {
            closedropdownTimer = undefined;
        }
    }


    $scope.closeDropdownIfMouseOutside = function () {
        closedropdownTimer = $timeout($scope.toggleSettingsDropdown, 100);
    }

    $scope.cancelDropdownTimer = function () {
        if (closedropdownTimer) {
            $timeout.cancel(closedropdownTimer);
        }
    }


    $scope.signOut = function () {
        broadCastService.logoutUser();
        $scope.loggedInUser = "";
        $scope.visibleLoggedInStyle = "isHidden";
    }

}

HeaderController.$inject = ['$scope', 'memmeeBroadCastService', '$location', '$timeout'];