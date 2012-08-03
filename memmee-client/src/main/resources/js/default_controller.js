//Use this as a place to put base/extended functionality in other controllers (search for use to see)
function DefaultController($scope, tearDown, runTearDownOnLoad) {
    if (runTearDownOnLoad == undefined) {
        runTearDownOnLoad = true;
    }

    //Event Listeners
    $scope.$on(LoginControllerEvents.get('LOGOUT'), tearDown);


    //Initialization
    if (runTearDownOnLoad) {
        tearDown();
    }
}