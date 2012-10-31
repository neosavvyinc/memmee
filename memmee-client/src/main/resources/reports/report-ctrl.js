//Use this as a place to put base/extended functionality in other controllers (search for use to see)
function ReportController($scope) {
    "use strict";
    console.log("Firing up the report controller");

    $scope.onShowAllRegisteredClicked = function () {
        console.log("clicking show all");
        //href="/reporting/users"
    };

    $scope.onShowAllUsersWithCompletedProfiles = function () {
        console.log("clicking show all with completed profiles");
        //href="/reporting/users/completed/profile"
    };

    $scope.onShowAllUsersWithoutCompletedProfiles = function () {
        console.log("clicking show all users without completed profiles");
        //href="/reporting/users/no/profile"
    };

    $scope.onShowAllUsersWithMemmeeCount = function () {
        console.log("clicking show all users with memmee count");
        //href="/reporting/users/memmeecount"
    };

    $scope.onShowAllUsersWhoHaveNeverMemmeed = function () {
        console.log("clicking show all users with no memmees");
        //href="/reporting/users/no/memmeecount"
    };
}