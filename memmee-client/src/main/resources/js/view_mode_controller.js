function ViewModeController($scope, $http, broadCastService) {

    $scope.user = broadCastService.user;
    $scope.memmee = null;

    //Action Handlers
    $scope.onDeleteMemmee = function () {
        broadCastService.confirmDeleteViewModeController();
    };

    $scope.generateAndShowPublicLink = function () {
        $http({method:'PUT', url:'/memmeerest/sharememmee/?apiKey=' + $scope.user.apiKey, data:$scope.memmee}).
            success(function (data, status, headers, config) {
                console.log('you have generated a share link');
                $scope.memmee = data;
                broadCastService.showShareLinkViewModeController($scope.memmee);
            }).
            error(function (data, status, headers, config) {
                console.log('there was an error generating your share link');
            });
    }

    //Service Calls
    $scope.getDefaultMemmee = function () {
        if (broadCastService.selectedMemmee == null) {
            $http({method:'GET', url:'/memmeerest/getmemmee?apiKey=' + $scope.user.apiKey}).
                success(function (data, status, headers, config) {
                    console.log('your memmee has been loaded');
                    $scope.memmee = broadCastService.selectedMemmee = data;
                }).error(function (data, status, headers, config) {
                    console.log('error loading your doggone memmee');
                });
        } else {
            $scope.memmee = broadCastService.selectedMemmee;
        }
    };

    $scope.deleteMemmee = function (memmee) {
        $http({method:'DELETE', url:'/memmeerest/deletememmee?apiKey=' + $scope.user.apiKey + "&id=" + memmee.id}).
            success(function (data, status, headers, config) {
                console.log('your memmee has been deleted');
                $scope.memmee = null;

                //Broadcasts event
                broadCastService.memmeeDeletedViewModeController();

                //Re-initializes view
                $scope.getDefaultMemmee();
            }).
            error(function (data, status, headers, config) {
                console.log('error deleting your doggone memmee');
            });
    };

    //Broadcast Handlers
    $scope.$on(ArchiveListControllerEvents.get('MEMMEE_SELECTED'), function (event, memmee) {
        $scope.memmee = memmee;
    });

    $scope.$on(AlertsControllerEvents.get('YES_SELECTED'), function (event, promptingEvent) {
        if (promptingEvent == ViewModeControllerEvents.get('CONFIRM_DELETE')) {
            $scope.deleteMemmee($scope.memmee);
        }
    });

    //UI
    $scope.getDisplayDate = function () {
        return $scope.memmee.displayDate.toDateString();
    }

    //Initializaton
    $scope.getDefaultMemmee();

    $scope.showImage = function () {
        if ($scope.memmee && $scope.memmee.attachment && $scope.memmee.attachment.filePath) {
            return true;
        }
        return false;
    }
}

ViewModeController.$inject = ['$scope', '$http', 'memmeeBroadCastService'];