//Use this as a place to put base/extended functionality in other controllers (search for use to see)
function ReportController($scope, reportingService ) {
    "use strict";
    console.log("Firing up the report controller");

    $scope.results;

    $scope.onShowAllRegisteredClicked = function () {
        console.log("clicking show all");
        reportingService.showAllRegistered(
            showAllResult,
            showAllFault
        );
    };

    $scope.onShowAllUsersWithCompletedProfiles = function () {
        console.log("clicking show all with completed profiles");
        reportingService.showAllUsersWithCompletedProfiles(
            showAllResult,
            showAllFault
        );
    };

    $scope.onShowAllUsersWithoutCompletedProfiles = function () {
        console.log("clicking show all users without completed profiles");
        reportingService.showAllUsersWithoutCompletedProfiles(
            showAllResult,
            showAllFault
        );
    };

    $scope.onShowAllUsersWithMemmeeCount = function () {
        console.log("clicking show all users with memmee count");
        reportingService.showAllUsersWithMemmeeCount(
            showAllResult,
            showAllFault
        );
    };

    $scope.onShowAllUsersWhoHaveNeverMemmeed = function () {
        console.log("clicking show all users with no memmees");
        reportingService.showAllUsersWhoHaveNeverMemmeed(
            showAllResult,
            showAllFault
        );
    };

    var showAllResult = function (data) {
        console.log("Show all results success");
        JSON.stringify(data);
        $scope.results = data;
    };

    var showAllFault = function (data) {
        console.log("Show all results failed");
    }
}